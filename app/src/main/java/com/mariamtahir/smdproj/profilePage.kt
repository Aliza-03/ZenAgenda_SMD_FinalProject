package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class profilePage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        val editpfp=findViewById<ImageView>(R.id.editProfilePhoto1)
        val pomo=findViewById<Button>(R.id.pomodoro)
        val tasklist=findViewById<Button>(R.id.tasksButton)
        val colab=findViewById<Button>(R.id.collabButton)
        val note=findViewById<Button>(R.id.note)
        val lgout2=findViewById<ImageView>(R.id.arrowBack)

        //user profile variables
        auth = FirebaseAuth.getInstance()
        val uname=findViewById<TextView>(R.id.name)
        val currentUser = auth.currentUser




        //update the username according to the user accessing the dashboard
        fetchUserDataAndSetDisplayName(uname)

        //buttons that can be clicked below

        editpfp.setOnClickListener {
            intent= Intent(this,taskslist::class.java)
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

    private fun fetchUserDataAndSetDisplayName(nameTextView: TextView) {
        val currentUser = auth.currentUser



        // Check if the user is signed in
        currentUser?.let { user ->
            // Fetch user data from Firebase Authentication
            user.reload().addOnSuccessListener {
                // Get the refreshed user instance
                val refreshedUser = auth.currentUser

                // Get the display name of the user
                val displayName = refreshedUser?.displayName

                // Update the TextView with the display name
                nameTextView.text = displayName ?: "Display Name Not Set"
            }.addOnFailureListener { exception ->
                // Handle failure to refresh user data
                exception.printStackTrace()
            }
        }
    }
}

