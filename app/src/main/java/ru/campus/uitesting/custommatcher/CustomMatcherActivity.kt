package ru.campus.uitesting.custommatcher

import android.app.Activity
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_custom_matcher.*
import ru.campus.uitesting.R

class CustomMatcherActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_matcher)
        // Sets the listener for the button.
        button.setOnClickListener {
            val inputText = editText.text.toString()
            // Validate the input and show the result.
            showResult(validateText(inputText))
        }
    }

    private fun showResult(isValidResult: Boolean) {
        inputValidationSuccess.isVisible = isValidResult
        inputValidationError.isVisible = !isValidResult
    }

    private fun validateText(inputText: String): Boolean =
        if (inputText.endsWith(VALID_ENDING, true)) true
        else COFFEE_PREPARATIONS.any { it.equals(inputText, ignoreCase = true) }

    companion object {
        @VisibleForTesting
        val COFFEE_PREPARATIONS = arrayOf(
            "Espresso",
            "Latte",
            "Mocha",
            "Café con leche",
            "Cold brew"
        )
        @VisibleForTesting
        const val VALID_ENDING = "coffee"
    }
}