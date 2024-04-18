package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class PomodoroTimer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro_timer)
        val bck=findViewById<ImageView>(R.id.prev_btn)

        bck.setOnClickListener {
            intent= Intent(this,profilePage::class.java)
            startActivity(intent)
        }
    }
}