package com.example.smartrestro.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.smartrestro.databinding.ItemMenuCardBinding
import com.example.smartrestro.models.FoodItem

/**
 * MenuAdapter — RecyclerView adapter for the food menu grid.
 *
 * Displays each FoodItem in a Material CardView with image, name, price, rating,
 * and an "Add to Cart" button.
 *
 * @param foodItems List of FoodItem objects to display.
 * @param onItemClick Lambda called when a card or "Add to Cart" is clicked, passing the FoodItem.
 */
class MenuAdapter(
    private val foodItems: List<FoodItem>,
    private val onItemClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    /**
     * ViewHolder that holds ViewBinding references for a single menu card.
     */
    inner class MenuViewHolder(private val binding: ItemMenuCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds a FoodItem to the views in this ViewHolder.
         */
        fun bind(foodItem: FoodItem) {
            binding.apply {
                // Load food image from URL with fallback placeholder
                ivFoodImage.load(foodItem.imageUrl) {
                    placeholder(com.example.smartrestro.R.drawable.ic_food_placeholder)
                    error(com.example.smartrestro.R.drawable.ic_food_placeholder)
                }

                // Set food name and truncate if too long
                tvFoodName.text = foodItem.name

                // Format price with rupee symbol
                tvFoodPrice.text = "₹${foodItem.price.toInt()}"

                // Set star rating bar value
                ratingBar.rating = foodItem.rating

                // Display numeric rating alongside the bar
                tvRating.text = foodItem.rating.toString()

                // Category chip/label
                tvCategory.text = foodItem.category

                // Clicking the card opens the Order screen
                root.setOnClickListener { onItemClick(foodItem) }

                // Clicking "Add to Cart" also opens the Order screen
                btnAddToCart.setOnClickListener { onItemClick(foodItem) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        // Inflate the item layout using ViewBinding
        val binding = ItemMenuCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(foodItems[position])
    }

    override fun getItemCount(): Int = foodItems.size
}
