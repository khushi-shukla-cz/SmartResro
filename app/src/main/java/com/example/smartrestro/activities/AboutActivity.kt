package com.example.smartrestro.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.smartrestro.databinding.ActivityAboutBinding

/**
 * AboutActivity — displays restaurant information including description,
 * contact details, email, and location.
 *
 * Contact number and email are tappable — they open the Phone Dialer
 * and Email app respectively.
 */
class AboutActivity : AppCompatActivity() {

    // ViewBinding reference for activity_about layout
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using ViewBinding
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar with back navigation
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "About Us"

        setupClickListeners()
    }

    /**
     * Makes the phone and email fields tappable with appropriate system intents.
     */
    private fun setupClickListeners() {

        // Open phone dialer when contact number is tapped
        binding.tvPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:+919876543210")
            }
            startActivity(intent)
        }

        // Open email app when email address is tapped
        binding.tvEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:hello@smartrestro.com")
                putExtra(Intent.EXTRA_SUBJECT, "Enquiry - SmartRestro")
            }
            startActivity(intent)
        }

        // Open Google Maps when location is tapped
        binding.tvLocation.setOnClickListener {
            val geoUri = Uri.parse("geo:0,0?q=SmartRestro+Restaurant+Mumbai")
            val mapIntent = Intent(Intent.ACTION_VIEW, geoUri)
            startActivity(mapIntent)
        }
    }

    // Handle Up/Back button in toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
