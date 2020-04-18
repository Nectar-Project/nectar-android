package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.ReceipeUtensil
import com.realitix.nectar.database.entity.ReceipeUtensilRaw

@Dao
abstract class ReceipeUtensilDao: BaseDao<ReceipeUtensilRaw, ReceipeUtensil>() {
    @Transaction
    @Query("SELECT * FROM ReceipeUtensilRaw WHERE receipeUuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<ReceipeUtensil>

    @Transaction
    @Query("SELECT * FROM ReceipeUtensilRaw WHERE receipeUuid = :uuid")
    abstract override fun get(uuid: String): ReceipeUtensil?

    @Transaction
    @Query("SELECT * FROM ReceipeUtensilRaw WHERE receipeUuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): ReceipeUtensil?

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