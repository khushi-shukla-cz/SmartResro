package com.example.smartrestro.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.smartrestro.R
import com.example.smartrestro.databinding.ActivityOrderBinding
import com.example.smartrestro.models.FoodItem
import com.example.smartrestro.models.OrderItem
import com.example.smartrestro.utils.CartManager

/**
 * OrderActivity — allows users to customise and place an order for a selected food item.
 *
 * Features:
 * - Displays food image, name and base price
 * - Quantity selector (+ / - buttons)
 * - Size selection via RadioGroup (Small / Medium / Large)
 * - Add-ons via CheckBoxes (Extra Cheese, Extra Sauce, Olives)
 * - City selection via Spinner
 * - Auto-calculated total price
 * - Order confirmation via AlertDialog
 */
class OrderActivity : AppCompatActivity() {

    // ViewBinding reference for activity_order layout
    private lateinit var binding: ActivityOrderBinding

    // The food item passed from MenuActivity
    private lateinit var foodItem: FoodItem

    // Current quantity selected by the user
    private var quantity = 1

    // Price additions based on size selection
    private val sizePrice = mapOf("Small" to 0.0, "Medium" to 50.0, "Large" to 100.0)

    // Price additions for each add-on
    private val addOnPrice = mapOf("Extra Cheese" to 30.0, "Extra Sauce" to 20.0, "Olives" to 25.0)

    companion object {
        // Intent key for passing FoodItem to this activity
        const val EXTRA_FOOD_ITEM = "extra_food_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using ViewBinding
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve the FoodItem from Intent; finish if null (safety guard)
        @Suppress("DEPRECATION")
        foodItem = intent.getSerializableExtra(EXTRA_FOOD_ITEM) as? FoodItem
            ?: run { finish(); return }

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Customise Order"

        setupUI()
        setupListeners()
        updateTotalPrice()
    }

    /**
     * Populates all UI elements with food item data and sets up the city Spinner.
     */
    private fun setupUI() {
        // Set food details
        binding.tvFoodName.text = foodItem.name
        binding.tvFoodPrice.text = "₹${foodItem.price.toInt()}"
        binding.tvDescription.text = foodItem.description
        binding.ivFoodImage.load(foodItem.imageUrl) {
            placeholder(R.drawable.ic_food_placeholder)
            error(R.drawable.ic_food_placeholder)
        }
        binding.tvQuantity.text = quantity.toString()

        // Populate city spinner
        val cities = listOf("Select City", "Mumbai", "Delhi", "Bangalore", "Hyderabad",
            "Chennai", "Kolkata", "Pune", "Ahmedabad", "Nashik")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCity.adapter = spinnerAdapter
    }

    /**
     * Attaches click listeners to all interactive elements and triggers price recalculation.
     */
    private fun setupListeners() {
        // Quantity decrement — minimum 1
        binding.btnDecrement.setOnClickListener {
            if (quantity > 1) {
                quantity--
                binding.tvQuantity.text = quantity.toString()
                updateTotalPrice()
            }
        }

        // Quantity increment — maximum 10
        binding.btnIncrement.setOnClickListener {
            if (quantity < 10) {
                quantity++
                binding.tvQuantity.text = quantity.toString()
                updateTotalPrice()
            }
        }

        // Recalculate price when size selection changes
        binding.rgSize.setOnCheckedChangeListener { _, _ ->
            updateTotalPrice()
        }

        // Recalculate price when add-on checkboxes change
        binding.cbExtraCheese.setOnCheckedChangeListener { _, _ -> updateTotalPrice() }
        binding.cbExtraSauce.setOnCheckedChangeListener { _, _ -> updateTotalPrice() }
        binding.cbOlives.setOnCheckedChangeListener { _, _ -> updateTotalPrice() }

        // Place order button
        binding.btnPlaceOrder.setOnClickListener {
            placeOrder()
        }
    }

    /**
     * Calculates and displays the total price based on:
     * base price + size surcharge + add-on prices, multiplied by quantity.
     */
    private fun updateTotalPrice() {
        val selectedSize = getSelectedSize()
        val sizeSurcharge = sizePrice[selectedSize] ?: 0.0
        val addOnTotal = getSelectedAddOns().sumOf { addOnPrice[it] ?: 0.0 }
        val total = (foodItem.price + sizeSurcharge + addOnTotal) * quantity
        binding.tvTotalPrice.text = "Total: ₹${String.format("%.2f", total)}"
    }

    /**
     * Returns the currently selected size as a string.
     */
    private fun getSelectedSize(): String {
        return when (binding.rgSize.checkedRadioButtonId) {
            R.id.rbMedium -> "Medium"
            R.id.rbLarge -> "Large"
            else -> "Small" // Default to Small
        }
    }

    /**
     * Returns a list of selected add-on names.
     */
    private fun getSelectedAddOns(): List<String> {
        val addOns = mutableListOf<String>()
        if (binding.cbExtraCheese.isChecked) addOns.add("Extra Cheese")
        if (binding.cbExtraSauce.isChecked) addOns.add("Extra Sauce")
        if (binding.cbOlives.isChecked) addOns.add("Olives")
        return addOns
    }

    /**
     * Validates the order, adds it to CartManager, and shows a confirmation AlertDialog.
     */
    private fun placeOrder() {
        // Validate city selection
        if (binding.spinnerCity.selectedItemPosition == 0) {
            Toast.makeText(this, "Please select a delivery city", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedSize = getSelectedSize()
        val selectedAddOns = getSelectedAddOns()
        val sizeSurcharge = sizePrice[selectedSize] ?: 0.0
        val addOnTotal = selectedAddOns.sumOf { addOnPrice[it] ?: 0.0 }
        val totalPrice = (foodItem.price + sizeSurcharge + addOnTotal) * quantity
        val selectedCity = binding.spinnerCity.selectedItem.toString()

        // Build the OrderItem
        val orderItem = OrderItem(
            foodItem = foodItem,
            quantity = quantity,
            size = selectedSize,
            addOns = selectedAddOns,
            totalPrice = totalPrice
        )

        // Add to global cart
        CartManager.addItem(orderItem)

        // Build order summary string for the dialog
        val addOnsText = if (selectedAddOns.isEmpty()) "None" else selectedAddOns.joinToString(", ")
        val summary = """
            🍽 ${foodItem.name}
            📦 Size: $selectedSize
            🔢 Quantity: $quantity
            ➕ Add-ons: $addOnsText
            🏙 Delivery to: $selectedCity
            💰 Total: ₹${String.format("%.2f", totalPrice)}
        """.trimIndent()

        // Show confirmation AlertDialog
        AlertDialog.Builder(this)
            .setTitle("Order Confirmed! 🎉")
            .setMessage(summary)
            .setPositiveButton("View My Orders") { dialog, _ ->
                dialog.dismiss()
                finish() // Return to menu
            }
            .setNegativeButton("Order More") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setCancelable(false)
            .show()
    }

    // Handle Up/Back button in toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
