package com.realitix.mealassistant.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = arrayOf(ForeignKey(
        entity = Aliment::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("alimentId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ReceipeStep::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("stepId"),
        onDelete = ForeignKey.CASCADE
    )),
    indices = arrayOf(
        Index(value=["alimentId"]),
        Index(value=["stepId"])
    )
)
open class ReceipeStepAliment(var alimentId: Long, var stepId: Long, var quantity: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    constructor(): this(0, 0, 0)
}