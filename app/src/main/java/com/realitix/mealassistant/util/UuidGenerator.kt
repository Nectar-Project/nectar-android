package com.realitix.mealassistant.util

import java.util.*

class UuidGenerator: UuidGeneratorInterface {
    override fun generateUuid(): String = UUID.randomUUID().toString()
}