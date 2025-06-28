package co.uk.bbk.culinarycompanion

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycompanion.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var adapter: CategoryRecipeAdapter

    private var selectedRecipe: Recipe? = null

    // confirmation view Launcher
    private val confirmDeleteLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val deletedRecipe =
                    result.data?.getSerializableExtra("confirmed_recipe") as? Recipe
                deletedRecipe?.let {
                    viewModel.deleteRecipe(it)
                    Toast.makeText(this, "Recipe deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get category name from intent
        val categoryName = intent.getStringExtra("category_name") ?: ""
        Log.d("CategoryActivity", "Received category: $categoryName")

        try {
            val categoryEnum = Category.valueOf(categoryName)
            viewModel.loadRecipesForCategory(categoryEnum)
        } catch (e: IllegalArgumentException) {
            Toast.makeText(this, "Invalid category: $categoryName", Toast.LENGTH_SHORT).show()
            Log.e("CategoryActivity", "Invalid category passed: $categoryName")
        }

        // Set up adapter
        adapter = CategoryRecipeAdapter(
            recipeList = listOf(),
            onEditClick = { recipe ->
                // TODO: open edit screen
            },
            onDeleteClick = { recipe ->
                selectedRecipe = recipe
                val intent = Intent(this, ConfirmationDialogActivity::class.java)
                intent.putExtra("recipe", recipe)
                confirmDeleteLauncher.launch(intent)
            },
            onRecipeClick = { recipe ->
                RecipeDetailsActivity.start(this, recipe)
            }
        )

        binding.recipeListRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipeListRecyclerView.adapter = adapter

        // Observe recipe list
        viewModel.filteredRecipes.observe(this) { recipes ->
            adapter.updateData(recipes)
            Log.d("CategoryActivity", "Loaded ${recipes.size} recipes")
        }

        // Search input logic
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSearchQuery(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // create button handler
        binding.createRecipeButton.setOnClickListener {
            val intent = Intent(this, RecipeEditAddActivity::class.java)
            startActivity(intent)
        }


        // Prevent search bar from grabbing focus on launch
        binding.root.clearFocus()
    }
}
