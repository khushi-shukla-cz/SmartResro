package com.example.smartrestro.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartrestro.adapters.OrdersAdapter
import com.example.smartrestro.databinding.ActivityMyOrdersBinding
import com.example.smartrestro.utils.CartManager

/**
 * MyOrdersActivity — displays all items that have been added to the cart.
 *
 * Features:
 * - RecyclerView list of ordered items
 * - Total price summary
 * - Remove item functionality
 * - Empty state message when cart is empty
 */
class MyOrdersActivity : AppCompatActivity() {

    // ViewBinding reference for activity_my_orders layout
    private lateinit var binding: ActivityMyOrdersBinding

    // Adapter for the orders list RecyclerView
    private lateinit var ordersAdapter: OrdersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using ViewBinding
        binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar with back navigation
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "My Orders"

        setupRecyclerView()
        updateUI()
    }

    /**
     * Initialises the RecyclerView with a LinearLayoutManager and OrdersAdapter.
     * The remove-item lambda updates the cart and refreshes the UI.
     */
    private fun setupRecyclerView() {
        ordersAdapter = OrdersAdapter(CartManager.getItems().toMutableList()) { position ->
            // Remove item from CartManager at the given position
            CartManager.removeItem(position)
            ordersAdapter.removeItem(position)
            updateUI()
        }

        binding.rvOrders.apply {
            layoutManager = LinearLayoutManager(this@MyOrdersActivity)
            adapter = ordersAdapter
            setHasFixedSize(false)
        }
    }

    /**
     * Updates the total price display and toggles the empty state view
     * based on whether the cart has items.
     */
    private fun updateUI() {
        val totalPrice = CartManager.getTotalPrice()
        val itemCount = CartManager.getItemCount()

        if (itemCount == 0) {
            // Show empty state
            binding.rvOrders.visibility = View.GONE
            binding.layoutTotal.visibility = View.GONE
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            // Show order list and total
            binding.rvOrders.visibility = View.VISIBLE
            binding.layoutTotal.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
            binding.tvTotalAmount.text = "Grand Total: ₹${String.format("%.2f", totalPrice)}"
            binding.tvItemCount.text = "$itemCount item(s) in cart"
        }
    }

    // Refresh UI when returning to this screen (e.g., after adding more items)
    override fun onResume() {
        super.onResume()
        ordersAdapter.updateData(CartManager.getItems().toMutableList())
        updateUI()
    }

    // Handle Up/Back button in toolbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
