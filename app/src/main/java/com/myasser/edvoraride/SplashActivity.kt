package com.myasser.edvoraride

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    private val milliseconds:Long=2500
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //added post delayed intent to main activity
        val handler= Handler()
        handler.postDelayed({startActivity(Intent(this@SplashActivity,MainActivity::class.java))},milliseconds)
    }
}