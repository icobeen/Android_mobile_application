package com.example.projetmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MtActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mt1)

        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            // Start the game activity
            val intent = Intent(this, MtActivity2::class.java)
            startActivity(intent)
        }
    }
}