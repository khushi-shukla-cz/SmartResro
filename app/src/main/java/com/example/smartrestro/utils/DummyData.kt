package com.example.smartrestro.utils

import com.example.smartrestro.models.FoodItem

/**
 * Utility object that provides dummy/mock data for the menu.
 * In a real app, this data would come from a remote API or local database.
 */
object DummyData {

    /**
     * Returns a list of 8 dummy food items to populate the Menu screen.
     * Each item uses a real image URL for the menu card thumbnail.
     */
    fun getFoodItems(): List<FoodItem> = listOf(
        FoodItem(
            id = 1,
            name = "Margherita Pizza",
            price = 299.0,
            rating = 4.5f,
            category = "Pizza",
            description = "Classic pizza with tomato sauce, fresh mozzarella, and basil leaves on a crispy thin crust.",
            imageUrl = "https://content.jdmagicbox.com/v2/comp/mumbai/c2/022pxx22.xx22.140701152423.t6c2/catalogue/super-pizza-vangani-mumbai-pizza-outlets-4doyfat.jpg"
        ),
        FoodItem(
            id = 2,
            name = "Chicken Burger",
            price = 199.0,
            rating = 4.3f,
            category = "Burgers",
            description = "Juicy grilled chicken patty with lettuce, tomato, and our signature sauce in a toasted bun.",
            imageUrl = "https://images.unsplash.com/photo-1550547660-d9450f859349?auto=format&fit=crop&w=800&q=80"
        ),
        FoodItem(
            id = 3,
            name = "Pasta Alfredo",
            price = 249.0,
            rating = 4.6f,
            category = "Pasta",
            description = "Creamy Alfredo sauce with fettuccine pasta, parmesan cheese, and garlic butter.",
            imageUrl = "https://www.sharmispassions.com/wp-content/uploads/2015/12/alfredopasta5-500x500.jpg"
        ),
        FoodItem(
            id = 4,
            name = "Garden Salad",
            price = 149.0,
            rating = 4.2f,
            category = "Salads",
            description = "Fresh mixed greens, cherry tomatoes, cucumber, olives, and feta with balsamic dressing.",
            imageUrl = "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe?auto=format&fit=crop&w=800&q=80"
        ),
        FoodItem(
            id = 5,
            name = "Grilled Chicken",
            price = 349.0,
            rating = 4.7f,
            category = "Mains",
            description = "Tender grilled chicken breast marinated in herbs and spices, served with roasted vegetables.",
            imageUrl = "https://www.budgetbytes.com/wp-content/uploads/2024/06/Grilled-Chicken-Overhead-500x500.jpg"
        ),
        FoodItem(
            id = 6,
            name = "Chocolate Lava Cake",
            price = 179.0,
            rating = 4.8f,
            category = "Desserts",
            description = "Warm chocolate cake with a molten center, served with a scoop of vanilla ice cream.",
            imageUrl = "https://jessiebakescakes.com/wp-content/uploads/2025/02/chocolate-covered-strawberry-lava-cakes-6-min-scaled.jpg"
        ),
        FoodItem(
            id = 7,
            name = "Veg Spring Rolls",
            price = 129.0,
            rating = 4.1f,
            category = "Starters",
            description = "Crispy spring rolls stuffed with fresh vegetables and glass noodles, served with sweet chili dip.",
            imageUrl = "https://d1mxd7n691o8sz.cloudfront.net/static/recipe/recipe/2023-12/Vegetable-Spring-Rolls-2-1-906001560ca545c8bc72baf473f230b4.jpg"
        ),
        FoodItem(
            id = 8,
            name = "Mango Smoothie",
            price = 119.0,
            rating = 4.4f,
            category = "Drinks",
            description = "Thick and creamy mango smoothie made with fresh Alphonso mangoes and a hint of cardamom.",
            imageUrl = "https://images.unsplash.com/photo-1525385133512-2f3bdd039054?auto=format&fit=crop&w=800&q=80"
        )
    )
}
