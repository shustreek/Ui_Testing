package ru.campus.uitesting.basic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.common.base.Strings
import kotlinx.android.synthetic.main.activity_show_text.*
import ru.campus.uitesting.R

class ShowTextActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_text)
        // Get the message from the Intent.
        val message =
            Strings.nullToEmpty(intent.getStringExtra(KEY_EXTRA_MESSAGE))
        // Show message.
        showTextView.text = message
    }

    companion object {
        // The name of the extra data sent through an {@link Intent}.
        const val KEY_EXTRA_MESSAGE =
            "com.example.android.testing.espresso.basicsample.MESSAGE"

        fun newStartIntent(
            context: Context?,
            message: String?
        ): Intent {
            return Intent(context, ShowTextActivity::class.java)
                .putExtra(KEY_EXTRA_MESSAGE, message)
        }
    }
}