package co.uk.bbk.culinarycompanion

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.content.IntentCompat
import co.uk.bbk.culinarycompanion.databinding.ActivityRecipeEditAddBinding

/**
 * Activity for adding a new recipe or editing an existing one.
 * Allows entry of title, ingredients, instructions, category and selection if an image.
 */
class RecipeEditAddActivity : ComponentActivity() {

    private lateinit var binding: ActivityRecipeEditAddBinding
    private val viewModel: RecipeEditAddViewModel by viewModels()
    private var selectedImageName: String? = null
    private var recipeToEdit: Recipe? = null

    /**
     * Initialises layout, pre-fills fields if editing and sets up save/cancel actions.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecipeEditAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCategorySpinner()
        setupImageSpinner()
        viewModel.loadImageOptions()

        recipeToEdit = IntentCompat.getSerializableExtra(intent, "recipe", Recipe::class.java)

        recipeToEdit?.let { recipe ->
            binding.recipeTitleInput.setText(recipe.title)
            binding.ingredientsInput.setText(recipe.ingredients)
            binding.instructionsInput.setText(recipe.instructions)
            binding.categorySpinner.setSelection(Category.values().indexOf(recipe.category))

            selectedImageName = recipe.imageUri
            updateImagePreview()
        }

        binding.saveButton.setOnClickListener {
            val title = binding.recipeTitleInput.text.toString().trim()
            val ingredients = binding.ingredientsInput.text.toString().trim()
            val instructions = binding.instructionsInput.text.toString().trim()
            val category = Category.valueOf(binding.categorySpinner.selectedItem.toString())

            if (title.isNotEmpty() && ingredients.isNotEmpty() && instructions.isNotEmpty() && selectedImageName != null) {
                val recipe = Recipe(
                    id = recipeToEdit?.id ?: 0,
                    title = title,
                    ingredients = ingredients,
                    instructions = instructions,
                    category = category,
                    imageUri = selectedImageName!!
                )

                if (recipeToEdit != null) {
                    viewModel.updateRecipe(recipe) {
                        finish()
                    }
                } else {
                    viewModel.insertRecipe(recipe) {
                        finish()
                    }
                }
            } else {
                binding.recipeTitleInput.error = if (title.isEmpty()) "Required" else null
                binding.ingredientsInput.error = if (ingredients.isEmpty()) "Required" else null
                binding.instructionsInput.error = if (instructions.isEmpty()) "Required" else null
            }
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }
    }

    /**
     * Populates the category dropdown with all defined recipe categories.
     */
    private fun setupCategorySpinner() {
        val categoryNames = Category.entries.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter
    }

    /**
     * Loads image options into the spinner and sets up image preview behaviour.
     */
    private fun setupImageSpinner() {
        viewModel.imageNames.observe(this) { displayNames ->
            val placeholder = "Select image"
            val options = listOf(placeholder) + displayNames

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                options
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }

            binding.imageSpinner.adapter = adapter

            val selectedDisplay = recipeToEdit?.imageUri
                ?.substringAfterLast('/')
                ?.replace('_', ' ')

            val preselectIndex = if (selectedDisplay != null) {
                options.indexOf(selectedDisplay).takeIf { it > 0 } ?: 0
            } else {
                0
            }

            binding.imageSpinner.setSelection(preselectIndex)

            binding.imageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position > 0) {
                        val selectedDisplayName = displayNames[position - 1]
                        selectedImageName = selectedDisplayName.replace(' ', '_')
                        updateImagePreview()
                    } else {
                        selectedImageName = null
                        binding.recipeImageView.setImageResource(R.drawable.placeholder_image)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedImageName = null
                }
            }
        }
    }

    /**
     * Updates the recipe image preview based on the selected image name.
     */
    private fun updateImagePreview() {
        selectedImageName?.let { imageName ->
            val imageResId = resources.getIdentifier(imageName, "drawable", packageName)
            if (imageResId != 0) {
                binding.recipeImageView.setImageResource(imageResId)
            } else {
                binding.recipeImageView.setImageResource(R.drawable.placeholder_image)
            }
        }
    }
}
