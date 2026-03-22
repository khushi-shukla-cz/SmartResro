package com.example.smartrestro.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * BatteryReceiver — monitors battery status changes.
 * Shows Toast notifications for low battery, battery okay, and power connected events.
 */
class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // Validate context and intent
        if (context == null || intent == null) return

        // Handle different battery-related actions
        when (intent.action) {
            Intent.ACTION_BATTERY_LOW -> {
                // Battery is running low — warn user to save cart
                Toast.makeText(
                    context,
                    "🔋 Battery low! Save your cart before\nyou lose power.",
                    Toast.LENGTH_LONG
                ).show()
            }

            Intent.ACTION_BATTERY_OKAY -> {
                // Battery level is okay again — reassure user
                Toast.makeText(
                    context,
                    "🔋 Battery restored. Happy ordering!",
                    Toast.LENGTH_LONG
                ).show()
            }

            Intent.ACTION_POWER_CONNECTED -> {
                // Device is now charging — encourage menu exploration
                Toast.makeText(
                    context,
                    "⚡ Charging! Great time to explore\nour menu 🍕",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
