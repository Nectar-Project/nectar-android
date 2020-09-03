package com.realitix.nectar.database.entity


import androidx.room.*


class ShoppingListAlimentState(shoppingListUuid: String, alimentStateUuid: String, weight: Int):
    ShoppingListAlimentStateRaw(shoppingListUuid, alimentStateUuid, weight) {
}

@Entity(
    primaryKeys = ["alimentStateUuid", "shoppingListUuid"],
    foreignKeys = [ForeignKey(
        entity = AlimentStateRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["alimentStateUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ShoppingListRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["shoppingListUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["alimentStateUuid"]),
        Index(value=["shoppingListUuid"])
    ]
)
open class ShoppingListAlimentStateRaw (
    var shoppingListUuid: String,
    var alimentStateUuid: String,
    var weight: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShoppingListAlimentStateRaw

        if (shoppingListUuid != other.shoppingListUuid) return false
        if (alimentStateUuid != other.alimentStateUuid) return false
        if (weight != other.weight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = shoppingListUuid.hashCode()
        result = 31 * result + alimentStateUuid.hashCode()
        result = 31 * result + weight
        return result
    }

}




