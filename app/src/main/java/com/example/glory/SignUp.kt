package com.example.glory

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
import com.example.glory.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    //view binding
    private lateinit var binding: ActivitySignUpBinding

    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //init progress dialog, will show while creating account /Register User
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle log in
        binding.signUpLink.setOnClickListener() {
            //back to log in
            startActivity(Intent(this@SignUp, LogActivity ::class.java))
        }

        //handle sign up
        binding.signUpBut.setOnClickListener() {
            /*Steps
            1. Input Data
            2. Validate Data
            3. Create account - Firebase Auth
            4. Save User Info - Firebase Database
             */
            validateData()
        }


    }
    private var name = ""
    private var email = ""
    private var password = ""


    private fun validateData() {
        //Input Data
        name = binding.name.text.toString().trim()
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()
        val cPassword = binding.cPassword.text.toString().trim()

        //validate Data
        if(name.isEmpty()){
            //empty name
            Toast.makeText(this, "Enter your name ...", Toast.LENGTH_SHORT).show()
        }
    else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
           //invalid email pattern
           Toast.makeText(this, "Invalid Email Pattern...", Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            //empty password
            Toast.makeText(this, "Enter your password ...", Toast.LENGTH_SHORT).show()
        }
        else if (cPassword.isEmpty()){
            //empty password
            Toast.makeText(this, "You have to confirm your password ...", Toast.LENGTH_SHORT).show()
        }
        else if (password != cPassword){
            Toast.makeText(this, "Password doesn't match ...", Toast.LENGTH_SHORT).show()
        }

        else{
            createUserAccount()

        }
    }

    private fun createUserAccount() {
        // Create Account - Firebase Auth

        //show progress
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()

        //create user in firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //account created, now ready to add to db
                updateUserInfo()
                //Toast.makeText(this, "Password doesn't match ...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e->
                //failed creating the account
                progressDialog.dismiss()
                Toast.makeText(this, "Failed creating account due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUserInfo() {
        //Save user info into database
        progressDialog.setMessage("Saving user info...")

        //timestamp
        val timestamp = System.currentTimeMillis()

        //get current user uid, since user is now registered so we can get it now
        val uid =firebaseAuth.uid

        //set up date to add in db
        val hashMap : HashMap<String, Any?> = HashMap()
        hashMap["uid"] = uid
        hashMap["email"] = email
        hashMap["name"]= name
        hashMap["profileImage"] = "" // add empty, will do in profile edit
        hashMap["userType"] = "user" //possible values are user/admin, will change value to admin manually
        hashMap["timestamp"] = timestamp

        //set data to db
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                //user info saved, open dashboard
                updateUserInfo()
                Toast.makeText(this, "Account created ...", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SignUp, Dashboard ::class.java))
                finish()
            }
            .addOnFailureListener{ e->
                //failed saving user info to db
                progressDialog.dismiss()
                Toast.makeText(this, "Failed saving info due to ${e.message}", Toast.LENGTH_SHORT).show()
            }

    }
}


