package com.realitix.nectar

import android.util.Log
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.realitix.nectar.database.entity.GitRepositoryRaw
import com.realitix.nectar.repository.GitRepoRepository
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.lang.Thread.sleep


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainInstrumentedTest {
    @get:Rule
    var activityRule = NectarActivityTestRule()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun test1() {
        onView(withId(R.id.mealsFragment))
            .perform(click())
    }
}
