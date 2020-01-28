package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = arrayOf(ForeignKey(
        entity = Receipe::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("receipeId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ReceipeStep::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("stepId"),
        onDelete = ForeignKey.CASCADE
    )),
    indices = arrayOf(
        Index(value=["receipeId"]),
        Index(value=["stepId"])
    )
)
open class ReceipeStepReceipe(var receipeId: Long, var stepId: Long) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    constructor(): this(0, 0)
}