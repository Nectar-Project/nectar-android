package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.ShoppingListAlimentState
import com.realitix.nectar.database.entity.ShoppingListAlimentStateRaw

@Dao
abstract class ShoppingListAlimentStateDao: GenericGetJoinDao<ShoppingListAlimentStateRaw, ShoppingListAlimentState>() {
    @Transaction
    @Query("SELECT * FROM ShoppingListAlimentStateRaw WHERE shoppingListUuid = :uuid1 AND alimentStateUuid = :uuid2")
    abstract override fun getLive(uuid1: String, uuid2: String): LiveData<ShoppingListAlimentState>

    @Transaction
    @Query("SELECT * FROM ShoppingListAlimentStateRaw WHERE shoppingListUuid = :uuid1 AND alimentStateUuid = :uuid2")
    abstract override fun get(uuid1: String, uuid2: String): ShoppingListAlimentState?

    @Transaction
    @Query("SELECT * FROM ShoppingListAlimentStateRaw WHERE shoppingListUuid = :uuid1 AND alimentStateUuid = :uuid2")
    abstract override suspend fun getSuspend(uuid1: String, uuid2: String): ShoppingListAlimentState?

    @Transaction
    @Query("SELECT * FROM ShoppingListAlimentStateRaw")
    abstract override fun list(): List<ShoppingListAlimentState>

    @Transaction
    @Query("SELECT * FROM ShoppingListAlimentStateRaw")
    abstract override fun listLive(): LiveData<List<ShoppingListAlimentState>>

    @Transaction
    @Query("SELECT * FROM ShoppingListAlimentStateRaw")
    abstract override suspend fun listSuspend(): List<ShoppingListAlimentState>
}