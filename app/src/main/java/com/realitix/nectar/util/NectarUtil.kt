package com.realitix.nectar.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.realitix.nectar.R
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.AlimentState
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor


class NectarUtil {
    companion object {
        private const val nbSecondsInMinute: Long = 60
        private const val nbSecondsInHour: Long = nbSecondsInMinute*60
        private const val nbSecondsInDay: Long = nbSecondsInHour*24
        private val pattern1 = initSimpleDate("EEEE d MMMM")
        private val pattern2 = initSimpleDate("HH:mm")
        private val pattern3 = initSimpleDate("EEEE d MMMM YYYY")

        fun timestamp() = System.currentTimeMillis()/1000
        fun timestampTimezoned() = timestamp() + TimeZone.getDefault().getOffset(timestamp())/1000
        fun beginDayTimestamp(timestamp: Long): Long = floor((timestamp / nbSecondsInDay).toDouble()).toLong() * nbSecondsInDay
        fun endDayTimestamp(timestamp: Long): Long = beginDayTimestamp(timestamp) + nbSecondsInDay
        fun hourTimestamp(hour: Int): Long = hour * nbSecondsInHour
        fun minuteTimestamp(minute: Int): Long = minute * nbSecondsInMinute
        fun dayMonthFromTimestamp(timestamp: Long): String = pattern1.format(Date(timestamp*1000))
        fun dayMonthYearFromTimestamp(timestamp: Long): String = pattern3.format(Date(timestamp*1000))
        fun hourMinuteFromTimestamp(timestamp: Long): String = pattern2.format(Date(timestamp*1000))
        fun generateUuid(): String = UUID.randomUUID().toString()

        private fun initSimpleDate(pattern: String): SimpleDateFormat {
            val r = SimpleDateFormat(pattern, Locale.getDefault())
            r.timeZone = TimeZone.getTimeZone("GMT")
            return r
        }

        fun getProperty(context: Context, key: String): String {
            val inputStream = context.assets.open("config.properties")
            val properties = Properties()
            properties.load(inputStream)
            return properties.getProperty(key)
        }

        fun getRepositoryFolder(context: Context): File = File(context.filesDir, getProperty(context, "repositoryNameFolder"))
        fun getImageFolder(context: Context): File = File(context.filesDir, getProperty(context, "imageFolder"))

        fun isUuidValid(uuid: String): Boolean {
            return try {
                UUID.fromString(uuid)
                true
            } catch (exception: IllegalArgumentException) {
               false
            }
        }

        fun searchMaker(search: String): String {
            var searchResult = ""
            if(!search.isBlank()) {
                val matcher: MutableList<String> = search.split(" ") as MutableList<String>
                matcher.retainAll { !it.isBlank() }
                searchResult = if (matcher.size < 2) {
                    matcher[0]
                } else {
                    matcher.joinToString(separator = "* ")
                }
            }
            return "$searchResult*"
        }

        fun writeToJsonFile(out: File, obj: Any) {
            val builder = StringBuilder(Klaxon().toJsonString(obj))
            val json = (Parser().parse(builder) as JsonObject).toJsonString(true)

            // Create directory and file if not exist
            out.parentFile?.mkdirs()
            out.createNewFile()

            out.writeText(json)
        }

        fun showNotification(context: Context, text: String) {
            val builder = NotificationCompat.Builder(context, "nectar")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Nectar")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(context)) {
                notify(10, builder.build())
            }
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo?.isConnected == true
        }

        fun exceptionToStacktrace(e: Exception): String {
            val sw = StringWriter()
            e.printStackTrace(PrintWriter(sw))
            return sw.toString()
        }

        fun addAlimentStateToList(out: MutableList<Pair<AlimentState, Int>>, aliment: AlimentState, weight: Int) {
            var exist = false
            for(i in 0 until out.size) {
                if(out[i].first == aliment) {
                    out[i] = aliment to out[i].second + weight
                    exist = true
                }
            }

            if(!exist) {
                out.add(aliment to weight)
            }
        }

        fun addAlimentStateListToList(out: MutableList<Pair<AlimentState, Int>>, aliments: List<Pair<AlimentState, Int>>) {
            for(a in aliments) {
                addAlimentStateToList(out, a.first, a.second)
            }
        }
    }
}