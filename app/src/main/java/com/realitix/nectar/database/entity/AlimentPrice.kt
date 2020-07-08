package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


// We separate price from aliment to manage custom sync with github repo
class AlimentPrice(uuid: String, alimentUuid: String, price: Float):
    AlimentPriceRaw(uuid, alimentUuid, price)

@Entity(
    primaryKeys = ["uuid"],
    foreignKeys = [ForeignKey(
        entity = AlimentRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentUuid"])
    ]
)
open class AlimentPriceRaw (
    var uuid: String,
    var alimentUuid: String,
    var price: Float
): UuidInterface {
    override fun getEntityUuid() = uuid
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlimentPriceRaw

        if (uuid != other.uuid) return false
        if (alimentUuid != other.alimentUuid) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + alimentUuid.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }
}