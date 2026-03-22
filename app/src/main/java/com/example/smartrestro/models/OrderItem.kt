package com.example.smartrestro.models

import java.io.Serializable

/**
 * Data class representing an item added to the cart/order.
 * Contains the food item reference plus order-specific details.
 */
data class OrderItem(
    val foodItem: FoodItem,
    val quantity: Int,
    val size: String,
    val addOns: List<String>,
    val totalPrice: Double
) : Serializable
