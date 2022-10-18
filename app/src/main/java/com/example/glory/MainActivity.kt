package com.example.glory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogButton = findViewById<Button>(R.id.logButton)

        btnLogButton.setOnClickListener {
            val intent = Intent(this,LogActivity::class.java)

            startActivity(intent)
        }

    }
}