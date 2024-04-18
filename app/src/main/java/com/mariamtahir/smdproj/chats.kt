package com.mariamtahir.smdproj

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout

class Chats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chats)

        // Find the RelativeLayout by its ID
        val firstChatRelativeLayout: RelativeLayout = findViewById(R.id.firstchatrl)

        // Set click listener on the RelativeLayout
        firstChatRelativeLayout.setOnClickListener {
            // Create an Intent to navigate to the "chat_page" activity
            val intent = Intent(this, ChatPageActivity::class.java)
            // Start the "chat_page" activity
            startActivity(intent)
        }
    }
}
