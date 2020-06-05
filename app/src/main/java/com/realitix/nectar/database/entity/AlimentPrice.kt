package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class AlimentPrice(alimentUuid: String, price: Float, description: String):
    AlimentPriceRaw(alimentUuid, price, description)

@Entity(
    primaryKeys = ["alimentUuid"],
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentUuid"],
        onDelete = ForeignKey.CASCADE
    )]
)
open class AlimentPriceRaw (
    var alimentUuid: String,
    var price: Float,
    var description: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlimentPriceRaw

        if (alimentUuid != other.alimentUuid) return false
        if (price != other.price) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = alimentUuid.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }

}