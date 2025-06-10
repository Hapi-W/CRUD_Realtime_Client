package com.example.crudrealtimeclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.crudrealtimeclient.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var  databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val searchPhoneNumber : String = binding.searchPhoneNumber.text.toString()
            if (searchPhoneNumber.isNotEmpty()){
                readData(searchPhoneNumber)
            }else{
                Toast.makeText(this, " Please Enter Phone Number", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private  fun readData(phoneNumber : String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.child(phoneNumber).get().addOnSuccessListener {
            if (it.exists()){
                val name = it.child("name").value
                val operator = it.child("operator").value
                val location = it.child("location").value

                Toast.makeText(this, "Results Found", Toast.LENGTH_SHORT).show()
                binding.searchPhoneNumber.text.clear()
                binding.readName.text = name.toString()
                binding.readOperator.text = operator.toString()
                binding.readLocation.text = location.toString()
            }else{
                Toast.makeText(this, "Phone Number does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}