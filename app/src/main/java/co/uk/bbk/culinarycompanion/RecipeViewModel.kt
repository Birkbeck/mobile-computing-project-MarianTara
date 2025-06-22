package co.uk.bbk.culinarycompanion

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes
    private val _groupedRecipes = MutableLiveData<Map<Category, List<Recipe>>>()
    val groupedRecipes: LiveData<Map<Category, List<Recipe>>> = _groupedRecipes

    var recipeDao: RecipeDao? = null

    fun readAllRecipes() {
        viewModelScope.launch {
            recipeDao?.let {
                val allRecipes = it.getAllRecipes()
                _recipes.value = allRecipes
            }
        }
    }

    fun getRecipesByCategory(category: Category) {
        viewModelScope.launch {
            recipeDao?.let {
                val filtered = it.getRecipesByCategory(category)
                _recipes.value = filtered
            }
        }
    }

    fun getGroupedRecipes() {
        viewModelScope.launch {
            recipeDao?.let { dao ->
                val allRecipes = dao.getAllRecipes()
                val grouped = allRecipes.groupBy { it.category }
                _groupedRecipes.value = grouped
            }
        }
    }

    fun insertRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeDao?.insert(recipe)
            readAllRecipes()
        }
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeDao?.update(recipe)
            readAllRecipes()
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipeDao?.delete(recipe)
            readAllRecipes()
        }
    }
}
