package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class TeamSpace : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_space)

        // Find the button by its ID
        val chatButton = findViewById<ImageView>(R.id.chat)

        // Set click listener on the button
        chatButton.setOnClickListener {
            // Create an Intent to navigate to the "chats" activity
            val intent = Intent(this, Chats::class.java)
            // Start the "chats" activity
            startActivity(intent)
        }

    }
}