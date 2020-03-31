package com.realitix.mealassistant.util

enum class EntityType(val folderName: String) {
    // Position is very important, order of the import
    UNKNOW(""),
    IMAGE("images"),
    MEASURE("measures"),
    STATE("states"),
    TAG("tags"),
    UTENSIL("utensils"),
    ALIMENT("aliments"),
    RECEIPE("receipes"),
    MEAL("meals")
}