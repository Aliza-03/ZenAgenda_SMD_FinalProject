package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class TeamProjectList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_project_list)

        // Find the button by its ID
        val chatButton = findViewById<ImageView>(R.id.chat_)

        // Set click listener on the button
        chatButton.setOnClickListener {
            // Create an Intent to navigate to the "chats" activity
            val intent = Intent(this, chats::class.java)
            // Start the "chats" activity
            startActivity(intent)
        }
    }
}