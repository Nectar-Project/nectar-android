package com.realitix.nectar.database.entity


import androidx.room.*


class BookImage(bookUuid: String, imageUuid: String): BookImageRaw(bookUuid, imageUuid)

@Entity(
    primaryKeys = ["bookUuid", "imageUuid"],
    foreignKeys = [
        ForeignKey(
            entity = BookRaw::class,
            parentColumns = ["uuid"],
            childColumns = ["bookUuid"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ImageRaw::class,
            parentColumns = ["uuid"],
            childColumns = ["imageUuid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value=["bookUuid"]),
        Index(value=["imageUuid"])
    ]
)
open class BookImageRaw (
    var bookUuid: String,
    var imageUuid: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookImageRaw

        if (bookUuid != other.bookUuid) return false
        if (imageUuid != other.imageUuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bookUuid.hashCode()
        result = 31 * result + imageUuid.hashCode()
        return result
    }
}




