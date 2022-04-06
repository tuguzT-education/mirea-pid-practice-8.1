package io.github.tuguzt.guessthenumber

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import io.github.tuguzt.guessthenumber.databinding.ActivityGuessBinding

class GuessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuessBinding
    private val viewModel: GuessViewModel by viewModels(this::GuessViewModelFactory)

    private inner class GuessViewModelFactory : Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val bundle = this@GuessActivity.intent.extras
            val minimum = requireNotNull(bundle?.getInt("minimum"))
            val maximum = requireNotNull(bundle?.getInt("maximum"))
            return GuessViewModel(minimum..maximum) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.yes.setOnClickListener { handleResult(true) }
        binding.no.setOnClickListener { handleResult(false) }

        binding.text.text = viewModel.createMessage(this)
    }

    private fun handleResult(answer: Boolean): Unit = when (viewModel.acceptAnswer(answer)) {
        GameState.Win -> {
            val message = """
                You guessed the number!
                It was ${viewModel.guessedValue}
            """.trimIndent()
            showToast(this, message)
            finish()
        }
        GameState.Lose -> {
            val message = """
                You have not guessed the number!
                It was ${viewModel.guessedValue}
            """.trimIndent()
            showToast(this, message)
            finish()
        }
        GameState.Continue -> binding.text.text = viewModel.createMessage(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
