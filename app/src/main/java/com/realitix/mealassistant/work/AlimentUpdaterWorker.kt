package com.realitix.mealassistant.work

import android.R.attr.path
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.beust.klaxon.JsonReader
import com.realitix.mealassistant.command.AlimentUpdater
import org.tukaani.xz.XZInputStream
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStreamReader


class AlimentUpdaterWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val au = AlimentUpdater()
        au.update(applicationContext)
        return Result.success()
    }

    private fun getJsonData() {
        /*
         * Current convention:
         * see assets/json_convention.json
         *
         */
        val fileStream = FileInputStream(path.toString() + "myFile.xz")
        val inData = BufferedInputStream(fileStream)
        val xzIn = XZInputStream(inData)
        val xzInReader = InputStreamReader(xzIn, "UTF-8")
        JsonReader(xzInReader).use { reader ->
            reader.beginObject {

            }
        }
        xzIn.close()
    }
}