package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.ReceipeUtensil
import com.realitix.nectar.database.entity.ReceipeUtensilRaw

@Dao
abstract class ReceipeUtensilDao: GenericGetJoinDao<ReceipeUtensilRaw, ReceipeUtensil>() {
    @Transaction
    @Query("SELECT * FROM ReceipeUtensilRaw WHERE receipeUuid = :uuid1 AND utensilUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<ReceipeUtensil>

    @Transaction
    @Query("SELECT * FROM ReceipeUtensilRaw WHERE receipeUuid = :uuid1 AND utensilUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): ReceipeUtensil?

    @Transaction
    @Query("SELECT * FROM ReceipeUtensilRaw WHERE receipeUuid = :uuid1 AND utensilUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): ReceipeUtensil?

    @Transaction
    @Query("SELECT * FROM ReceipeUtensilRaw")
    abstract override fun list(): List<ReceipeUtensil>

    @Transaction
    @Query("SELECT * FROM ReceipeUtensilRaw")
    abstract override fun listLive(): LiveData<List<ReceipeUtensil>>

    @Transaction
    @Query("SELECT * FROM ReceipeUtensilRaw")
    abstract override suspend fun listSuspend(): List<ReceipeUtensil>
}