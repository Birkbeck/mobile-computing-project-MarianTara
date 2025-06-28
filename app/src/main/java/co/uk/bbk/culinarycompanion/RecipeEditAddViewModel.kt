package co.uk.bbk.culinarycompanion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeEditAddViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = RecipeDatabase.getInstance(application).recipeDao()

    private val _imageNames = MutableLiveData<List<String>>()
    val imageNames: LiveData<List<String>> = _imageNames


    fun loadImageOptions() {
        viewModelScope.launch {
            val rawUris = recipeDao.getAllImageOptions()
            val imageNames = rawUris.mapNotNull { uri ->
                uri.substringAfterLast('/')
                    .replace('_', ' ')
            }.distinct().sorted()
            _imageNames.postValue(imageNames)
        }
    }




    fun insertRecipe(recipe: Recipe, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeDao.insert(recipe)
            onComplete()
        }
    }

    fun updateRecipe(recipe: Recipe, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeDao.update(recipe)
            onComplete()
        }
    }
}
