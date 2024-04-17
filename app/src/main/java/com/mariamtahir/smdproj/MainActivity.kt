package com.mariamtahir.smdproj

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textclick=findViewById<TextView>(R.id.textclick)

        textclick.setOnClickListener {
            intent=Intent(this,CoverPage::class.java)
            startActivity(intent)
        }


    }
}