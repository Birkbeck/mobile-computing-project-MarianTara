package co.uk.bbk.culinarycompanion

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

/**
 * ViewModel for managing recipe data within a specific category.
 * Supports live filtering and deletion.
 */
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

    /**
     * Loads recipes for the selected category and updates observers.
     */
    fun loadRecipesForCategory(category: Category) {
        _category.value = category
    }

    /**
     * Sets the current search query for filtering recipes.
     */
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    /**
     * Filters recipes based on selected category and search query.
     * Called automatically when observed sources change.
     */
    private fun updateFilteredRecipes() {
        val category = _category.value ?: return
        val query = _searchQuery.value ?: ""

        viewModelScope.launch {
            val all = dao.getRecipesByCategory(category)
            _filtered.value = if (query.isBlank()) all
            else all.filter { it.title.contains(query, ignoreCase = true) }
        }
    }

    /**
     * Deletes the given recipe and reloads the category list.
     */
    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch {
            dao.delete(recipe)
            loadRecipesForCategory(recipe.category)
        }
    }
}
