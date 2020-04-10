package com.realitix.nectar.util

import android.content.Context
import com.realitix.nectar.background.GitRepositorySynchronizer
import kotlin.concurrent.thread

class AndroidTestUtil {
    fun runSynchronizer(context: Context) {
        thread(start = true) {
            GitRepositorySynchronizer(context).exec()
        }.join()
    }
}