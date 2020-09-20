package com.example.homegarden.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.homegarden.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val userProfileJson = getSharedPreferences("user", MODE_PRIVATE).getString("profile", null)
        Handler().postDelayed(
            Runnable {
                if (userProfileJson != null) {
                    startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, UserProfileActivity::class.java))
                }
                finish()
            },
            2000
        )
    }
}