package com.realitix.nectar.database.entity


import androidx.room.*


class StringKey(uuid: String): StringKeyRaw(uuid) {
    @Relation(parentColumn = "uuid", entityColumn = "stringKeyUuid", entity = StringKeyValueRaw::class)
    lateinit var strings: List<StringKeyValue>

    fun getValue(): String {
        return strings[0].value
    }
}

@Entity
open class StringKeyRaw (
    @PrimaryKey
    var uuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StringKeyRaw

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }
}




