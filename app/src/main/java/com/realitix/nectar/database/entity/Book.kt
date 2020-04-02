package com.realitix.nectar.database.entity


import androidx.room.*


class Book(uuid: String, author: String, publishDate: Long):
    BookRaw(uuid, author, publishDate) {
    @Relation(parentColumn = "uuid", entityColumn = "bookUuid", entity = BookNameRaw::class)
    lateinit var names: List<BookName>
    @Relation(parentColumn = "uuid", entityColumn = "bookUuid", entity = BookReceipeRaw::class)
    lateinit var receipes: List<BookReceipe>
    @Relation(parentColumn = "uuid", entityColumn = "bookUuid", entity = BookImageRaw::class)
    lateinit var images: List<BookImage>
}

@Entity
open class BookRaw (
    @PrimaryKey
    var uuid: String,
    var author: String,
    var publishDate: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookRaw

        if (uuid != other.uuid) return false
        if (author != other.author) return false
        if (publishDate != other.publishDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + publishDate.hashCode()
        return result
    }
}




