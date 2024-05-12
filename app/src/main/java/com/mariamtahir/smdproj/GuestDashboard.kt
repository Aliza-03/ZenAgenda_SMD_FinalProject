package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class GuestDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_dashboard)

        //setting the button nav
        val editpfp=findViewById<ImageView>(R.id.editProfilePhoto1)
        val pomo=findViewById<Button>(R.id.pomodoro)
        val tasklist=findViewById<Button>(R.id.tasksButton)
        val note=findViewById<Button>(R.id.note)

        editpfp.setOnClickListener {
            intent= Intent(this,profileCustomize::class.java)
            startActivity(intent)
        }

        pomo.setOnClickListener {
            intent= Intent(this,PomodoroTimer::class.java)
            startActivity(intent)
        }

        tasklist.setOnClickListener {
            intent= Intent(this,taskslist::class.java)
            startActivity(intent)
        }
        note.setOnClickListener {
            intent= Intent(this,Notes::class.java)
            startActivity(intent)
        }


    }

}