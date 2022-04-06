package io.github.tuguzt.guessthenumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import io.github.tuguzt.guessthenumber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val showSnackbar = { message: CharSequence -> showSnackbar(binding.root, message) }
        binding.readyToGuess.setOnClickListener {
            val minimum = binding.minimumValue.text.toString().toIntOrNull()
            val maximum = binding.maximumValue.text.toString().toIntOrNull()

            when {
                minimum == null && maximum == null -> showSnackbar(getString(R.string.values_empty))
                minimum == null -> showSnackbar(getString(R.string.min_empty))
                maximum == null -> showSnackbar(getString(R.string.max_empty))
                minimum < maximum -> {
                    val intent = Intent(this, GuessActivity::class.java).apply {
                        val bundle = bundleOf("minimum" to minimum, "maximum" to maximum)
                        putExtras(bundle)
                    }
                    startActivity(intent)
                }
                else -> showSnackbar(getString(R.string.max_too_small))
            }
        }
    }
}
