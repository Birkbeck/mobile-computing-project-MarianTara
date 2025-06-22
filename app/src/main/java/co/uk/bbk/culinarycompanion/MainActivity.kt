package co.uk.bbk.culinarycompanion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycompanion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mockViewModel: MockDataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // Force light mode
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up ViewModel and insert mock data if DB is empty
        mockViewModel = ViewModelProvider(this)[MockDataViewModel::class.java]
        mockViewModel.populateDatabaseIfEmpty()

        // Setup RecyclerView (static/mock for CW1)
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this)

        // Setup button (for CW2 logic later)
        binding.createRecipeButton.setOnClickListener {
            // TODO: open create recipe screen
        }
    }
}
