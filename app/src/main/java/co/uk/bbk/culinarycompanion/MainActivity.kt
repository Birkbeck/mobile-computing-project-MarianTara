package co.uk.bbk.culinarycompanion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycompanion.databinding.ActivityMainBinding

/**
 * Main activity displaying grouped recipes by category.
 * Provides navigation to recipe creation and category-specific views.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mockViewModel: MockDataViewModel
    private lateinit var recipeViewModel: RecipeViewModel

    /**
     * Initialises view binding, ViewModels, RecyclerView adapter
     * and observers for grouped recipe data.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Populate mock data
        mockViewModel = ViewModelProvider(this)[MockDataViewModel::class.java]
        mockViewModel.populateDatabaseIfEmpty()

        // Initialise ViewModel and DAO
        recipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        recipeViewModel.recipeDao = RecipeDatabase.getInstance(this).recipeDao()
        recipeViewModel.getGroupedRecipes()

        // Set up adapter and category click handler
        val categoryAdapter = CategoryAdapter ({ selectedCategory ->
            // Launch category-specific recipe view
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("category_name", selectedCategory.name)
            startActivity(intent)
        },
            onRecipeClick = { recipe ->
                RecipeDetailsActivity.start(this, recipe)
            }
        )

        // Set up RecyclerView
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.categoryRecyclerView.adapter = categoryAdapter

        // Observe data
        recipeViewModel.groupedRecipes.observe(this) { grouped ->
            categoryAdapter.updateData(grouped)
        }

        // Set up create button
        binding.createRecipeButton.setOnClickListener {
            val intent = Intent(this, RecipeEditAddActivity::class.java)
            startActivity(intent)
        }

    }

    /**
     * Refreshes recipe data when returning to activity.
     */
    override fun onResume() {
        super.onResume()
        recipeViewModel.getGroupedRecipes()
    }

}
