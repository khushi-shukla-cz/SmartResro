# Android Experiments - Code Extracts

## Experiment 1: Event Handling with UI Components
**Components: Spinner, RadioButton, CheckBox, Button**

---

### 1.1 XML Layout (activity_order.xml)

#### Spinner for City Selection
```xml
<Spinner
    android:id="@+id/spinnerCity"
    android:layout_width="match_parent"
    android:layout_height="52dp"
    android:background="@drawable/bg_spinner"
    android:paddingHorizontal="12dp" />
```

#### RadioGroup for Size Selection
```xml
<RadioGroup
    android:id="@+id/rgSize"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

    <com.google.android.material.radiobutton.MaterialRadioButton
        android:id="@+id/rbSmall"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Small\n+₹0"
        android:checked="true"
        android:buttonTint="@color/primary" />

    <com.google.android.material.radiobutton.MaterialRadioButton
        android:id="@+id/rbMedium"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Medium\n+₹50"
        android:buttonTint="@color/primary" />

    <com.google.android.material.radiobutton.MaterialRadioButton
        android:id="@+id/rbLarge"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="Large\n+₹100"
        android:buttonTint="@color/primary" />
</RadioGroup>
```

#### CheckBoxes for Add-ons
```xml
<com.google.android.material.checkbox.MaterialCheckBox
    android:id="@+id/cbExtraCheese"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="🧀  Extra Cheese  (+₹30)"
    android:buttonTint="@color/primary" />

<com.google.android.material.checkbox.MaterialCheckBox
    android:id="@+id/cbExtraSauce"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="🍅  Extra Sauce  (+₹20)"
    android:buttonTint="@color/primary" />

<com.google.android.material.checkbox.MaterialCheckBox
    android:id="@+id/cbOlives"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="🫒  Olives  (+₹25)"
    android:buttonTint="@color/primary" />
```

#### Button for Action
```xml
<com.google.android.material.button.MaterialButton
    android:id="@+id/btnPlaceOrder"
    android:layout_width="match_parent"
    android:layout_height="52dp"
    android:text="Place Order 🚀"
    android:textStyle="bold"
    app:cornerRadius="12dp" />
```

---

### 1.2 Kotlin Code (OrderActivity.kt)

#### Setup Spinner
```kotlin
private fun setupUI() {
    // Populate city spinner
    val cities = listOf("Select City", "Mumbai", "Delhi", "Bangalore", 
        "Hyderabad", "Chennai", "Kolkata", "Pune", "Ahmedabad")
    val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    binding.spinnerCity.adapter = spinnerAdapter
}
```

#### Handle RadioButton Selection
```kotlin
private fun setupListeners() {
    // Recalculate price when size selection changes
    binding.rgSize.setOnCheckedChangeListener { _, _ ->
        updateTotalPrice()
    }
}

private fun getSelectedSize(): String {
    return when (binding.rgSize.checkedRadioButtonId) {
        R.id.rbMedium -> "Medium"
        R.id.rbLarge -> "Large"
        else -> "Small"
    }
}
```

#### Handle CheckBox Events
```kotlin
private fun setupListeners() {
    // Recalculate price when add-on checkboxes change
    binding.cbExtraCheese.setOnCheckedChangeListener { _, _ -> updateTotalPrice() }
    binding.cbExtraSauce.setOnCheckedChangeListener { _, _ -> updateTotalPrice() }
    binding.cbOlives.setOnCheckedChangeListener { _, _ -> updateTotalPrice() }
}

private fun getSelectedAddOns(): List<String> {
    val addOns = mutableListOf<String>()
    if (binding.cbExtraCheese.isChecked) addOns.add("Extra Cheese")
    if (binding.cbExtraSauce.isChecked) addOns.add("Extra Sauce")
    if (binding.cbOlives.isChecked) addOns.add("Olives")
    return addOns
}
```

#### Handle Button Click
```kotlin
private fun setupListeners() {
    // Place order button
    binding.btnPlaceOrder.setOnClickListener {
        placeOrder()
    }
}

private fun placeOrder() {
    // Validate city selection
    if (binding.spinnerCity.selectedItemPosition == 0) {
        Toast.makeText(this, "Please select a delivery city", Toast.LENGTH_SHORT).show()
        return
    }
    
    // Process order logic here
    val orderSummary = """
        Food: ${foodItem.name}
        Quantity: $quantity
        Size: ${getSelectedSize()}
        Add-ons: ${getSelectedAddOns().joinToString(", ")}
        City: ${binding.spinnerCity.selectedItem}
        Total: ${binding.tvTotalPrice.text}
    """.trimIndent()
    
    Toast.makeText(this, "Order Placed Successfully!", Toast.LENGTH_SHORT).show()
}
```

---

## Experiment 2: ViewFlipper & Image Gallery
**Components: ViewFlipper (Auto-Flip), ImageView (Gallery), AlertDialog**

---

### 2.1 XML Layout (activity_home.xml)

#### ViewFlipper for Auto-Flipping Banner
```xml
<ViewFlipper
    android:id="@+id/viewFlipper"
    android:layout_width="match_parent"
    android:layout_height="220dp"
    android:autoStart="true"
    android:flipInterval="2500"
    android:inAnimation="@android:anim/slide_in_left"
    android:outAnimation="@android:anim/slide_out_right">

    <!-- Slide 1 -->
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="220dp">

        <ImageView
            android:id="@+id/ivSlide1"
            android:layout_width="match_parent"
            android:layout_height="220dp"
            android:scaleType="centerCrop" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom"
            android:orientation="vertical"
            android:padding="20dp">

            <TextView
                android:text="🍕 50% OFF on Pizzas Today!"
                android:textColor="@android:color/white"
                android:textSize="20sp"
                android:textStyle="bold" />
        </LinearLayout>
    </FrameLayout>

    <!-- Slide 2, 3, 4 similar structure -->
</ViewFlipper>
```

#### Dot Indicators
```xml
<LinearLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:gravity="center">

    <ImageView
        android:id="@+id/dot1"
        android:layout_width="8dp"
        android:layout_height="8dp"
        android:src="@drawable/dot_indicator_active" />

    <ImageView
        android:id="@+id/dot2"
        android:layout_width="8dp"
        android:layout_height="8dp"
        android:src="@drawable/dot_indicator" />

    <ImageView
        android:id="@+id/dot3"
        android:layout_width="8dp"
        android:layout_height="8dp"
        android:src="@drawable/dot_indicator" />

    <ImageView
        android:id="@+id/dot4"
        android:layout_width="8dp"
        android:layout_height="8dp"
        android:src="@drawable/dot_indicator" />
</LinearLayout>
```

#### Image Gallery with HorizontalScrollView
```xml
<HorizontalScrollView
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:scrollbars="none">

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <!-- Gallery Item 1 -->
        <com.google.android.material.card.MaterialCardView
            android:layout_width="150dp"
            android:layout_height="120dp"
            app:cardCornerRadius="12dp">

            <ImageView
                android:id="@+id/galleryImg1"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="centerCrop" />

            <TextView
                android:text="Margherita Pizza"
                android:layout_gravity="bottom"
                android:background="#80000000"
                android:textColor="@android:color/white"
                android:padding="8dp" />
        </com.google.android.material.card.MaterialCardView>

        <!-- Gallery Items 2, 3, 4, 5 similar structure -->
    </LinearLayout>
</HorizontalScrollView>
```

---

### 2.2 Kotlin Code (HomeActivity.kt)

#### Setup ViewFlipper with Auto-Flip
```kotlin
private lateinit var handler: Handler
private lateinit var dotUpdater: Runnable

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
```

#### Load Images into ViewFlipper
```kotlin
private fun loadViewFlipperImages() {
    binding.ivSlide1.load("https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800") {
        crossfade(true)
        placeholder(R.drawable.ic_food_placeholder)
    }
    
    binding.ivSlide2.load("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800") {
        crossfade(true)
        placeholder(R.drawable.ic_food_placeholder)
    }
    
    binding.ivSlide3.load("https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=800") {
        crossfade(true)
        placeholder(R.drawable.ic_food_placeholder)
    }
    
    binding.ivSlide4.load("https://images.unsplash.com/photo-1551024506-0bccd828d307?w=800") {
        crossfade(true)
        placeholder(R.drawable.ic_food_placeholder)
    }
}
```

#### Update Dot Indicators
```kotlin
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
```

#### Setup Gallery with Click Events
```kotlin
private fun setupGallery() {
    // Load gallery images
    binding.galleryImg1.load("https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400") {
        crossfade(true)
        placeholder(R.drawable.ic_food_placeholder)
    }
    
    binding.galleryImg2.load("https://images.unsplash.com/photo-1550547660-d9450f859349?w=400") {
        crossfade(true)
        placeholder(R.drawable.ic_food_placeholder)
    }
    
    // ... (load remaining images)

    // Set click listeners
    binding.galleryImg1.setOnClickListener { showGalleryItemInfo(0) }
    binding.galleryImg2.setOnClickListener { showGalleryItemInfo(1) }
    binding.galleryImg3.setOnClickListener { showGalleryItemInfo(2) }
    binding.galleryImg4.setOnClickListener { showGalleryItemInfo(3) }
    binding.galleryImg5.setOnClickListener { showGalleryItemInfo(4) }
}
```

#### Show Gallery Item Info with AlertDialog
```kotlin
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
```

#### Gallery Data Class
```kotlin
private data class GalleryItem(
    val name: String,
    val price: String,
    val description: String
)

private val galleryItems = listOf(
    GalleryItem("Margherita Pizza", "₹299", "Classic Italian pizza with fresh mozzarella"),
    GalleryItem("Classic Burger", "₹249", "Juicy beef patty with cheese and veggies"),
    GalleryItem("Penne Pasta", "₹279", "Pasta in rich tomato basil sauce"),
    GalleryItem("Caesar Salad", "₹199", "Fresh romaine lettuce with Caesar dressing"),
    GalleryItem("Chocolate Cake", "₹159", "Decadent chocolate layer cake")
)
```

#### Lifecycle Management
```kotlin
override fun onResume() {
    super.onResume()
    binding.viewFlipper.startFlipping()
}

override fun onPause() {
    super.onPause()
    binding.viewFlipper.stopFlipping()
}

override fun onDestroy() {
    super.onDestroy()
    handler.removeCallbacks(dotUpdater)
}
```

---

## Important Notes:

### Experiment 1 Key Points:
- **Spinner**: Use `ArrayAdapter` to populate dropdown options
- **RadioButton**: Use `RadioGroup` to ensure single selection
- **CheckBox**: Each checkbox works independently, multiple selections allowed
- **Button**: Use `setOnClickListener` for click events

### Experiment 2 Key Points:
- **ViewFlipper**: `autoStart="true"` and `flipInterval="2500"` for auto-flip
- **Manual Navigation**: `showNext()` method on ViewFlipper click
- **Dot Indicators**: Update based on `displayedChild` property
- **Gallery**: `HorizontalScrollView` for horizontal scrolling
- **AlertDialog**: Show item details on image click
- **Lifecycle**: Start/stop flipping and cleanup handlers

### Dependencies Required:
```gradle
// Coil for image loading
implementation("io.coil-kt:coil:2.4.0")

// Material Design 3
implementation("com.google.android.material:material:1.11.0")
```

---

## Experiment 3: BroadcastReceiver for System Events
**Components: BroadcastReceiver, IntentFilter, Snackbar, Toast**

---

### 3.1 NetworkReceiver.kt

#### Complete NetworkReceiver Class
```kotlin
package com.example.smartrestro.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import com.google.android.material.snackbar.Snackbar

class NetworkReceiver(private val rootView: View) : BroadcastReceiver() {

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            if (isInternetAvailable(context)) {
                showInternetRestoredSnackbar()
            } else {
                showInternetLostSnackbar()
            }
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) 
            as? ConnectivityManager ?: return false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) 
                ?: return false
            
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                   capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            return networkInfo?.isConnected == true
        }
    }

    private fun showInternetLostSnackbar() {
        Snackbar.make(
            rootView,
            "📡 No internet connection!\\nFood images & ordering unavailable.",
            Snackbar.LENGTH_LONG
        ).setAction("DISMISS") {
            // Dismiss when clicked
        }.setBackgroundTint(
            rootView.context.getColor(android.R.color.holo_red_dark)
        ).show()
    }

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
```

---

### 3.2 BatteryReceiver.kt

#### Complete BatteryReceiver Class
```kotlin
package com.example.smartrestro.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        when (intent.action) {
            Intent.ACTION_BATTERY_LOW -> {
                Toast.makeText(
                    context,
                    "🔋 Battery low! Save your cart before\\nyou lose power.",
                    Toast.LENGTH_LONG
                ).show()
            }

            Intent.ACTION_BATTERY_OKAY -> {
                Toast.makeText(
                    context,
                    "🔋 Battery restored. Happy ordering!",
                    Toast.LENGTH_LONG
                ).show()
            }

            Intent.ACTION_POWER_CONNECTED -> {
                Toast.makeText(
                    context,
                    "⚡ Charging! Great time to explore\\nour menu 🍕",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
```

---

### 3.3 Activity Integration (HomeActivity.kt)

#### Add Imports
```kotlin
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.example.smartrestro.receivers.BatteryReceiver
import com.example.smartrestro.receivers.NetworkReceiver
```

#### Declare Receivers as Class Members
```kotlin
class HomeActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHomeBinding
    
    // BroadcastReceivers for monitoring system events
    private lateinit var networkReceiver: NetworkReceiver
    private lateinit var batteryReceiver: BatteryReceiver
    
    // ... other members
}
```

#### Register Receivers
```kotlin
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
```

#### Unregister Receivers
```kotlin
private fun unregisterReceivers() {
    try {
        unregisterReceiver(networkReceiver)
        unregisterReceiver(batteryReceiver)
    } catch (e: IllegalArgumentException) {
        // Receivers were not registered, ignore
    }
}
```

#### Lifecycle Methods
```kotlin
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
```

---

### 3.4 AndroidManifest.xml

#### Add Permission
```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

#### Register NetworkReceiver
```xml
<receiver
    android:name=".receivers.NetworkReceiver"
    android:exported="false">
    <intent-filter>
        <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
    </intent-filter>
</receiver>
```

#### Register BatteryReceiver
```xml
<receiver
    android:name=".receivers.BatteryReceiver"
    android:exported="false">
    <intent-filter>
        <action android:name="android.intent.action.BATTERY_LOW" />
        <action android:name="android.intent.action.BATTERY_OKAY" />
        <action android:name="android.intent.action.ACTION_POWER_CONNECTED" />
    </intent-filter>
</receiver>
```

---

## Experiment 3 Key Points:

### BroadcastReceiver Basics:
- **BroadcastReceiver**: Component that responds to system-wide broadcast announcements
- **IntentFilter**: Specifies which broadcast actions a receiver should listen for
- **Dynamic vs Static**: Receivers can be registered in manifest (static) or in code (dynamic)
- **onReceive()**: Callback method invoked when broadcast is received

### System Broadcasts Used:
- **CONNECTIVITY_ACTION**: Fired when network connectivity changes
- **ACTION_BATTERY_LOW**: Fired when battery is running low
- **ACTION_BATTERY_OKAY**: Fired when battery returns to okay level
- **ACTION_POWER_CONNECTED**: Fired when device is plugged in for charging

### Best Practices:
- **Null Checks**: Always validate context and intent in onReceive()
- **Lifecycle Management**: Register in onResume(), unregister in onPause()
- **Try-Catch**: Handle IllegalArgumentException when unregistering
- **Permissions**: Require ACCESS_NETWORK_STATE for connectivity monitoring
- **UI Updates**: Use Snackbar/Toast for user feedback
- **Deprecation**: Use @Suppress("DEPRECATION") for CONNECTIVITY_ACTION (works on SDK 24+)

### Testing:
- **Network**: Turn WiFi/Mobile data on/off to trigger connectivity broadcasts
- **Battery**: Connect/disconnect charger, or use ADB commands:
  ```bash
  adb shell dumpsys battery set level 10  # Simulate low battery
  adb shell dumpsys battery set level 80  # Simulate okay battery
  adb shell dumpsys battery set ac 1      # Simulate charging
  adb shell dumpsys battery reset         # Reset to actual state
  ```

