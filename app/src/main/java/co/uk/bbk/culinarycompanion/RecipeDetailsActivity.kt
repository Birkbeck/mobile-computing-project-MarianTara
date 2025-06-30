package co.uk.bbk.culinarycompanion

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycompanion.databinding.ActivityRecipeDetailsBinding

/**
 * Displays the full details of a selected recipe in a read-only view.
 * Allows navigation to edit or delete the recipe.
 */
class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding
    private val viewModel: RecipeDetailsViewModel by viewModels()

    /**
     * Handles result from the confirmation dialog for deletion.
     * Finishes the activity if deletion is confirmed.
     */
    private val confirmDeleteLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            finish()
        }
    }

    /**
     * Initialises the layout, observes recipe data and sets up UI interactions.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = intent.getSerializableExtra("recipe") as? Recipe
        if (recipe == null) {
            finish()
            return
        }

        viewModel.setRecipe(recipe)

        viewModel.recipeLiveData.observe(this) { displayRecipe ->
            binding.recipeTitle.text = displayRecipe.title

            val ingredientsText = SpannableStringBuilder().apply {
                val boldTitle = "List of ingredients:\n"
                append(boldTitle)
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    0,
                    boldTitle.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                append(displayRecipe.ingredients)
            }

            val instructionsText = SpannableStringBuilder().apply {
                val title = "Cooking instructions:\n"
                append(title)
                setSpan(
                    StyleSpan(Typeface.BOLD),
                    0,
                    title.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                append(displayRecipe.instructions)
            }

            binding.ingredientList.text = ingredientsText
            binding.recipeInstructions.text = instructionsText
            binding.recipeCategory.text = displayRecipe.category.name

            val imageName = displayRecipe.imageUri.substringAfterLast("/")
            val imageResId = resources.getIdentifier(
                imageName,
                "drawable",
                packageName
            )
            if (imageResId != 0) {
                binding.recipeImage.setImageResource(imageResId)
            } else {
                binding.recipeImage.setImageResource(R.drawable.placeholder_image)
            }
        }

        binding.deleteButton.setOnClickListener {
            val intent = Intent(this, ConfirmationDialogActivity::class.java).apply {
                putExtra("recipe", viewModel.recipeLiveData.value)
            }
            confirmDeleteLauncher.launch(intent)
        }

        binding.editButton.setOnClickListener {
            val intent = Intent(this, RecipeEditAddActivity::class.java).apply {
                putExtra("recipe", viewModel.recipeLiveData.value)
            }
            startActivity(intent)
        }
    }

    /**
     * Refreshes the displayed recipe when returning to the activity.
     */
    override fun onResume() {
        super.onResume()
        viewModel.refreshRecipe()
    }

    /**
     * Starts this activity with the provided recipe.
     */
    companion object {
        fun start(context: Context, recipe: Recipe) {
            val intent = Intent(context, RecipeDetailsActivity::class.java).apply {
                putExtra("recipe", recipe)
            }
            context.startActivity(intent)
        }
    }
}
