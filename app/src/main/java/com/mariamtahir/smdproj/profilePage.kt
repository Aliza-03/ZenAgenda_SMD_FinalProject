package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class profilePage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        val editpfp=findViewById<ImageView>(R.id.editProfilePhoto1)
        val pomo=findViewById<Button>(R.id.pomodoro)
        val tasklist=findViewById<Button>(R.id.tasksButton)
        val colab=findViewById<Button>(R.id.collabButton)
        val note=findViewById<Button>(R.id.note)
        val lgout2=findViewById<ImageView>(R.id.arrowBack)

        editpfp.setOnClickListener {
            intent= Intent(this,profileCustomize::class.java)
            startActivity(intent)
        }

        pomo.setOnClickListener {
            intent= Intent(this,PomodoroTimer::class.java)
            startActivity(intent)
        }

      tasklist.setOnClickListener {
            intent= Intent(this,tasklist::class.java)
            startActivity(intent)
        }

        colab.setOnClickListener {
            intent= Intent(this,TeamProjectList::class.java)
            startActivity(intent)
        }

        note.setOnClickListener {
            intent= Intent(this,Notes::class.java)
            startActivity(intent)
        }
        lgout2.setOnClickListener {
            intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}