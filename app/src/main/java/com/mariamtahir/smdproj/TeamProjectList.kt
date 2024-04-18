package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TeamProjectList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_project_list)
        val proj=findViewById<com.google.android.material.card.MaterialCardView>(R.id.cc1)

        proj.setOnClickListener {
            intent= Intent(this,TeamSpace::class.java)
            startActivity(intent)
        }
    }
}