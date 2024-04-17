package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts

class CoverPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cover_page)
        val lg=findViewById<ImageView>(R.id.logo_)

        lg.setOnClickListener {
            intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}