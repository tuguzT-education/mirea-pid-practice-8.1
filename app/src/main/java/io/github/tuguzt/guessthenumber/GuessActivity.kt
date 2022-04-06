package io.github.tuguzt.guessthenumber

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.tuguzt.guessthenumber.databinding.ActivityGuessBinding

class GuessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
