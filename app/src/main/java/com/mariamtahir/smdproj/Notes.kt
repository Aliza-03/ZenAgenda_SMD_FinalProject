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
        val addnote=findViewById<ImageView>(R.id.addnotebutton)


        addnote.setOnClickListener {
            val intent = Intent(this, NotePageactivity::class.java)

            startActivity(intent)
        }


        chatButton.setOnClickListener {

            val intent = Intent(this, Chats::class.java)
            startActivity(intent)
        }
        note1.setOnClickListener {
            val intent = Intent(this, NotePageactivity::class.java)

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
