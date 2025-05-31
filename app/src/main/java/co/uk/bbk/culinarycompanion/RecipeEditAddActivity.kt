package co.uk.bbk.culinarycompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import co.uk.bbk.culinarycompanion.databinding.ActivityRecipeEditAddBinding

class RecipeEditAddActivity : ComponentActivity() {
    private lateinit var binding: ActivityRecipeEditAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeEditAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CW1: static form only; no save/cancel logic yet
    }
}
