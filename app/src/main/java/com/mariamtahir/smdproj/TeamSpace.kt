package com.mariamtahir.smdproj

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class TeamSpace : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_space)

        val chatButton = findViewById<ImageView>(R.id.chat)
        val home=findViewById<ImageView>(R.id.home)
        val note=findViewById<ImageView>(R.id.gallery)
        val btn=findViewById<Button>(R.id.add_task_button)



        //-------------------------------------------------------------------
        val projectId = "a1tAurycNgzAfodbfIIu"
        val taskList = mutableListOf<Task>(
            Task("Implement Code",  "To Do"))
        //recycler view code
        /*val taskList = listOf(
            Task("Implement Code",  "To Do"),
            Task("Make database", "Doing"),
            Task("Code XML",  "Doing"),
            Task("Make figma board", "Done")
        )*/

        // RecyclerView setup

        val recyclerView = findViewById<RecyclerView>(R.id.teamspace_rview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = TaskAdapter(taskList)



        retrieveTasks(projectId, adapter)
        recyclerView.adapter = adapter

        //---------------------------------------------------------------------------
        btn.setOnClickListener {
            val intent=Intent(this,addTeamTask::class.java)
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

    private fun retrieveTasks(projectId: String,adapter: TaskAdapter) {
        val projectId = "a1tAurycNgzAfodbfIIu"
        val taskList = mutableListOf<Task>()
        val db = FirebaseFirestore.getInstance()

        db.collection("projects")
            .document(projectId)
            .collection("tasks")
            .get()
            .addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot.documents) {
                    val task = document.toObject(Task::class.java)
                    task?.let {
                        taskList.add(it)
                    }
                }
                // Updating the RecyclerView adapter with the retrieved tasks
               /* val recyclerView = findViewById<RecyclerView>(R.id.teamspace_rview)
                (recyclerView.adapter as? TaskAdapter)?.apply {
                    updateTaskList(taskList)
                }*/
            }
            .addOnFailureListener { exception ->
                Log.e("TeamSpace", "Error getting tasks for project $projectId", exception)
            }




    }
}