package com.example.smartrestro.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.smartrestro.databinding.ActivitySplashBinding

/**
 * SplashActivity — the first screen shown when the app launches.
 * Displays the app logo and name for 2 seconds, then navigates to HomeActivity.
 *
 * Suppressing the "CustomSplashScreen" lint warning because we're targeting API 24+
 * (the SplashScreen API is only recommended for API 31+).
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    // ViewBinding reference for activity_splash layout
    private lateinit var binding: ActivitySplashBinding

    companion object {
        // Splash delay duration in milliseconds
        private const val SPLASH_DELAY = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using ViewBinding
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // After SPLASH_DELAY milliseconds, navigate to HomeActivity and finish splash
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToHome()
        }, SPLASH_DELAY)
    }

    /**
     * Starts HomeActivity and finishes SplashActivity so user can't navigate back to it.
     */
    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish() // Remove splash from back stack
    }
}
