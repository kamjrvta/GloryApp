package com.example.glory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.glory.databinding.ActivityGetstartedBinding

class GetStartedActivity : AppCompatActivity() {

    //view binding
    private lateinit var binding:ActivityGetstartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGetstartedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle get started button
        binding.logButton.setOnClickListener{
            startActivity(Intent(this@GetStartedActivity, SignUp::class.java))
        }



        }

    }
