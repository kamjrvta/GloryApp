package com.example.glory

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.glory.databinding.ActivityCreateCategoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CreateCategory : AppCompatActivity() {

    // view binding
    private lateinit var binding:ActivityCreateCategoryBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init // firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click, go back
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        //handle click, begin upload category
        binding.createCategoryButton.setOnClickListener {
            validateData()
        }
    }
    private var category = ""

    private fun validateData(){


        //get data
        category = binding.inputCategory.text.toString().trim()

        //validate data
        if(category.isBlank()){
            Toast.makeText(this, "Enter Category...", Toast.LENGTH_SHORT).show()
        }
        else{
            addCategoryFirebase()
        }
    }

    private fun addCategoryFirebase() {
     //show progress
        progressDialog.show()

     //get timestamp
        val timestamp =System.currentTimeMillis()

    //setup data to add in firebase db
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["category"] = category
        hashMap["timestamp"] = timestamp
        hashMap["uid"]= "${firebaseAuth.uid}"

        //add to firebase db : Database Root > Categories > categoryId > category info
        val ref = FirebaseDatabase.getInstance().getReference("Categories")

        ref.child( "$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this, "Added successfully...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {  e->
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }


    }
}