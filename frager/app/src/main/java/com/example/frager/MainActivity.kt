package com.example.frager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text: TextView = findViewById(R.id.textView2) as TextView
        text.setOnClickListener {
            val intent:Intent = Intent (this, Registro ::class.java)
            startActivity(intent)
        }

    }
}