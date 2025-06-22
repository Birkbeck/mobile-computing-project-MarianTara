package co.uk.bbk.culinarycompanion

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.uk.bbk.culinarycompanion.databinding.CategorySectionBinding

class CategoryAdapter(
    private val groupedRecipes: MutableMap<Category, List<Recipe>> = mutableMapOf()
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: CategorySectionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategorySectionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = groupedRecipes.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = groupedRecipes.keys.elementAt(position)
        val recipes  = groupedRecipes[category].orEmpty()

        holder.binding.categoryTitle.text = category.name.replaceFirstChar { it.uppercase() }

        holder.binding.recipeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter      = RecipeAdapter(recipes)
        }
    }

    /** Replace the whole map and refresh */
    fun updateData(newData: Map<Category, List<Recipe>>) {
        groupedRecipes.clear()
        groupedRecipes.putAll(newData)
        notifyDataSetChanged()
    }
}
