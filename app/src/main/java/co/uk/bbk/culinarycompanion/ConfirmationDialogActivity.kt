package co.uk.bbk.culinarycompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import co.uk.bbk.culinarycompanion.databinding.ActivityConfirmationDialogBinding

class ConfirmationDialogActivity : ComponentActivity() {
    private lateinit var binding: ActivityConfirmationDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CW1: just static Yes/No buttons; no logic yet
    }
}
