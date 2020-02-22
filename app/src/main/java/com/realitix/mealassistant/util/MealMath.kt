package com.realitix.mealassistant.util

import kotlin.math.floor

class MealMath {
    companion object {
        private const val nbSecondsInMinute: Long = 60
        private const val nbSecondsInHour: Long = nbSecondsInMinute*60
        private const val nbSecondsInDay: Long = nbSecondsInHour*24

        fun beginDayTimestamp(timestamp: Long): Long {
            return floor((timestamp / nbSecondsInDay).toDouble()).toLong() * nbSecondsInDay
        }

        fun endDayTimestamp(timestamp: Long): Long {
            return beginDayTimestamp(timestamp) + nbSecondsInDay
        }

        fun hourTimestamp(hour: Int): Long {
            return hour * nbSecondsInHour
        }

        fun minuteTimestamp(minute: Int): Long {
            return minute * nbSecondsInMinute
        }
    }
}