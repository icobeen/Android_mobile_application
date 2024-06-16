package com.example.projetmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class XoActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xo1)
        // Find the play button
        val playButton = findViewById<Button>(R.id.play_btn)

        // Set click listener for the play button
        playButton.setOnClickListener {
            // Launch the GameActivity when the play button is clicked
            val intent = Intent(this, XoActivity2::class.java)
            startActivity(intent)
        }
    }
}