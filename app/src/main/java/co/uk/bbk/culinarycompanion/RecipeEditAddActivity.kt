package co.uk.bbk.culinarycompanion

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import co.uk.bbk.culinarycompanion.databinding.ActivityRecipeEditAddBinding

class RecipeEditAddActivity : ComponentActivity() {

    private lateinit var binding: ActivityRecipeEditAddBinding
    private val viewModel: RecipeEditAddViewModel by viewModels()
    private var selectedImageName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeEditAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCategorySpinner()
        setupImageSpinner()

        binding.saveButton.setOnClickListener {
            val title = binding.recipeTitleInput.text.toString().trim()
            val ingredients = binding.ingredientsInput.text.toString().trim()
            val instructions = binding.instructionsInput.text.toString().trim()
            val category = Category.valueOf(binding.categorySpinner.selectedItem.toString())

            if (title.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty() && selectedImageName != null) {
                val newRecipe = Recipe(
                    title = title,
                    ingredients = ingredients,
                    instructions = instructions,
                    category = category,
                    imageUri = selectedImageName!!
                )
                viewModel.insertRecipe(newRecipe) {
                    runOnUiThread {
                        Toast.makeText(this, "Recipe saved", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            } else {
                // Validation feedback
                binding.recipeTitleInput.error = if (title.isEmpty()) "Required" else null
                binding.ingredientsInput.error = if (ingredients.isEmpty()) "Required" else null
                binding.instructionsInput.error = if (instructions.isEmpty()) "Required" else null
                if (selectedImageName == null) {
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun setupCategorySpinner() {
        val categoryNames = Category.entries.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter
    }

    private fun setupImageSpinner() {
        viewModel.imageNames.observe(this) { imageNames ->
            val placeholder = "Select image"
            val displayList = listOf(placeholder) + imageNames

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                displayList
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            binding.imageSpinner.adapter = adapter
            binding.imageSpinner.setSelection(0) // Show placeholder text initially

            binding.imageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position > 0) {
                        selectedImageName = imageNames[position - 1]
                        updateImagePreview()
                    } else {
                        selectedImageName = null
                        // Keep placeholder_image visible — no change needed
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedImageName = null
                }
            }
        }

        viewModel.loadImageOptions()
    }


    private fun updateImagePreview() {
        val name = selectedImageName ?: return
        val resId = resources.getIdentifier(name, "drawable", packageName)
        binding.recipeImageView.setImageResource(
            if (resId != 0) resId else R.drawable.placeholder_image
        )
    }
}
