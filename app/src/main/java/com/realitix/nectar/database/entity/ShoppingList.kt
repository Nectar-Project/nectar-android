package com.realitix.nectar.database.entity


import androidx.room.*


class ShoppingList(uuid: String, beginTimestamp: Long, endTimestamp: Long): ShoppingListRaw(uuid, beginTimestamp, endTimestamp) {
    @Relation(parentColumn = "uuid", entityColumn = "shoppingListUuid", entity = ShoppingListAlimentStateRaw::class)
    lateinit var aliments: List<ShoppingListAlimentState>
}

@Entity
open class ShoppingListRaw (
    @PrimaryKey
    var uuid: String,
    var beginTimestamp: Long,
    var endTimestamp: Long
): UuidInterface {
    override fun getEntityUuid() = uuid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShoppingListRaw

        if (uuid != other.uuid) return false
        if (beginTimestamp != other.beginTimestamp) return false
        if (endTimestamp != other.endTimestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + beginTimestamp.hashCode()
        result = 31 * result + endTimestamp.hashCode()
        return result
    }
}




