package com.mariamtahir.smdproj

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class addTeamTask : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_team_task)
        val projectId="a1tAurycNgzAfodbfIIu"
        //get variables from layout
        val taskname=findViewById<EditText>(R.id.task_txt)
        val status=findViewById<EditText>(R.id.status_txt)
        val add=findViewById<Button>(R.id.add_btn)

        val taskId = UUID.randomUUID().toString()

        //----------------------------------------------------------

        add.setOnClickListener {
            val db = FirebaseFirestore.getInstance()

           // val projectId = intent.getStringExtra("project_name") ?: "PDC Projecr"


            val task = hashMapOf(
                "taskId" to taskId,
                "taskTitle" to taskname.text.toString() ,
                "taskStatus" to status.text.toString()// Initial status
            )


            db.collection("projects")
                .document(projectId)
                .collection("tasks")
                .add(task)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "Task added with ID: ${documentReference.id}")
                    Toast.makeText(this,"Added new task",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding task", e)

                }
        }
    }
}