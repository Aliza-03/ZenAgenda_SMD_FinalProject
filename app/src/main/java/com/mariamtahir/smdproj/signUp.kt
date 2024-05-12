package com.mariamtahir.smdproj

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth
import java.net.URLEncoder

class signUp : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth= FirebaseAuth.getInstance()
        //form variables
        val name=findViewById<EditText>(R.id.enterName)
        val email=findViewById<EditText>(R.id.enterEmail)
        val pass=findViewById<EditText>(R.id.enterPassword)
        //buttons
        val signUp = findViewById<Button>(R.id.signUpButton)
        val login = findViewById<TextView>(R.id.login)
        val bck=findViewById<ImageView>(R.id.arrowbackLogin)

        bck.setOnClickListener {
            intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        signUp.setOnClickListener {
            if (name.text.isEmpty()) {
                Toast.makeText(this, "Enter name credentials", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (email.text.isEmpty()) {
                Toast.makeText(this, "Enter email credentials", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass.text.isEmpty()) {
                Toast.makeText(this, "Enter password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email.text.toString(), pass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign up success, update UI with the signed-up user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        // Once signup is successful, navigate to the profile page
                        val intent = Intent(this, profilePage::class.java)
                        startActivity(intent)

                        //---------------------------------------------------------------------
                        // Encode the username to handle special characters
                        val uname = name.text.toString()
                        val userid = user?.uid

                        val encodedUname = URLEncoder.encode(uname, "UTF-8")
                        val url = "http://192.168.18.14/zenagenda/v1/entername.php"

                        // Create a request to update the user's name
                        val request = object : StringRequest(
                            Method.POST, url,
                            Response.Listener { response ->
                                Toast.makeText(
                                    applicationContext,
                                    "Name updated successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            Response.ErrorListener { error ->

                                val responseCode = error.networkResponse?.statusCode
                                if (responseCode != null) {
                                    // Log or handle the response code
                                    Log.d("Response Code", "Error Response Code: $responseCode")

                                }
                            }){}

                        Volley.newRequestQueue(this).add(request)

                        // Add the request to the request queue

                    } else {
                        // If sign up fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }


        }

        login.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }



    }


}


