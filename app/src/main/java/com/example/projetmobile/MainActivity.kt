package com.example.projetmobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun Xolunch(view: View) {
        val i= Intent(this,XoActivity1::class.java)
        startActivity(i)
    }
    fun Btlunch(view: View) {
        val i= Intent(this,BtActivity1::class.java)
        startActivity(i)
    }
    fun MatchTileslunch(view: View) {
        val i= Intent(this,MtActivity1::class.java)
        startActivity(i)
    }
    fun MemoryGamelunch(view: View){
        val i= Intent(this,MgActivity1::class.java)
        startActivity(i)
    }
}