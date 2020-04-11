package com.realitix.nectar

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.realitix.nectar.background.GitRepositorySynchronizer
import com.realitix.nectar.repository.GitRepoRepository
import com.realitix.nectar.util.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.nio.file.Files


@RunWith(AndroidJUnit4::class)
@LargeTest
class GitRepositoryTest {
    @get:Rule
    var activityRule = NectarActivityTestRule()
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val testContext = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun checkGitRepositoryIsInSetting() {
        onView(withId(R.id.settingsFragment)).perform(click())
        onView(withId(R.id.fragmentSettingsLayout)).check(matches(isDisplayed()))
        onView(withText(NectarUtil.getProperty(testContext, "defaultGitRepositoryName"))).check(matches(isDisplayed()))
    }

    @Test
    fun checkAppReceiveDataFromGitRepository() {
        // 1. clone repo and fill it with data
        val gitRepository = GitRepoRepository(appContext).getByName(NectarUtil.getProperty(testContext, "defaultGitRepositoryName"))!!
        val repoDir = File(File(
            appContext.filesDir,
            NectarUtil.getProperty(testContext, "repositoryNameFolder")),
            gitRepository.name)
        val gitManager = GitManager(repoDir, gitRepository.url, gitRepository.credentials)
        gitManager.clean()
        ZipUtil.unzipFromAssets(testContext, "test_repository.zip", repoDir.absolutePath)
        gitManager.addAll()
        gitManager.commit()
        gitManager.push()

        // 2. run synchronization
        GitRepositorySynchronizer(appContext).exec()

        // 3. check data synchonized
        onView(withId(R.id.settingsFragment)).perform(click())
        onView(withId(R.id.fragmentSettingsLayout)).check(matches(isDisplayed()))
        onView(withText(NectarUtil.getProperty(testContext, "defaultGitRepositoryName"))).check(matches(isDisplayed()))
    }
}
