package ru.campus.uitesting.custommatcher

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.campus.uitesting.R
import ru.campus.uitesting.custommatcher.CustomMatcherActivity
import ru.campus.uitesting.custommatcher.HintMatcher.withHint

@RunWith(AndroidJUnit4::class)
@LargeTest
class HintMatchersTest {
    // A valid string with a valid ending
    private var mStringWithValidEnding: String? = null
    // A valid string from the coffee preparations
    private var mValidStringToBeTyped: String? = null
    /**
     * [ActivityScenarioRule] will create and launch of the activity for you.
     */
    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(CustomMatcherActivity::class.java)

    @Before
    fun initValidStrings() { // Produce a string with valid ending.
        mStringWithValidEnding = "Random " + CustomMatcherActivity.VALID_ENDING
        // Get one of the available coffee preparations.
        mValidStringToBeTyped = CustomMatcherActivity.COFFEE_PREPARATIONS[0]
    }

    /**
     * Uses a custom matcher [HintMatcher.withHint], with a [String] as the argument.
     */
    @Test
    fun hint_isDisplayedInEditText() {
        val hintText = "How do you like your coffee?"
        Espresso.onView(withId(R.id.editText))
            .check(ViewAssertions.matches(withHint(hintText)))
    }

    /**
     * Same as above but using a [org.hamcrest.Matcher] as the argument.
     */
    @Test
    fun hint_endsWith() { // This check will probably fail if the app is localized and the language is changed. Avoid
// string literals in code!
        Espresso.onView(withId(R.id.editText)).check(
            ViewAssertions.matches(
                withHint(
                    Matchers.anyOf(
                        Matchers.endsWith(COFFEE_ENDING),
                        Matchers.endsWith(COFFEE_INVALID_ENDING)
                    )
                )
            )
        )
    }

    @Test
    fun editText_canBeTypedInto() {
        Espresso.onView(withId(R.id.editText))
            .perform(ViewActions.typeText(mValidStringToBeTyped), ViewActions.closeSoftKeyboard())
            .check(ViewAssertions.matches(ViewMatchers.withText(mValidStringToBeTyped)))
    }

    @Test
    fun validation_resultIsOneOfTheValidStrings() { // Type a valid string and click on the button.
        Espresso.onView(withId(R.id.editText))
            .perform(ViewActions.typeText(mValidStringToBeTyped), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.button)).perform(ViewActions.click())
        // Check that the correct sign is displayed.
        Espresso.onView(withId(R.id.inputValidationSuccess))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.inputValidationError))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun validation_resultHasCorrectEnding() { // Type a string with a valid ending and click on the button.
        Espresso.onView(withId(R.id.editText))
            .perform(ViewActions.typeText(mStringWithValidEnding), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.button)).perform(ViewActions.click())
        // Check that the correct sign is displayed.
        Espresso.onView(withId(R.id.inputValidationSuccess))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.inputValidationError))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    @Test
    fun validation_resultIsIncorrect() { // Type a valid string and click on the button.
        Espresso.onView(withId(R.id.editText))
            .perform(
                ViewActions.typeText(INVALID_STRING_TO_BE_TYPED),
                ViewActions.closeSoftKeyboard()
            )
        Espresso.onView(withId(R.id.button)).perform(ViewActions.click())
        // Check that the correct sign is displayed.
        Espresso.onView(withId(R.id.inputValidationError))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.inputValidationSuccess))
            .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
    }

    companion object {
        private const val INVALID_STRING_TO_BE_TYPED = "Earl Grey"
        private const val COFFEE_ENDING = "coffee?"
        private const val COFFEE_INVALID_ENDING = "tea?"
    }
}