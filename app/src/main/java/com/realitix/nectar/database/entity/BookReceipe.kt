package com.realitix.nectar.database.entity


import androidx.room.*


class BookReceipe(bookUuid: String, receipeUuid: String): BookReceipeRaw(bookUuid, receipeUuid)

@Entity(
    primaryKeys = ["bookUuid", "receipeUuid"],
    foreignKeys = [
        ForeignKey(
            entity = BookRaw::class,
            parentColumns = ["uuid"],
            childColumns = ["bookUuid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ReceipeRaw::class,
            parentColumns = ["uuid"],
            childColumns = ["receipeUuid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value=["bookUuid"]),
        Index(value=["receipeUuid"])
    ]
)
open class BookReceipeRaw (
    var bookUuid: String,
    var receipeUuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookReceipeRaw

        if (bookUuid != other.bookUuid) return false
        if (receipeUuid != other.receipeUuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bookUuid.hashCode()
        result = 31 * result + receipeUuid.hashCode()
        return result
    }
}




