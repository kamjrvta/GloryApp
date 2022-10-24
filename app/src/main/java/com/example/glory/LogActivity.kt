package com.example.glory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.glory.databinding.ActivityGetstartedBinding
import com.example.glory.databinding.ActivityLogBinding


class LogActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding: ActivityLogBinding

    override fun onCreate(savedInstanceState: Bundle?)  {

        super.onCreate(savedInstanceState)
        binding = ActivityLogBinding .inflate(layoutInflater)
        setContentView(binding.root)

        //handle sign up link below
        binding.signUpLink.setOnClickListener{
            startActivity(Intent(this@LogActivity, SignUp::class.java))
        }


        }
    }