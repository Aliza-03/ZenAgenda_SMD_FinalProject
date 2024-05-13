package com.mariamtahir.smdproj

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class TeamProjectList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_project_list)

        //firestore
        val db = FirebaseFirestore.getInstance()


        val chatButton = findViewById<ImageView>(R.id.chat_)
        val home=findViewById<ImageView>(R.id.home_)
        val note=findViewById<ImageView>(R.id.gallery_)


        //recycler view
        val recyclerView: RecyclerView = findViewById(R.id.proj_card_rview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        Log.d("Debug", "Layout manager set")
        val projectList = mutableListOf<Project_data>()



// Query Firestore to get the projects collection
        db.collection("projects")
            .get()
            .addOnSuccessListener { result ->


                for (document in result) {
                    val projectName = document.getString("projectName") ?: ""
                    val status = document.getString("status") ?: ""


                    val projectData = Project_data(projectName, status)
                    projectList.add(projectData)
                }


                val adapter = ProjectSpace_Adapter(projectList)
                recyclerView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting documents: ", exception)
            }




 /*       val projectList = mutableListOf<Project_data>(
            Project_data("Project 1", "In Progress"),
            Project_data("Project 2", "Completed"),
            Project_data("Project 3", "Pending")
        )*/
        Log.d("Debug", "Adapter created")
        //adapter code
        val adapter = ProjectSpace_Adapter(projectList)
        recyclerView.adapter = adapter
        Log.d("Debug", "Adapter attached to RecyclerView")

        val addproj=findViewById<ImageView>(R.id.add_project_btn)

        addproj.setOnClickListener {
            val intent = Intent(this, AddProjectActivity::class.java)

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

        note.setOnClickListener {
            intent= Intent(this,Notes::class.java)
            startActivity(intent)
        }
    }
}