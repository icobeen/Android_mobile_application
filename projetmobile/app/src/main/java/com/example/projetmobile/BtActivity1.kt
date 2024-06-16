
package com.example.projetmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class BtActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bt2)


    }
    fun StartBrainTrain(view: View){
        val i=Intent(this, BtActivity2::class.java)
        startActivity(i)
    }
}