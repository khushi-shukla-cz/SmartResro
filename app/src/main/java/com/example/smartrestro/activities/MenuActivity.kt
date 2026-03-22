package com.example.smartrestro.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartrestro.adapters.MenuAdapter
import com.example.smartrestro.databinding.ActivityMenuBinding
import com.example.smartrestro.models.FoodItem
import com.example.smartrestro.utils.DummyData

/**
 * MenuActivity — displays all available food items in a 2-column grid.
 * Uses RecyclerView with GridLayoutManager and a custom MenuAdapter.
 * Clicking a food item navigates to OrderActivity with the selected item.
 */
class MenuActivity : AppCompatActivity() {

    // ViewBinding reference for activity_menu layout
    private lateinit var binding: ActivityMenuBinding

    // Adapter for the food items RecyclerView
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using ViewBinding
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar with back navigation
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Our Menu"

        setupRecyclerView()
    }

    /**
     * Initialises the RecyclerView with a 2-column GridLayoutManager
     * and attaches the MenuAdapter with dummy food data.
     */
    private fun setupRecyclerView() {
        val foodItems = DummyData.getFoodItems()

        val spanCount = resources.getInteger(com.example.smartrestro.R.integer.menu_grid_span_count)

        // Create adapter with item-click lambda — navigates to OrderActivity
        menuAdapter = MenuAdapter(foodItems) { foodItem ->
            openOrderActivity(foodItem)
        }

        binding.rvMenu.apply {
            // 2-column grid layout
            layoutManager = GridLayoutManager(this@MenuActivity, spanCount)
            adapter = menuAdapter
            // Smooth scrolling enabled by default in RecyclerView
            setHasFixedSize(true)
        }
    }

    /**
     * Starts OrderActivity and passes the selected FoodItem via Intent.
     */
    private fun openOrderActivity(foodItem: FoodItem) {
        val intent = Intent(this, OrderActivity::class.java).apply {
            putExtra(OrderActivity.EXTRA_FOOD_ITEM, foodItem)
        }
        startActivity(intent)
    }

    // Handle Up/Back button in toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
