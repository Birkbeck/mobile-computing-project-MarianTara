package co.uk.bbk.culinarycompanion

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.culinarycompanion.databinding.RecipeItemBinding

/**
 * Adapter for displaying a horizontal list of recipe items.
 * Supports click interaction for recipe selection.
 */
class RecipeAdapter(
    private var recipeList: List<Recipe>,
    private val onClick: ((Recipe) -> Unit)? = null
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    /**
     * ViewHolder for displaying a recipe card with title and image.
     */
    inner class RecipeViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root)

    /**
     * Inflates the layout for a recipe item view.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    /**
     * Binds recipe data to the view and sets up click listener.
     */
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.binding.recipeTitle.text = recipe.title

        val context = holder.itemView.context
        val imageName = recipe.imageUri.substringAfterLast("/")

        val imageResId = context.resources.getIdentifier(
            imageName,
            "drawable",
            context.packageName
        )

        if (imageResId != 0) {
            holder.binding.recipeImage.setImageResource(imageResId)
        } else {
            holder.binding.recipeImage.setImageResource(R.drawable.placeholder_image)
            Log.w("RecipeAdapter", "Image not found for name: $imageName")
        }

        holder.binding.root.setOnClickListener {
            onClick?.invoke(recipe)
        }
    }

    /**
     * Returns the number of recipes in the list.
     */
    override fun getItemCount(): Int = recipeList.size
}
