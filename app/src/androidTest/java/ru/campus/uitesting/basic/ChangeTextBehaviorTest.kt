package ru.campus.uitesting.basic

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.campus.uitesting.R

const val STRING_TO_BE_TYPED = "Espresso"

@RunWith(AndroidJUnit4::class)
@LargeTest
class ChangeTextBehaviorTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<BasicActivity>()

    @Test
    fun changeText_sameActivity() {

        // Type text and then press the button.
        onView(withId(R.id.editTextUserInput))
            .perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard())
        onView(withId(R.id.changeTextBt)).perform(click())

        // Check that the text was changed.
        onView(withId(R.id.textToBeChanged)).check(matches(withText(STRING_TO_BE_TYPED)))
    }







    @Test
    fun changeText_newActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUserInput)).perform(
            typeText(STRING_TO_BE_TYPED),
            closeSoftKeyboard()
        )
        onView(withId(R.id.activityChangeTextBtn)).perform(click())

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.showTextView)).check(matches(withText(STRING_TO_BE_TYPED)))
    }







    private var mIdlingResource: IdlingResource? = null

    @Before
    fun registerIdlingResource() {
        activityScenarioRule.scenario.onActivity { activity ->
            mIdlingResource = activity.idlingResource
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(mIdlingResource)
        }
    }

    @Test
    fun delayedChangeText_sameActivity() { // Type text and then press the button.
        onView(withId(R.id.editTextUserInput))
            .perform(
                typeText(STRING_TO_BE_TYPED),
                closeSoftKeyboard()
            )
        onView(withId(R.id.changeTextDelayedBt)).perform(click())
        // Check that the text was changed.
        onView(withId(R.id.textToBeChanged))
            .check(matches(withText(STRING_TO_BE_TYPED)))
    }

    @After
    fun unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource)
        }
    }

}