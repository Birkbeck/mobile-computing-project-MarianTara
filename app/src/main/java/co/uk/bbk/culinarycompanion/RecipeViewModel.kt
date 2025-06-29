package co.uk.bbk.culinarycompanion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
//
    private val _groupedRecipes = MutableLiveData<Map<Category, List<Recipe>>>()
    val groupedRecipes: LiveData<Map<Category, List<Recipe>>> = _groupedRecipes

    var recipeDao: RecipeDao? = null


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
