package com.realitix.mealassistant.database.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    foreignKeys = arrayOf(ForeignKey(
        entity = AlimentCategory::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("categoryId"),
        onDelete = ForeignKey.CASCADE
    )),
    indices = arrayOf(
        Index(value=["name"], unique=true),
        Index(value=["categoryId"])
    )
)
class Aliment(
    var name: String,
    var nameSearch: String,
    var categoryId: Long,
    nutrition: AlimentNutrition
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @Embedded
    var nutrition: AlimentNutrition = nutrition
}