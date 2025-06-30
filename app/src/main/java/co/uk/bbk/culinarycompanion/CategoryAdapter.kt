package co.uk.bbk.culinarycompanion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.culinarycompanion.databinding.CategorySectionBinding

/**
 * Adapter for displaying recipe categories, each with a horizontal list of recipes.
 * Handles category and recipe click events.
 */
class CategoryAdapter(
    private val onCategoryClick: (Category) -> Unit,
    private val onRecipeClick: (Recipe) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val groupedRecipes: MutableMap<Category, List<Recipe>> = mutableMapOf()

    /**
     * ViewHolder for a single category section containing a title and a horizontal list of recipes.
     */
    class CategoryViewHolder(val binding: CategorySectionBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * Inflates the layout for a category section.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategorySectionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    /**
     * Returns the number of categories to display.
     */
    override fun getItemCount(): Int = groupedRecipes.size

    /**
     * Binds category name and corresponding recipes to the view.
     * Sets up click listeners for category and individual recipes.
     */
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = groupedRecipes.keys.elementAt(position)
        val recipes = groupedRecipes[category].orEmpty()

        holder.binding.categoryTitle.text = category.name.replaceFirstChar { it.uppercase() }

        holder.binding.recipeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = RecipeAdapter(recipes, onRecipeClick)
        }

        // Handle category click
        holder.binding.categoryTitle.setOnClickListener {
            onCategoryClick(category)
        }
    }

    /**
     * Updates the dataset with a new map of categories to recipes and refreshes the view.
     */
    fun updateData(newData: Map<Category, List<Recipe>>) {
        groupedRecipes.clear()
        groupedRecipes.putAll(newData)
        notifyDataSetChanged()
    }
}
