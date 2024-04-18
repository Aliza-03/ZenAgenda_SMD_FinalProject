package com.mariamtahir.smdproj

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class taskslist : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_list)
        val editbutton: ImageButton = findViewById(R.id.editbutton)
        // Set click listener on the RelativeLayout
        editbutton.setOnClickListener {
            // Create an Intent to navigate to the "chat_page" activity
            val intent = Intent(this, EditTaskActivity::class.java)
            // Start the "chat_page" activity
            startActivity(intent)
        }

    }
}