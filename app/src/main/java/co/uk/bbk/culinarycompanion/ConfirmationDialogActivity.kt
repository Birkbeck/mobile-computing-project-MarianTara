package co.uk.bbk.culinarycompanion

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfirmationDialogActivity : AppCompatActivity() {

    private lateinit var recipeToDelete: Recipe

    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation_dialog)

        val yesButton = findViewById<Button>(R.id.yesButton)
        val noButton = findViewById<Button>(R.id.noButton)
        val message = findViewById<TextView>(R.id.confirmationMessage)

        // Get the recipe to delete
        recipeToDelete = intent.getSerializableExtra("recipe") as? Recipe ?: run {
            finish()
            return
        }

        message.text = getString(
            R.string.confirm_delete_message,
            recipeToDelete.title
        )

        yesButton.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    RecipeDatabase.getInstance(applicationContext)
                        .recipeDao()
                        .delete(recipeToDelete)
                }
                setResult(RESULT_OK)
                finish()
            }
        }

        noButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
