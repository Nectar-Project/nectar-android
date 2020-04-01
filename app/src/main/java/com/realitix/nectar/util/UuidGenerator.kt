package com.realitix.nectar.util

import java.util.*

class UuidGenerator: UuidGeneratorInterface {
    override fun generateUuid(): String = UUID.randomUUID().toString()
}