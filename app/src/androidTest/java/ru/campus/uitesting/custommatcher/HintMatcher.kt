package ru.campus.uitesting.custommatcher

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.viewpager.widget.ViewPager
import com.google.common.base.Preconditions
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers

object HintMatcher {
    fun withHint(substring: String): Matcher<View?> {
        return withHint(Matchers.`is`(substring))
    }

    fun withHint(stringMatcher: Matcher<String>): Matcher<View?> {
        Preconditions.checkNotNull(stringMatcher)

        return object : BoundedMatcher<View, EditText>(EditText::class.java) {
            public override fun matchesSafely(view: EditText): Boolean {
                val hint = view.hint
                return hint != null && stringMatcher.matches(hint.toString())
            }

            override fun describeTo(description: Description) {
                description.appendText("with hint: ")
                stringMatcher.describeTo(description)
            }
        }
    }
}

class CustomWithTextMatcher(
    private val text: String
) : BoundedMatcher<View, TextView>(TextView::class.java) {
    override fun describeTo(description: Description?) {
        description?.appendText("No matching views found")
    }

    override fun matchesSafely(view: TextView): Boolean {
        return view.text == text
    }
}

class ViewPagerItemCountAssertion(
    private val matcher: Matcher<Int>
) : ViewAssertion {
    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        noViewFoundException?.let { throw it }

        with(view as ViewPager) {
            assertThat(adapter!!.count, matcher)
        }
    }
}