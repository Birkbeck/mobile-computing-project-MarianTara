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
        viewModelScope.launch(Dispatchers.Default) {
            val fields = R.drawable::class.java.fields

            val imageNames = fields.mapNotNull { field ->
                try {
                    val name = field.name
                    if (name.endsWith("_preview") ) null
                    else name.replace('_', ' ')
                } catch (e: Exception) {
                    null
                }
            }.sorted()

            _imageNames.postValue(imageNames)
        }
    }

    fun insertRecipe(recipe: Recipe, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeDao.insert(recipe)
            onComplete()
        }
    }

    fun updateRecipe(recipe: Recipe, onDone: () -> Unit) {
        viewModelScope.launch {
            recipeDao.update(recipe)
            onDone()
        }
    }

}
