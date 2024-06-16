package com.example.projetmobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projetmobile.databinding.ActivityMg1Binding

class MgActivity1 : AppCompatActivity() {

    private  lateinit var binding : ActivityMg1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMg1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.StartBtn.setOnClickListener(){
            startActivity(Intent(this@MgActivity1, MgActivity2::class.java))
        }
    }
}