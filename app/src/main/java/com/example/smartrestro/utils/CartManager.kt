package com.example.smartrestro.utils

import com.example.smartrestro.models.OrderItem

/**
 * Singleton object that manages the in-memory cart/order list.
 * Acts as a simple shared state store for the app session.
 * In a production app, this would be replaced with a Room database or ViewModel with LiveData.
 */
object CartManager {

    // Mutable list holding all order items added by the user
    private val cartItems: MutableList<OrderItem> = mutableListOf()

    /** Add an order item to the cart */
    fun addItem(item: OrderItem) {
        cartItems.add(item)
    }

    /** Remove an order item from the cart by its position index */
    fun removeItem(index: Int) {
        if (index in cartItems.indices) {
            cartItems.removeAt(index)
        }
    }

    /** Get a read-only copy of all cart items */
    fun getItems(): List<OrderItem> = cartItems.toList()

    /** Calculate the total price of all items in the cart */
    fun getTotalPrice(): Double = cartItems.sumOf { it.totalPrice }

    /** Returns the number of items in the cart */
    fun getItemCount(): Int = cartItems.size

    /** Clear all items from the cart */
    fun clearCart() {
        cartItems.clear()
    }
}
