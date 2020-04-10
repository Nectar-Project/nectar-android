package com.realitix.nectar.util

import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.realitix.nectar.BuildConfig
import com.realitix.nectar.MainActivity
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.GitCredentials
import com.realitix.nectar.database.entity.GitRepositoryRaw
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import java.io.File
import java.lang.Exception

class NectarActivityTestRule: ActivityTestRule<MainActivity>(
    MainActivity::class.java) {
    override fun beforeActivityLaunched() {
        super.beforeActivityLaunched()
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // disable automatic synchronization
        MainActivity.enableSynchronizer = false

        // clean database
        context.deleteDatabase(NectarUtil.getProperty(context, "databaseName"))

        // clean repositories folder
        File(context.filesDir, NectarUtil.getProperty(context, "repositoryNameFolder")).deleteRecursively()

        // add test repository
        NectarDatabase.setInitCallback(getRoomCallback())
    }

    private fun getRoomCallback(): RoomDatabase.Callback {
        val testContext = InstrumentationRegistry.getInstrumentation().context
        val username = BuildConfig.testGitRepositoryUsername
        val password = BuildConfig.testGitRepositoryPassword
        val nullValue = "null"

        if(username == nullValue || password == nullValue) {
            throw Exception("Instrumented tests needs parameters testGitRepositoryUsername and testGitRepositoryPassword in local.properties")
        }

        return object: RoomDatabase.Callback() {
            fun init(db: SupportSQLiteDatabase) {
                // Create DB Entry for default repository
                val repo = GitRepositoryRaw(
                    generateUuid(),
                    NectarUtil.getProperty(testContext, "defaultGitRepositoryName"),
                    NectarUtil.getProperty(testContext, "defaultGitRepositoryUrl"),
                    enabled = true,
                    rescan = true,
                    readOnly = false,
                    lastCheck = 0,
                    frequency = 60,
                    credentials = GitCredentials(username, password)
                )
                db.insert("GitRepositoryRaw", OnConflictStrategy.IGNORE,
                    NectarDatabase.gitRepositoryRawToContentValues(repo)
                )
            }

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                init(db)
            }

            override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                super.onDestructiveMigration(db)
                init(db)
            }
        }
    }
}