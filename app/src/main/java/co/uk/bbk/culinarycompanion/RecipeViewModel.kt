package co.uk.bbk.culinarycompanion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel for retrieving and exposing recipes grouped by category.
 * Used in MainActivity to display the home screen layout.
 */
class RecipeViewModel : ViewModel() {

    private val _groupedRecipes = MutableLiveData<Map<Category, List<Recipe>>>()
    val groupedRecipes: LiveData<Map<Category, List<Recipe>>> = _groupedRecipes

    var recipeDao: RecipeDao? = null

      /**
      * Fetches all recipes from the DAO and groups them by category.
      */
    fun getGroupedRecipes() {
        viewModelScope.launch {
            recipeDao?.let { dao ->
                val allRecipes = dao.getAllRecipes()
                val grouped = allRecipes.groupBy { it.category }
                _groupedRecipes.value = grouped
            }
        }
    }
}
