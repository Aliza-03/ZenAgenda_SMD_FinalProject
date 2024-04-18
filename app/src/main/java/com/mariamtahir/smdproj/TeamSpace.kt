package com.mariamtahir.smdproj

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class TeamSpace : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_space)

        val chatButton = findViewById<ImageView>(R.id.chat)
        val home=findViewById<ImageView>(R.id.home)
        val note=findViewById<ImageView>(R.id.gallery)
        chatButton.setOnClickListener {
            val intent = Intent(this, Chats::class.java)
            startActivity(intent)
        }

        home.setOnClickListener {
            val intent = Intent(this, profilePage::class.java)
            startActivity(intent)
        }

        note.setOnClickListener {
            intent= Intent(this,Notes::class.java)
            startActivity(intent)
        }
    }
}