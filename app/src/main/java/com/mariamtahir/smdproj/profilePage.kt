package com.mariamtahir.smdproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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

        lgout2.setOnClickListener {
            auth.signOut()
            intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

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


    }

    private fun fetchUserDataAndSetDisplayName(nameTextView: TextView) {
        val currentUser = auth.currentUser
        val userid=currentUser?.uid

        val url = "http://192.168.18.14/zenagenda/v1/getuid.php?userid=$userid"

        // Create a JSON request using Volley
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Parse the JSON response
                val name = response.getString("name")

                // Set the name in the TextView
                nameTextView.text = name
            },
            { error ->
                // Handle errors
                Toast.makeText(this, "Error fetching user data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(jsonObjectRequest)



        }
    }


