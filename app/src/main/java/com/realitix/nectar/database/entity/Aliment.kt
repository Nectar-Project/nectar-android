package com.realitix.nectar.database.entity


import androidx.room.*
import com.realitix.nectar.repository.AlimentStateRepository
import kotlinx.coroutines.runBlocking


class Aliment(uuid: String, nameUuid: String): AlimentRaw(uuid, nameUuid) {
    @Relation(parentColumn = "nameUuid", entityColumn = "uuid", entity = StringKeyRaw::class)
    lateinit var name: StringKey
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentTagRaw::class)
    lateinit var tags: List<AlimentTag>
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentImageRaw::class)
    lateinit var images: List<AlimentImage>
    @Relation(parentColumn = "uuid", entityColumn = "alimentUuid", entity = AlimentPriceRaw::class)
    lateinit var price: AlimentPrice

    fun getName(): String = name.getValue()

    // Bug with room preventing nested relation
    fun getStates(rAlimentState: AlimentStateRepository): List<AlimentState> {
        var res = listOf<AlimentState>()
        runBlocking {
            res = rAlimentState.listByAlimentSuspend(uuid)
        }

        return res
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
open class AlimentRaw (
    @PrimaryKey
    var uuid: String,
    var nameUuid: String
): UuidInterface {
    override fun getEntityUuid() = uuid

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlimentRaw

        if (uuid != other.uuid) return false
        if (nameUuid != other.nameUuid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + nameUuid.hashCode()
        return result
    }
}