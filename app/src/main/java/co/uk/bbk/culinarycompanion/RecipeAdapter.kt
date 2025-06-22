package co.uk.bbk.culinarycompanion

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.culinarycompanion.databinding.RecipeItemBinding

class RecipeAdapter(
    private var recipeList: List<Recipe>
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.binding.recipeTitle.text = recipe.title

        val context = holder.itemView.context

        // Log the raw URI
        Log.d("RecipeAdapter", "Trying to load image for: ${recipe.imageUri}")

        // Extract the actual image name from the URI
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
    }




    override fun getItemCount(): Int = recipeList.size

    fun updateList(newList: List<Recipe>) {
        recipeList = newList
        notifyDataSetChanged()
    }
}
