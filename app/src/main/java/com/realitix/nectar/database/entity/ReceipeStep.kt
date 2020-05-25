package com.realitix.nectar.database.entity

import androidx.room.*


class ReceipeStep(uuid: String, receipeUuid: String, previousStepUuid: String?, descriptionUuid: String, duration: Int):
    ReceipeStepRaw(uuid, receipeUuid, previousStepUuid, descriptionUuid, duration) {
    @Relation(parentColumn = "uuid", entityColumn = "stepUuid", entity = ReceipeStepAlimentStateRaw::class)
    lateinit var aliments: List<ReceipeStepAlimentState>
    // Can't return list of ReceipeStepReceipe because it creates an inner loop and a stackoverflow
    @Relation(parentColumn = "uuid", entityColumn = "stepUuid", entity = ReceipeStepReceipeRaw::class)
    lateinit var receipes: List<ReceipeStepReceipe>
    @Relation(parentColumn = "descriptionUuid", entityColumn = "uuid", entity = StringKeyRaw::class)
    lateinit var description: StringKey

    fun getDescription(): String {
        return description.getValue()
    }
}

@Entity(
    foreignKeys = [
        ForeignKey(
        entity = ReceipeRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["receipeUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = StringKeyRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["descriptionUuid"],
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = ReceipeStepRaw::class,
        parentColumns = ["uuid"],
        childColumns = ["previousStepUuid"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value=["receipeUuid"]),
        Index(value=["descriptionUuid"]),
        Index(value=["previousStepUuid"])
    ]
)
open class ReceipeStepRaw(
    @PrimaryKey
    var uuid: String,
    var receipeUuid: String,
    var previousStepUuid: String?,
    var descriptionUuid: String,
    // Duration in minutes
    var duration: Int
): UuidInterface {
    override fun getEntityUuid() = uuid
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReceipeStepRaw

        if (uuid != other.uuid) return false
        if (receipeUuid != other.receipeUuid) return false
        if (previousStepUuid != other.previousStepUuid) return false
        if (descriptionUuid != other.descriptionUuid) return false
        if (duration != other.duration) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuid.hashCode()
        result = 31 * result + receipeUuid.hashCode()
        result = 31 * result + (previousStepUuid?.hashCode() ?: 0)
        result = 31 * result + descriptionUuid.hashCode()
        result = 31 * result + duration
        return result
    }
}