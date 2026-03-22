package com.example.smartrestro.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * NetworkReceiver — monitors internet connectivity changes.
 * Shows Snackbar notifications when connection is lost or restored.
 */
class NetworkReceiver(private val rootView: View) : BroadcastReceiver() {

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context?, intent: Intent?) {
        // Validate context and intent
        if (context == null || intent == null) return

        // Check if action is connectivity change
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            // Check current internet availability
            if (isInternetAvailable(context)) {
                // Internet is available — show success message
                showInternetRestoredSnackbar()
            } else {
                // Internet is lost — show warning message
                showInternetLostSnackbar()
            }
        }
    }

    /**
     * Checks if internet connection is currently available.
     */
    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return false

        // For Android M (API 23) and above, use NetworkCapabilities
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            
            // Check if network has internet capability
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                   capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            // For older versions, use deprecated NetworkInfo API
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            return networkInfo?.isConnected == true
        }
    }

    /**
     * Shows Snackbar when internet connection is lost.
     */
    private fun showInternetLostSnackbar() {
        Snackbar.make(
            rootView,
            "📡 No internet connection!\nFood images & ordering unavailable.",
            Snackbar.LENGTH_LONG
        ).setAction("DISMISS") {
            // Dismiss the Snackbar when action is clicked
        }.setBackgroundTint(
            rootView.context.getColor(android.R.color.holo_red_dark)
        ).show()
    }

    /**
     * Shows Snackbar when internet connection is restored.
     */
    private fun showInternetRestoredSnackbar() {
        Snackbar.make(
            rootView,
            "✅ Back online! You can now place orders.",
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(
            rootView.context.getColor(android.R.color.holo_green_dark)
        ).show()
    }
}
