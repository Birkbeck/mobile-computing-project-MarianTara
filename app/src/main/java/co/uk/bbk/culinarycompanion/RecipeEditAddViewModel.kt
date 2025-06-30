package co.uk.bbk.culinarycompanion

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for handling logic in RecipeEditAddActivity.
 * Manages image options and database insert/update operations.
 */
class RecipeEditAddViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao = RecipeDatabase.getInstance(application).recipeDao()
    private val _imageNames = MutableLiveData<List<String>>()
    val imageNames: LiveData<List<String>> = _imageNames

    /**
     * Loads available drawable image names for selection in the UI.
     * Filters out preview variants and formats names for display.
     */
    fun loadImageOptions() {
        viewModelScope.launch(Dispatchers.Default) {
            val fields = R.drawable::class.java.fields

            val imageNames = fields.mapNotNull { field ->
                try {
                    val name = field.name
                    if (name.endsWith("_preview")) null
                    else name.replace('_', ' ')
                } catch (e: Exception) {
                    null
                }
            }.sorted()

            _imageNames.postValue(imageNames)
        }
    }

    /**
     * Inserts a new recipe into the database and triggers a callback on completion.
     */
    fun insertRecipe(recipe: Recipe, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            recipeDao.insert(recipe)
            onComplete()
        }
    }

    /**
     * Updates an existing recipe and triggers a callback when done.
     */
    fun updateRecipe(recipe: Recipe, onDone: () -> Unit) {
        viewModelScope.launch {
            recipeDao.update(recipe)
            onDone()
        }
    }
}
