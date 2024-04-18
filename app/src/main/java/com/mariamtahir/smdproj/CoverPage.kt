package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class CoverPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cover_page)
        val lgin = findViewById<Button>(R.id.loginButton)
        val sign=findViewById<TextView>(R.id.signUp)
        val forgotpass=findViewById<TextView>(R.id.forgotPassword)
        val bck=findViewById<ImageView>(R.id.arrowbackLogin)

        bck.setOnClickListener {
            intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        forgotpass.setOnClickListener {
            intent=Intent(this,forgotpass::class.java)
            startActivity(intent)
        }

        lgin.setOnClickListener {
            intent=Intent(this,profilePage::class.java)
            startActivity(intent)
        }
        sign.setOnClickListener {
            intent=Intent(this,signUp::class.java)
            startActivity(intent)
        }
    }
}