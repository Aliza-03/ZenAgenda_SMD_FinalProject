package com.mariamtahir.smdproj

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class taskslist : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_list)
        val editbutton: ImageButton = findViewById(R.id.editbutton)
        val addtask=findViewById<ImageView>(R.id.add_task_btn)
        editbutton.setOnClickListener {

            val intent = Intent(this, EditTaskActivity::class.java)
            startActivity(intent)
        }

        addtask.setOnClickListener {

            val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
        }

    }
}