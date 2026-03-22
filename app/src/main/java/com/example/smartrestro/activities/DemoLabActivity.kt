package com.example.smartrestro.activities

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.smartrestro.R
import com.example.smartrestro.databinding.ActivityDemoLabBinding

/**
 * DemoLabActivity — College Lab Experiment Screen
 * Demonstrates user interaction and event handling using 4 UI components:
 * 1. Spinner - Fruit selection (5 items)
 * 2. RadioGroup - Text color selection (Red, Green, Blue)
 * 3. CheckBox - Text style modifiers (Bold, Italic, Underline)
 * 4. Button - Apply button to show result
 *
 * Result is displayed in a TextView showing selected fruit with applied color and styles.
 */
class DemoLabActivity : AppCompatActivity() {

    // ViewBinding reference for activity_demo_lab layout
    private lateinit var binding: ActivityDemoLabBinding

    // Available fruits for the spinner
    private val fruits = listOf("Select Fruit", "Apple", "Mango", "Banana", "Orange", "Grapes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate layout using ViewBinding
        binding = ActivityDemoLabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up toolbar with back navigation
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Lab Experiment"

        setupSpinner()
        setupListeners()
    }

    /**
     * Populates the Spinner with fruit items.
     */
    private fun setupSpinner() {
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            fruits
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFruit.adapter = spinnerAdapter
    }

    /**
     * Sets up click listener for the Apply button.
     * The button collects all user selections and displays the result.
     */
    private fun setupListeners() {
        // Apply button click listener
        binding.btnApply.setOnClickListener {
            applySelections()
        }
    }

    /**
     * Collects all user selections from Spinner, RadioGroup, and CheckBoxes,
     * then applies them to the result TextView with appropriate styling.
     */
    private fun applySelections() {
        // 1. Get selected fruit from Spinner
        val selectedFruit = binding.spinnerFruit.selectedItem.toString()

        // Validate fruit selection
        if (selectedFruit == "Select Fruit") {
            binding.tvResult.text = "⚠️ Please select a fruit first!"
            binding.tvResult.setTextColor(ContextCompat.getColor(this, R.color.error))
            return
        }

        // 2. Get selected color from RadioGroup
        val selectedColor = when (binding.rgColor.checkedRadioButtonId) {
            R.id.rbRed -> ContextCompat.getColor(this, android.R.color.holo_red_dark)
            R.id.rbGreen -> ContextCompat.getColor(this, android.R.color.holo_green_dark)
            R.id.rbBlue -> ContextCompat.getColor(this, android.R.color.holo_blue_dark)
            else -> ContextCompat.getColor(this, R.color.text_primary)
        }

        // 3. Get selected text styles from CheckBoxes
        val isBold = binding.cbBold.isChecked
        val isItalic = binding.cbItalic.isChecked
        val isUnderline = binding.cbUnderline.isChecked

        // 4. Build result text
        val resultText = "Selected: $selectedFruit 🍎"

        // 5. Apply styles using SpannableString
        val spannableString = SpannableString(resultText)

        // Apply color to the entire text
        spannableString.setSpan(
            ForegroundColorSpan(selectedColor),
            0,
            resultText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Apply Bold and/or Italic styles
        val typefaceStyle = when {
            isBold && isItalic -> Typeface.BOLD_ITALIC
            isBold -> Typeface.BOLD
            isItalic -> Typeface.ITALIC
            else -> Typeface.NORMAL
        }

        if (typefaceStyle != Typeface.NORMAL) {
            spannableString.setSpan(
                StyleSpan(typefaceStyle),
                0,
                resultText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // Apply Underline
        if (isUnderline) {
            spannableString.setSpan(
                UnderlineSpan(),
                0,
                resultText.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // 6. Display the styled result in TextView
        binding.tvResult.text = spannableString

        // 7. Show summary of applied styles
        val stylesApplied = buildList {
            if (isBold) add("Bold")
            if (isItalic) add("Italic")
            if (isUnderline) add("Underline")
        }

        val colorName = when (binding.rgColor.checkedRadioButtonId) {
            R.id.rbRed -> "Red"
            R.id.rbGreen -> "Green"
            R.id.rbBlue -> "Blue"
            else -> "Default"
        }

        val stylesSummary = if (stylesApplied.isEmpty()) {
            "No styles"
        } else {
            stylesApplied.joinToString(", ")
        }

        binding.tvStyleInfo.text = "Applied: $colorName color, $stylesSummary"
    }

    // Handle toolbar back navigation
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
