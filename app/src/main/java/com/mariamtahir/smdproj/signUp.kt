package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class signUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signUp = findViewById<Button>(R.id.signUpButton)
        val login = findViewById<TextView>(R.id.login)

        val bck=findViewById<ImageView>(R.id.arrowbackLogin)

        bck.setOnClickListener {
            intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        signUp.setOnClickListener {
            val intent = Intent(this, profilePage::class.java)
            startActivity(intent)
        }

        login.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }
    }
}