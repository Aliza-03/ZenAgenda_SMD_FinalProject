package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class profileCustomize : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_customize)

        val save = findViewById<Button>(R.id.saveButton)

        save.setOnClickListener {
            val intent = Intent(this, profilePage::class.java)
            startActivity(intent)
        }
    }
}