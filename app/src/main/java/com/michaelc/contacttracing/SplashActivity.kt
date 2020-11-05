package com.michaelc.contacttracing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
//https://www.youtube.com/watch?v=GtE8NwOQoos
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val runnable = Runnable {
        if (!isFinishing) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }


    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}