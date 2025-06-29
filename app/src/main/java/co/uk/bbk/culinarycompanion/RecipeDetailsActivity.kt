package co.uk.bbk.culinarycompanion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import co.uk.bbk.culinarycompanion.databinding.ActivityRecipeDetailsBinding

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding
    private val viewModel: RecipeDetailsViewModel by viewModels()

    private val confirmDeleteLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            finish()
        }
    }

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
            binding.ingredientList.text = displayRecipe.ingredients
            binding.recipeInstructions.text = displayRecipe.instructions
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

    override fun onResume() {
        super.onResume()
        viewModel.refreshRecipe()
    }

    companion object {
        fun start(context: Context, recipe: Recipe) {
            val intent = Intent(context, RecipeDetailsActivity::class.java).apply {
                putExtra("recipe", recipe)
            }
            context.startActivity(intent)
        }
    }
}
