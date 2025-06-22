package co.uk.bbk.culinarycompanion

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = RecipeDatabase.getInstance(application).recipeDao()
    private val _category = MutableLiveData<Category>()
    private val _searchQuery = MutableLiveData<String>("")

    private val _filtered = MediatorLiveData<List<Recipe>>()
    val filteredRecipes: LiveData<List<Recipe>> = _filtered

    init {
        _filtered.addSource(_category) { updateFilteredRecipes() }
        _filtered.addSource(_searchQuery) { updateFilteredRecipes() }
    }

    fun loadRecipesForCategory(category: Category) {
        _category.value = category
    }



    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun updateFilteredRecipes() {
        val category = _category.value ?: return
        val query = _searchQuery.value ?: ""

        viewModelScope.launch {
            val all = dao.getRecipesByCategory(category)
            _filtered.value = if (query.isBlank()) all
            else all.filter { it.title.contains(query, ignoreCase = true) }
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            dao.delete(recipe)
            updateFilteredRecipes()
        }
    }
}

