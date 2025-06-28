package co.uk.bbk.culinarycompanion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import co.uk.bbk.culinarycompanion.databinding.ActivityRecipeDetailsBinding

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = IntentCompat.getSerializableExtra(intent, "recipe", Recipe::class.java)!!

        val imageName = recipe.imageUri.substringAfterLast("/")
        val imageResId = resources.getIdentifier(imageName, "drawable", packageName)
        val drawable = if (imageResId != 0)
            ContextCompat.getDrawable(this, imageResId)
        else
            ContextCompat.getDrawable(this, R.drawable.placeholder_image)

        binding.recipeImage.setImageDrawable(drawable)

        binding.recipeCategory.text = "Category: ${recipe.category}"
        binding.recipeTitle.text = recipe.title

        val formattedIngredients = "Ingredients:\n" + recipe.ingredients
            .split(",")
            .joinToString("\n") { "- ${it.trim()}" }

        binding.ingredientList.text = formattedIngredients

        val formattedInstructions = "Cooking instructions:\n" + recipe.instructions
//            .split(",")
//            .joinToString("\n") { "- ${it.trim()}" }

        binding.recipeInstructions.text = formattedInstructions
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
