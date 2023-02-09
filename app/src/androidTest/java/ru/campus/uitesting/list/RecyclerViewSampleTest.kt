/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.campus.uitesting.list

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.campus.uitesting.R

/**
 * Test class showcasing some [RecyclerViewActions] from Espresso.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class RecyclerViewSampleTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(ListActivity::class.java)

    @Test
    fun scrollToItemBelowFold_checkItsText() { // First scroll to the position that needs to be matched and click on it.
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    ITEM_BELOW_THE_FOLD,
                    ViewActions.click()
                )
            )
        // Match the text in an item below the fold and check that it's displayed.
        val itemElementText = "This is element #$ITEM_BELOW_THE_FOLD"
        Espresso.onView(ViewMatchers.withText(itemElementText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun itemInMiddleOfList_hasSpecialText() { // First, scroll to the view holder using the isInTheMiddle matcher.
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.scrollToHolder(
                    isInTheMiddle
                )
            )
        // Check that the item has the special text.
        val middleElementText = "This is the middle!"
        Espresso.onView(ViewMatchers.withText(middleElementText))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    companion object {
        private const val ITEM_BELOW_THE_FOLD = 40
        /**
         * Matches the [CustomAdapter.ViewHolder]s in the middle of the list.
         */
        private val isInTheMiddle: Matcher<CustomAdapter.ViewHolder>
            get() = object :
                TypeSafeMatcher<CustomAdapter.ViewHolder>() {
                override fun matchesSafely(customHolder: CustomAdapter.ViewHolder): Boolean {
                    return customHolder.isInTheMiddle
                }

                override fun describeTo(description: Description) {
                    description.appendText("item in the middle")
                }
            }
    }
}