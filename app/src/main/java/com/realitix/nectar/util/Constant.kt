package com.realitix.nectar.util

enum class EntityType(val folderName: String) {
    // Position is very important, order of the import
    UNKNOW(""),
    STRING_KEY("strings"),
    IMAGE("images"),
    MEASURE("measures"),
    STATE("states"),
    TAG("tags"),
    UTENSIL("utensils"),
    ALIMENT("aliments"),
    ALIMENT_PRICE("prices"),
    RECEIPE("receipes"),
    BOOK("books"),
    MEAL("meals")
}