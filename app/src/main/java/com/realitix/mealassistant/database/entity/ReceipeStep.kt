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
    )),
    indices = arrayOf(
        Index(value=["receipeId"])
    )
)
open class ReceipeStep(var receipeId: Long, var order: Int, var description: String?, var duration: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    constructor(): this(0, 0, "", 0)
}