package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
abstract class ShoppingListDao: GenericGetUuidDao<ShoppingListRaw, ShoppingList>() {
    @Transaction
    @Query("SELECT * FROM ShoppingListRaw WHERE uuid = :uuid")
    abstract override fun getLive(uuid: String): LiveData<ShoppingList>

    @Transaction
    @Query("SELECT * FROM ShoppingListRaw WHERE uuid = :uuid")
    abstract override fun get(uuid: String): ShoppingList?

    @Transaction
    @Query("SELECT * FROM ShoppingListRaw WHERE uuid = :uuid")
    abstract override suspend fun getSuspend(uuid: String): ShoppingList?

    @Transaction
    @Query("SELECT * FROM ShoppingListRaw")
    abstract override fun list(): List<ShoppingList>

    @Transaction
    @Query("SELECT * FROM ShoppingListRaw")
    abstract override fun listLive(): LiveData<List<ShoppingList>>

    @Transaction
    @Query("SELECT * FROM ShoppingListRaw")
    abstract override suspend fun listSuspend(): List<ShoppingList>
}