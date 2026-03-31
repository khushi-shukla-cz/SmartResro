package com.example.smartrestro.activities

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.smartrestro.R
import com.example.smartrestro.databinding.ActivityHomeBinding
import com.example.smartrestro.receivers.BatteryReceiver
import com.example.smartrestro.receivers.NetworkReceiver

/**
 * HomeActivity — the main landing screen of the SmartRestro app.
 * Displays a ViewFlipper with auto-flipping promo banners,
 * an image gallery with clickable items, and navigation buttons.
 */
class HomeActivity : AppCompatActivity() {

    // ViewBinding reference for activity_home layout
    private lateinit var binding: ActivityHomeBinding

    // Handler for updating dot indicators
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var dotUpdater: Runnable

    // BroadcastReceivers for monitoring system events
    private lateinit var networkReceiver: NetworkReceiver
    private lateinit var batteryReceiver: BatteryReceiver

    // Gallery items data
    private val galleryItems = listOf(
        GalleryItem("Margherita Pizza", "₹299", "Classic Italian pizza with fresh mozzarella, basil, and tomato sauce"),
        GalleryItem("Classic Burger", "₹249", "Juicy beef patty with lettuce, tomato, pickles, and our special sauce"),
        GalleryItem("Penne Pasta", "₹279", "Creamy alfredo sauce with tender penne pasta and parmesan cheese"),
        GalleryItem("Caesar Salad", "₹199", "Fresh romaine lettuce, croutons, parmesan, and caesar dressing"),
        GalleryItem("Chocolate Cake", "₹159", "Rich, moist chocolate cake with ganache frosting")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewFlipper()
        setupGallery()
        loadFeaturedImage()
        setupClickListeners()
    }

    /**
     * Sets up ViewFlipper with click listener and dot updater.
     */
    private fun setupViewFlipper() {
        // Load images into ViewFlipper slides
        loadViewFlipperImages()

        // Manual navigation on tap
        binding.viewFlipper.setOnClickListener {
            binding.viewFlipper.showNext()
            updateDots(binding.viewFlipper.displayedChild)
        }

        // Auto-update dots based on flip interval
        dotUpdater = object : Runnable {
            override fun run() {
                updateDots(binding.viewFlipper.displayedChild)
                handler.postDelayed(this, 2500)
            }
        }
        handler.postDelayed(dotUpdater, 2500)
    }

    /**
     * Loads images into ViewFlipper slides using Coil.
     */
    private fun loadViewFlipperImages() {
        // Load images from URLs using binding
        binding.ivSlide1.load("https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800&q=80") {
            crossfade(true)
            placeholder(R.drawable.ic_food_placeholder)
            error(R.drawable.ic_food_placeholder)
        }
        
        binding.ivSlide2.load("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&q=80") {
            crossfade(true)
            placeholder(R.drawable.ic_food_placeholder)
            error(R.drawable.ic_food_placeholder)
        }
        
        binding.ivSlide3.load("https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=800&q=80") {
            crossfade(true)
            placeholder(R.drawable.ic_food_placeholder)
            error(R.drawable.ic_food_placeholder)
        }
        
        binding.ivSlide4.load("https://images.unsplash.com/photo-1551024506-0bccd828d307?w=800&q=80") {
            crossfade(true)
            placeholder(R.drawable.ic_food_placeholder)
            error(R.drawable.ic_food_placeholder)
        }
    }

    /**
     * Sets up click listeners for gallery images.
     */
    private fun setupGallery() {
        // Load gallery images
        binding.galleryImg1.load("https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400&q=80") {
            crossfade(true)
            placeholder(R.drawable.ic_food_placeholder)
        }
        
        binding.galleryImg2.load("https://images.unsplash.com/photo-1550547660-d9450f859349?w=400&q=80") {
            crossfade(true)
            placeholder(R.drawable.ic_food_placeholder)
        }
        
        binding.galleryImg3.load("https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=400&q=80") {
            crossfade(true)
            placeholder(R.drawable.ic_food_placeholder)
        }
        
        binding.galleryImg4.load("https://images.unsplash.com/photo-1546793665-c74683f339c1?w=400&q=80") {
            crossfade(true)
            placeholder(R.drawable.ic_food_placeholder)
        }
        
        binding.galleryImg5.load("https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=400&q=80") {
            crossfade(true)
            placeholder(R.drawable.ic_food_placeholder)
        }

        // Set click listeners
        binding.galleryImg1.setOnClickListener { showGalleryItemInfo(0) }
        binding.galleryImg2.setOnClickListener { showGalleryItemInfo(1) }
        binding.galleryImg3.setOnClickListener { showGalleryItemInfo(2) }
        binding.galleryImg4.setOnClickListener { showGalleryItemInfo(3) }
        binding.galleryImg5.setOnClickListener { showGalleryItemInfo(4) }
    }

    /**
     * Shows detailed information dialog for a gallery item.
     */
    private fun showGalleryItemInfo(index: Int) {
        val item = galleryItems[index]
        AlertDialog.Builder(this)
            .setTitle("🍽️ ${item.name}")
            .setMessage("""
                💰 Price: ${item.price}
                
                📝 Description:
                ${item.description}
            """.trimIndent())
            .setPositiveButton("Order Now") { dialog, _ ->
                dialog.dismiss()
                startActivity(Intent(this, MenuActivity::class.java))
            }
            .setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    /**
     * Updates dot indicators based on current ViewFlipper position.
     */
    private fun updateDots(index: Int) {
        // Reset all dots to inactive
        binding.dot1.alpha = 0.3f
        binding.dot2.alpha = 0.3f
        binding.dot3.alpha = 0.3f
        binding.dot4.alpha = 0.3f

        // Highlight active dot
        when (index) {
            0 -> binding.dot1.alpha = 1.0f
            1 -> binding.dot2.alpha = 1.0f
            2 -> binding.dot3.alpha = 1.0f
            3 -> binding.dot4.alpha = 1.0f
        }
    }

    /**
     * Loads the featured image from remote URL.
     */
    private fun loadFeaturedImage() {
        binding.ivFeaturedImage.load("https://images.unsplash.com/photo-1546549032-9571cd6b27df?auto=format&fit=crop&w=1200&q=80") {
            placeholder(R.drawable.ic_food_placeholder)
            error(R.drawable.ic_food_placeholder)
        }
    }

    /**
     * Sets up click listeners for navigation buttons.
     */
    private fun setupClickListeners() {

        // Navigate to MenuActivity when "View Menu" is clicked
        binding.btnViewMenu.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        // Navigate to MyOrdersActivity when "My Orders" is clicked
        binding.btnMyOrders.setOnClickListener {
            startActivity(Intent(this, MyOrdersActivity::class.java))
        }

        // Navigate to AboutActivity when "About Us" is clicked
        binding.btnAboutUs.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

    }

    /**
     * Registers broadcast receivers for network and battery events.
     */
    @Suppress("DEPRECATION")
    private fun registerReceivers() {
        // Initialize and register NetworkReceiver
        networkReceiver = NetworkReceiver(binding.root)
        val networkFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, networkFilter)

        // Initialize and register BatteryReceiver
        batteryReceiver = BatteryReceiver()
        val batteryFilter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_BATTERY_OKAY)
            addAction(Intent.ACTION_POWER_CONNECTED)
        }
        registerReceiver(batteryReceiver, batteryFilter)
    }

    /**
     * Safely unregisters broadcast receivers.
     */
    private fun unregisterReceivers() {
        try {
            unregisterReceiver(networkReceiver)
            unregisterReceiver(batteryReceiver)
        } catch (e: IllegalArgumentException) {
            // Receivers were not registered, ignore
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceivers()
        binding.viewFlipper.startFlipping()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceivers()
        binding.viewFlipper.stopFlipping()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(dotUpdater)
    }

    /**
     * Data class for gallery items.
     */
    private data class GalleryItem(
        val name: String,
        val price: String,
        val description: String
    )
}
