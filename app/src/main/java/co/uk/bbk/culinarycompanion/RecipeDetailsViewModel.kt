package co.uk.bbk.culinarycompanion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = RecipeDatabase.getInstance(application).recipeDao()
    private val _recipeLiveData = MutableLiveData<Recipe>()
    val recipeLiveData: LiveData<Recipe> = _recipeLiveData

    fun setRecipe(recipe: Recipe) {
        _recipeLiveData.value = recipe
    }

    fun refreshRecipe() {
        val current = _recipeLiveData.value ?: return

        viewModelScope.launch {
            val updated = recipeDao.getRecipeById(current.id)
            if (updated != null) {
                _recipeLiveData.postValue(updated!!)
            }
        }
    }
}

