package co.uk.bbk.culinarycompanion

import android.os.Bundle
import android.view.Menu
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.uk.bbk.culinarycompanion.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView (mock only for Coursework 1)
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }
}
