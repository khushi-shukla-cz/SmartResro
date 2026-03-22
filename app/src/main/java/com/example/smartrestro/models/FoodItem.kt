package com.example.smartrestro.models

import java.io.Serializable

/**
 * Data class representing a food item in the restaurant menu.
 * Implements Serializable so it can be passed between Activities via Intent.
 */
data class FoodItem(
    val id: Int,
    val name: String,
    val price: Double,
    val rating: Float,
    val category: String,
    val description: String,
    val imageUrl: String // Remote image URL for the food image
) : Serializable
