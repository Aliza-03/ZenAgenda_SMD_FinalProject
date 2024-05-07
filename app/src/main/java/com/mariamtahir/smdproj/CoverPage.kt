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
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth

class CoverPage : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cover_page)

        auth = FirebaseAuth.getInstance()


        val lgin = findViewById<Button>(R.id.loginButton)
        val sign=findViewById<TextView>(R.id.signUp)
        val forgotpass=findViewById<TextView>(R.id.forgotPassword)
        val bck=findViewById<ImageView>(R.id.arrowbackLogin)

        //authentication parameters
        val email=findViewById<EditText>(R.id.enterEmail)
        val pword=findViewById<EditText>(R.id.enterPassword)



        bck.setOnClickListener {
            intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        forgotpass.setOnClickListener {
            intent=Intent(this,forgotpass::class.java)
            startActivity(intent)
        }

        lgin.setOnClickListener {
            if (email.text.isEmpty()) {
                Toast.makeText(this, "Enter email credentials", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pword.text.isEmpty()) {
                Toast.makeText(this, "Enter password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email.text.toString(), pword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        // Once login is successful, navigate to the profile page
                        val intent = Intent(this, profilePage::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        sign.setOnClickListener {
            intent=Intent(this,signUp::class.java)
            startActivity(intent)
        }
    }
}

// Helper function to save user ID in SharedPreferences
fun saveUserIdToSharedPreferences(context: Context, userId: String) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("userId", userId)
    editor.apply()
}

// Helper function to retrieve user ID from SharedPreferences
fun getUserIdFromSharedPreferences(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("userId", null)
}