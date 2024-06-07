package com.example.wallcandy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(1000)

        installSplashScreen().apply {
            initialize()
        }
    }

    private fun initialize(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}