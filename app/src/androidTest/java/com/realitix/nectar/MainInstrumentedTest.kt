package com.realitix.nectar

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import androidx.test.rule.ActivityTestRule


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainInstrumentedTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun changeText_sameActivity() {
        onView(withId(R.id.mealsFragment))
            .perform(click())
    }
}
