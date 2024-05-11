package com.mariamtahir.smdproj

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //splash screen code
        val splashScreenDuration = 2000 // 2 seconds
        val mainActivityIntent = Intent(this, CoverPage::class.java)
        val splashScreenTimer = object : Thread() {
            override fun run() {
                try {
                    sleep(splashScreenDuration.toLong())
                    startActivity(mainActivityIntent)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    finish() // Close the splash screen activity
                }
            }
        }
        splashScreenTimer.start()

        val textclick=findViewById<TextView>(R.id.textclick)

        textclick.setOnClickListener {
            intent=Intent(this,CoverPage::class.java)
            startActivity(intent)
        }


    }
}