package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Notes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        // Find the button by its ID
        val chatButton = findViewById<ImageView>(R.id.chat_)
        val note1=findViewById<TextView>(R.id.firstnote)
        val home=findViewById<ImageView>(R.id.home_)


        // Set click listener on the button
        chatButton.setOnClickListener {
            // Create an Intent to navigate to the "chats" activity
            val intent = Intent(this, Chats::class.java)
            // Start the "chats" activity
            startActivity(intent)
        }
        note1.setOnClickListener {
            // Create an Intent to navigate to the "chats" activity
            val intent = Intent(this, NotePageactivity::class.java)
            // Start the "chats" activity
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


    }
}
