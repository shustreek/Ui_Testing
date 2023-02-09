package ru.campus.uitesting.basic

import android.app.Activity
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import kotlinx.android.synthetic.main.activity_basic.*
import ru.campus.uitesting.R
import ru.campus.uitesting.idlingresource.SimpleIdlingResource

class BasicActivity : Activity() {

    @VisibleForTesting
    val idlingResource = SimpleIdlingResource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
        // Set the listeners for the buttons.
        changeTextBt.setOnClickListener {
            textToBeChanged.text = editTextUserInput.text
        }
        changeTextDelayedBt.setOnClickListener {
            // Set a temporary text.
            textToBeChanged.setText(R.string.waiting_msg)
            MessageDelayer.processMessage(
                editTextUserInput.text.toString(),
                idlingResource
            ) { textToBeChanged.text = it }
        }
        activityChangeTextBtn.setOnClickListener {
            startActivity(
                ShowTextActivity
                    .newStartIntent(this, editTextUserInput.text.toString())
            )
        }
    }

}