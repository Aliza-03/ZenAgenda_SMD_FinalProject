package com.mariamtahir.smdproj

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddProjectActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_project)

        //firebase declarations
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        auth= FirebaseAuth.getInstance()

        //set variables
        val projname=findViewById<EditText>(R.id.pname)
        val collaboratorEmail=findViewById<EditText>(R.id.collab)
        val status=findViewById<EditText>(R.id.statuset)
        val addProjectButton = findViewById<Button>(R.id.button)

        //adding data to firestore

        addProjectButton.setOnClickListener {
            val projectName = projname.text.toString()
            val collaborator = collaboratorEmail.text.toString()
            val projectStatus = status.text.toString()
            var userId:String?=null
            //getting collaborator email
            auth.fetchSignInMethodsForEmail(collaborator)
                .addOnSuccessListener { result ->
                    val signInMethods = result.signInMethods
                    if (signInMethods != null && signInMethods.isNotEmpty()) {
                         userId = currentUser?.uid
                    } else {
                        Log.d(TAG, "Email not associated with any existing account")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Error fetching sign-in methods: ", exception)
                }


            // Creating a new project object
            val project = hashMapOf(
                "userId" to currentUser?.uid,
                "projectName" to projectName,
                "collaborator" to collaborator,
                "collaboratorUserId" to userId,
                "status" to projectStatus

            )

// over here we add it to a new collection projects
            db.collection("projects")
                .add(project)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(this,"New project added",Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }


    }
}