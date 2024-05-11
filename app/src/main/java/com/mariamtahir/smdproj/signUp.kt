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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.auth

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

           //get the now currently signed user and update their profile details
            val user=FirebaseAuth.getInstance().currentUser
            val profileUpdate=UserProfileChangeRequest.Builder()
                .setDisplayName(name.text.toString())
                .build()
            user?.updateProfile(profileUpdate)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this,"Successfully added name",Toast.LENGTH_LONG).show()

                    }
                }
        }


        login.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
        }



    }


}


