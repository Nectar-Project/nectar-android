package com.realitix.nectar.database.entity


import androidx.room.*


class Book(uuid: String, nameUuid: String, author: String, publishDate: Long):
    BookRaw(uuid, nameUuid, author, publishDate) {
    @Relation(parentColumn = "nameUuid", entityColumn = "uuid", entity = StringKeyRaw::class)
    lateinit var name: StringKey
    @Relation(parentColumn = "uuid", entityColumn = "bookUuid", entity = BookReceipeRaw::class)
    lateinit var receipes: List<BookReceipe>
    @Relation(parentColumn = "uuid", entityColumn = "bookUuid", entity = BookImageRaw::class)
    lateinit var images: List<BookImage>

    fun getName(): String {
        return name.getValue()
    }
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = StringKeyRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["nameUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["nameUuid"])
    ]
)
open class BookRaw (
    @PrimaryKey
    var uuid: String,
    var nameUuid: String,
    var author: String,
    var publishDate: Long
): UuidInterface {
    override fun getEntityUuid() = uuid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookRaw

        if (uuid != other.uuid) return false
        if (nameUuid != other.nameUuid) return false
        if (author != other.author) return false
        if (publishDate != other.publishDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + nameUuid.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + publishDate.hashCode()
        return result
    }

}




