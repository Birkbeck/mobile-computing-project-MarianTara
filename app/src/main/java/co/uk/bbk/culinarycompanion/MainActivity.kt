package co.uk.bbk.culinarycompanion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycompanion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView (static/mock for CW1)
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this)

        // Setup button (for CW2 logic later)
        binding.createRecipeButton.setOnClickListener {
            // TODO: open create recipe screen
        }
    }
}
