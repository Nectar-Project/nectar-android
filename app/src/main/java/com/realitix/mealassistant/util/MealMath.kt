package com.realitix.mealassistant.util

import kotlin.math.floor

class MealMath {
    companion object {
        const val nbSeconds: Long = 86400

        fun beginDayTimestamp(timestamp: Long): Long {
            return floor((timestamp / nbSeconds).toDouble()).toLong() * nbSeconds
        }

        fun endDayTimestamp(timestamp: Long): Long {
            return beginDayTimestamp(timestamp) + nbSeconds
        }
    }
}