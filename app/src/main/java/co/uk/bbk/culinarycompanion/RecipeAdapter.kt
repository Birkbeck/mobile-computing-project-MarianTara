package co.uk.bbk.culinarycompanion

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
        // You could load an image into the ImageView here, e.g. with Glide or Picasso
    }

    override fun getItemCount(): Int = recipeList.size

    fun updateList(newList: List<Recipe>) {
        recipeList = newList
        notifyDataSetChanged()
    }
}
