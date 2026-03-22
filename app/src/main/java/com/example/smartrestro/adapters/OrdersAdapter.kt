package com.example.smartrestro.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.smartrestro.databinding.ItemOrderCardBinding
import com.example.smartrestro.models.OrderItem

/**
 * OrdersAdapter — RecyclerView adapter for the My Orders / Cart screen.
 *
 * Each row displays the ordered item's food name, size, quantity, add-ons,
 * total price, and a remove button.
 *
 * @param orderItems Mutable list of OrderItem objects (so items can be removed).
 * @param onRemoveClick Lambda called when the remove button is clicked; passes the adapter position.
 */
class OrdersAdapter(
    private val orderItems: MutableList<OrderItem>,
    private val onRemoveClick: (Int) -> Unit
) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    /**
     * ViewHolder for a single order item row.
     */
    inner class OrderViewHolder(private val binding: ItemOrderCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds an OrderItem to the views in this ViewHolder.
         */
        fun bind(orderItem: OrderItem, position: Int) {
            binding.apply {
                // Food image and name
                ivOrderImage.load(orderItem.foodItem.imageUrl) {
                    placeholder(com.example.smartrestro.R.drawable.ic_food_placeholder)
                    error(com.example.smartrestro.R.drawable.ic_food_placeholder)
                }
                tvOrderFoodName.text = orderItem.foodItem.name

                // Order details: size and quantity
                tvOrderSize.text = "Size: ${orderItem.size}"
                tvOrderQuantity.text = "Qty: ${orderItem.quantity}"

                // Add-ons — display "None" if empty
                val addOnsText = if (orderItem.addOns.isEmpty()) "Add-ons: None"
                else "Add-ons: ${orderItem.addOns.joinToString(", ")}"
                tvOrderAddOns.text = addOnsText

                // Total price for this order item
                tvOrderTotal.text = "₹${String.format("%.2f", orderItem.totalPrice)}"

                // Remove button — calls activity callback with current adapter position
                btnRemove.setOnClickListener {
                    onRemoveClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orderItems[position], position)
    }

    override fun getItemCount(): Int = orderItems.size

    /**
     * Removes an item at the given position and notifies the RecyclerView.
     * Called after the item has already been removed from CartManager.
     */
    fun removeItem(position: Int) {
        if (position in orderItems.indices) {
            orderItems.removeAt(position)
            notifyItemRemoved(position)
            // Rebind items below to fix stale position references
            notifyItemRangeChanged(position, orderItems.size)
        }
    }

    /**
     * Replaces the entire dataset and refreshes the RecyclerView.
     * Called in onResume to sync with CartManager.
     */
    fun updateData(newItems: MutableList<OrderItem>) {
        orderItems.clear()
        orderItems.addAll(newItems)
        notifyDataSetChanged()
    }
}
