package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class TeamProjectList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_project_list)
        val proj=findViewById<com.google.android.material.card.MaterialCardView>(R.id.cc1)
        val chatButton = findViewById<ImageView>(R.id.chat_)
        val home=findViewById<ImageView>(R.id.home_)
        val note=findViewById<ImageView>(R.id.gallery_)
        val addproj=findViewById<ImageView>(R.id.add_project_btn)

        addproj.setOnClickListener {
            val intent = Intent(this, AddProjectActivity::class.java)

            startActivity(intent)
        }


        proj.setOnClickListener {
            intent= Intent(this,TeamSpace::class.java)
            startActivity(intent)
        }


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