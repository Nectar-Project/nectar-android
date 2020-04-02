package com.realitix.nectar.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index


class BookName(bookUuid: String, language: String, name: String): BookNameRaw(bookUuid, language, name)

@Entity(
    primaryKeys = ["bookUuid", "language"],
    foreignKeys = [ForeignKey(
        entity = BookRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["bookUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["bookUuid"]),
        Index(value=["language"])
    ]
)
open class BookNameRaw (
    var bookUuid: String,
    var language: String,
    var name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookNameRaw

        if (bookUuid != other.bookUuid) return false
        if (language != other.language) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bookUuid.hashCode()
        result = 31 * result + language.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}