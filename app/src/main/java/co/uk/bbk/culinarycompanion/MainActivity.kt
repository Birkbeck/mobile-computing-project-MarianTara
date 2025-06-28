package co.uk.bbk.culinarycompanion

import android.content.Intent
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
//
//        // Disable dark mode
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

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
        val categoryAdapter = CategoryAdapter { selectedCategory ->
            // Launch category-specific recipe view
            val intent = Intent(this, CategoryActivity::class.java)
            intent.putExtra("category_name", selectedCategory.name)
            startActivity(intent)
        }

        // Set up RecyclerView
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.categoryRecyclerView.adapter = categoryAdapter

        // Observe data
        recipeViewModel.groupedRecipes.observe(this) { grouped ->
            categoryAdapter.updateData(grouped)
        }

        binding.createRecipeButton.setOnClickListener {
            val intent = Intent(this, RecipeEditAddActivity::class.java)
            startActivity(intent)
        }

    }
}
