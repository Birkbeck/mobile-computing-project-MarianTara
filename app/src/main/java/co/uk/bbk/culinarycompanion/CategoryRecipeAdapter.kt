package co.uk.bbk.culinarycompanion

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.culinarycompanion.databinding.CategoryRecipeItemBinding

/**
 * Adapter for displaying a list of recipes within a selected category.
 * Used in CategoryActivity only.
 */
class CategoryRecipeAdapter(
    private var recipeList: List<Recipe>,
    private val onEditClick: (Recipe) -> Unit,
    private val onDeleteClick: (Recipe) -> Unit,
    private val onRecipeClick: (Recipe) -> Unit
) : RecyclerView.Adapter<CategoryRecipeAdapter.RecipeViewHolder>() {

    /**
     * ViewHolder for displaying a single recipe with image, title, and actions.
     */
    inner class RecipeViewHolder(val binding: CategoryRecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    /**
     * Inflates the layout for a recipe item view.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = CategoryRecipeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    /**
     * Binds recipe data to the view and sets up click actions for edit, delete and Recipe details.
     */
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        val context = holder.itemView.context

        holder.binding.recipeTitle.text = recipe.title

        val formattedIngredients = "List of ingredients:\n" + recipe.ingredients
            .split(",")
            .joinToString("\n") { "- ${it.trim()}" }
        holder.binding.ingredientList.text = formattedIngredients

        // Load image
        val imageName = recipe.imageUri.substringAfterLast("/")
        val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)

        if (imageResId != 0) {
            holder.binding.recipeImage.setImageResource(imageResId)
        } else {
            holder.binding.recipeImage.setImageResource(R.drawable.placeholder_image)
            Log.w("CategoryRecipeAdapter", "Image not found for name: $imageName")
        }

        // Open recipe details
        holder.itemView.setOnClickListener {
            onRecipeClick(recipe)
        }

        // Edit & Delete actions
        holder.binding.editButton.setOnClickListener { onEditClick(recipe) }
        holder.binding.deleteButton.setOnClickListener { onDeleteClick(recipe) }
    }

    /**
     * Returns the number of recipes in the list.
     */
    override fun getItemCount(): Int = recipeList.size

    /**
     * Updates the recipe list and refreshes the RecyclerView.
     */
    fun updateData(newList: List<Recipe>) {
        recipeList = newList
        notifyDataSetChanged()
    }
}
