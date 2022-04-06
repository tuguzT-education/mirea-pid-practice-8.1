package io.github.tuguzt.guessthenumber

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class GuessViewModel(private var range: IntRange) : ViewModel() {
    init {
        require(!range.isEmpty())
    }

    private var gameState = GameState.Continue
    private val guessedNumber = range.random()
    private var guessedAnswer = Random.nextBoolean()

    private val middle get() = (range.first + range.last) / 2
    private val firstHalf get() = range.first..middle
    private val secondHalf get() = middle..range.last

    private val rightHalf get() = when (guessedNumber) {
        in firstHalf -> firstHalf
        else -> secondHalf
    }
    private val wrongHalf get() = when {
        guessedNumber in secondHalf && guessedNumber in firstHalf -> (middle + 1)..range.last
        guessedNumber in firstHalf -> secondHalf
        else -> firstHalf
    }
    private val isAlmostGuessed get() = range.last - range.first == 1

    val guessedValue: Int
        get() {
            check(gameState != GameState.Continue)
            return guessedNumber
        }

    fun createMessage(context: Context): String {
        if (isAlmostGuessed) {
            val number = if (guessedAnswer) guessedNumber else wrongHalf.first
            return context.getString(R.string.guess_text_number, number)
        }
        val halfToShow = when (guessedAnswer) {
            true -> rightHalf
            false -> wrongHalf
        }
        if (halfToShow.first == halfToShow.last)
            return context.getString(R.string.guess_text_number, halfToShow.first)

        return context.getString(R.string.guess_text_range, halfToShow.first, halfToShow.last)
    }

    fun acceptAnswer(answer: Boolean): GameState {
        if (answer == guessedAnswer) {
            if (isAlmostGuessed) {
                gameState = GameState.Win
                return gameState
            }
            range = rightHalf
            guessedAnswer = Random.nextBoolean()

            gameState = GameState.Continue
            return gameState
        }
        gameState = GameState.Lose
        return gameState
    }
}
