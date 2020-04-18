package com.realitix.nectar

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.realitix.nectar.background.GitRepositorySynchronizer
import com.realitix.nectar.background.synchronizer.AlimentSynchronizer
import com.realitix.nectar.background.synchronizer.StringKeySynchronizer
import com.realitix.nectar.repository.GitRepoRepository
import com.realitix.nectar.util.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.lang.Thread.sleep
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
        // clone repo and fill it with data
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

        // run synchronization
        GitRepositorySynchronizer(appContext).exec()

        // check data synchonized
        onView(withText("Receipe France")).perform(click())
        onView(withText("ReceipeStep Description France")).perform(click())
        onView(withText("Aliment France")).check(matches(isDisplayed()))
    }

    @Test
    fun checkAppSendDataToGitRepository() {
        // clone repo and clean it
        val gitRepository = GitRepoRepository(appContext).getByName(NectarUtil.getProperty(testContext, "defaultGitRepositoryName"))!!
        val repoDir = File(File(
            appContext.filesDir,
            NectarUtil.getProperty(testContext, "repositoryNameFolder")),
            gitRepository.name)
        val gitManager = GitManager(repoDir, gitRepository.url, gitRepository.credentials)
        gitManager.clean()

        // add an aliment to the repository
        val nameFr = "TEST NAME"
        val name = StringKeySynchronizer.ParseResult(NectarUtil.generateUuid(), mapOf("fr" to nameFr))
        val aliment =  AlimentSynchronizer.ParseResult(NectarUtil.generateUuid(), name.uuid, listOf(), listOf(), mapOf())

        NectarUtil.writeToJsonFile(File(File(repoDir, EntityType.STRING_KEY.folderName), name.uuid), name)
        NectarUtil.writeToJsonFile(File(File(repoDir, EntityType.ALIMENT.folderName), aliment.uuid), aliment)

        // Commit and push
        gitManager.addAll()
        gitManager.commit()
        gitManager.push()

        // run synchronization
        GitRepositorySynchronizer(appContext).exec()

        // create a receipe with the aliment
        onView(withId(R.id.fab)).perform(click())
    }
}
