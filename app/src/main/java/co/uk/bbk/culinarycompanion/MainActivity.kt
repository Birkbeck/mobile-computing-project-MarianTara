package co.uk.bbk.culinarycompanion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycompanion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mockViewModel: MockDataViewModel
    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up ViewModel and insert mock data if DB is empty
        mockViewModel = ViewModelProvider(this)[MockDataViewModel::class.java]
        mockViewModel.populateDatabaseIfEmpty()

        // Recipe-level ViewModel
        recipeViewModel = ViewModelProvider(this)[RecipeViewModel::class.java]
        recipeViewModel.recipeDao = RecipeDatabase.getInstance(this).recipeDao()
        recipeViewModel.getGroupedRecipes()

        // Category RecyclerView
        categoryAdapter = CategoryAdapter()
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.categoryRecyclerView.adapter = categoryAdapter

        // Update UI when data arrives
        recipeViewModel.groupedRecipes.observe(this) { grouped ->
            categoryAdapter.updateData(grouped)
        }

        // Create-recipe button (hooked up later)
        binding.createRecipeButton.setOnClickListener {
            // TODO: open create recipe screen
        }
    }
}
