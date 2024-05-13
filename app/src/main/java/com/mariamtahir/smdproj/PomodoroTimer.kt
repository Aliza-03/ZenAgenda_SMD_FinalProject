package com.mariamtahir.smdproj

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import java.util.Calendar

class PomodoroTimer : AppCompatActivity() {

    //getting the variables for the timer
    private lateinit var timerTextView: TextView
    private lateinit var pomodoroTimer: CountDownTimer
    private var timeLeftInMillis: Long = 0
    private var timerRunning = false

    //----------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro_timer)

        timerTextView = findViewById(R.id.pomodoro_txt)
        val bck=findViewById<ImageView>(R.id.prev_btn)
        val playbtn=findViewById<ImageView>(R.id.play_btn)
        val pausebtn=findViewById<ImageView>(R.id.pause_btn)
        val replaybtn=findViewById<ImageView>(R.id.restart_btn)


        //push notification
        // Create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "PomodoroChannel"
            val channelName = "Pomodoro Timer Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        //---------------------------------------------------------------------------------------


        bck.setOnClickListener {
            intent= Intent(this,profilePage::class.java)
            startActivity(intent)
        }
        //timer functionalities
        playbtn.setOnClickListener {

            startPomodoroTimer(25 * 60 * 1000)
           // startPomodoroTimer(60*1000)
        }

        pausebtn.setOnClickListener {
            pauseTimer()
        }

        replaybtn.setOnClickListener {
            restartTimer()
        }
    }





    //these functions implement the timer functionality
    private fun startPomodoroTimer(durationMillis: Long) {
        pomodoroTimer = object : CountDownTimer(durationMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished / 1000)
            }

            override fun onFinish() {
                // Handle timer finish logic here
                updateTimerText(0)
                setAlarmForBreak()
            }
        }
        pomodoroTimer.start()
    }

    private fun updateTimerText(remainingSeconds: Long) {
        val hours = remainingSeconds / 3600
        val minutes = (remainingSeconds % 3600) / 60
        val seconds = remainingSeconds % 60
        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        timerTextView.text = formattedTime
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cancel the timer to avoid memory leaks
        if (::pomodoroTimer.isInitialized) {
            pomodoroTimer.cancel()
        }
    }

    private fun pauseTimer() {
        pomodoroTimer.cancel()
        timerRunning = false
    }

    private fun restartTimer() {
        startPomodoroTimer(timeLeftInMillis)
    }

    //alarm
    private fun setAlarmForBreak() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent,
            PendingIntent.FLAG_IMMUTABLE)

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            add(Calendar.MINUTE, 1) // Set alarm after 30 minutes
        }

        // Create the notification
        val notification = NotificationCompat.Builder(this, "PomodoroChannel")
            .setContentTitle("Pomodoro Timer")
            .setContentText("Time for a break!")
            .setSmallIcon(R.drawable.baseline_close_24)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Send the notification
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)



    }

}