package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class UtensilDao: BaseDao<UtensilRaw, Utensil>() {
    @Transaction
    @Query("SELECT * FROM UtensilRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<Utensil>

    @Transaction
    @Query("SELECT * FROM UtensilRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): Utensil?

    @Transaction
    @Query("SELECT * FROM UtensilRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): Utensil?

    @Transaction
    @Query("SELECT * FROM UtensilRaw")
    abstract override fun list(): List<Utensil>

    @Transaction
    @Query("SELECT * FROM UtensilRaw")
    abstract override fun listLive(): LiveData<List<Utensil>>

    @Transaction
    @Query("SELECT * FROM UtensilRaw")
    abstract override suspend fun listSuspend(): List<Utensil>
}