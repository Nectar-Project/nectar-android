package com.realitix.nectar

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.realitix.nectar.database.entity.GitCredentials
import com.realitix.nectar.database.entity.GitRepositoryRaw
import com.realitix.nectar.repository.GitRepoRepository
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import java.io.File
import java.lang.Exception

class NectarActivityTestRule: ActivityTestRule<MainActivity>(MainActivity::class.java) {
    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // clean database
        context.deleteDatabase(NectarUtil.getProperty(context, "databaseName"))

        // clean repositories folder
        File(context.filesDir, NectarUtil.getProperty(context, "repositoryNameFolder")).deleteRecursively()
    }

    override fun afterActivityLaunched() {
        super.afterActivityLaunched()
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // Add test repository
        val username = BuildConfig.testGitRepositoryUsername
        val password = BuildConfig.testGitRepositoryPassword
        val nullValue = "null"

        if(username == nullValue || password == nullValue) {
            throw Exception("Instrumented tests needs parameters testGitRepositoryUsername and testGitRepositoryPassword in local.properties")
        }

        val repository = GitRepoRepository(context)
        repository.insert(
            GitRepositoryRaw(
                generateUuid(),
                name = "test-repository",
                url = "https://github.com/Nectar-Project/nectar-data-test.git",
                enabled = true,
                rescan = true,
                readOnly = false,
                lastCheck = 0,
                frequency = 60,
                credentials = GitCredentials(username, password)
            )
        )
}
}