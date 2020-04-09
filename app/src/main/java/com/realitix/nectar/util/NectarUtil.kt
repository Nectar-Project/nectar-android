package com.realitix.nectar.util

import android.content.Context
import java.io.File
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
    }
}