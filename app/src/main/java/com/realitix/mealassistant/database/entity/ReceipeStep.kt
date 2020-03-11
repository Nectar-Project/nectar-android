package com.realitix.mealassistant.database.entity

import androidx.room.*


class ReceipeStep(receipeId: Long, order: Int, description: String, duration: Int):
    ReceipeStepRaw(receipeId, order, description, duration) {
    @Relation(parentColumn = "id", entityColumn = "stepId", entity = ReceipeStepAlimentRaw::class)
    var aliments: List<ReceipeStepAliment>? = null
    // Can't return list of ReceipeStepReceipe because it creates an inner loop and a stackoverflow
    @Relation(parentColumn = "id", entityColumn = "stepId", entity = ReceipeStepReceipeRaw::class)
    var receipes: List<ReceipeStepReceipe>? = null
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("receipeId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeId"])
    ]
)
open class ReceipeStepRaw(var receipeId: Long, var order: Int, var description: String, var duration: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}