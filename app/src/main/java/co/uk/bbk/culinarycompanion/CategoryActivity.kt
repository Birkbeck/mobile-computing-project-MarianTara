package co.uk.bbk.culinarycompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycompanion.databinding.ActivityCategoryBinding

class CategoryActivity : ComponentActivity() {
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // For CW1: no adapter needed, just RecyclerView layout
        binding.recipeListRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
