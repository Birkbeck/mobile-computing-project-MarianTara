package co.uk.bbk.culinarycompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import co.uk.bbk.culinarycompanion.databinding.ActivityRecipeDetailsBinding

class RecipeDetailsActivity : ComponentActivity() {
    private lateinit var binding: ActivityRecipeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CW1: just display static placeholders
    }
}
