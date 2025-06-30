package co.uk.bbk.culinarycompanion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel for managing and updating the recipe shown in the RecipeDetailsActivity.
 */
class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = RecipeDatabase.getInstance(application).recipeDao()
    private val _recipeLiveData = MutableLiveData<Recipe>()
    val recipeLiveData: LiveData<Recipe> = _recipeLiveData

    /**
     * Sets the currently displayed recipe.
     */
    fun setRecipe(recipe: Recipe) {
        _recipeLiveData.value = recipe
    }

    /**
     * Reloads the recipe from the database to ensure data is up to date.
     */
    fun refreshRecipe() {
        val current = _recipeLiveData.value ?: return

        viewModelScope.launch {
            val updated = recipeDao.getRecipeById(current.id)
            if (updated != null) {
                _recipeLiveData.postValue(updated)
            }
        }
    }
}
