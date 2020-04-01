package com.realitix.nectar.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
interface UtensilDao: BaseDao<UtensilRaw> {
    @Transaction
    @Query("SELECT * FROM UtensilRaw WHERE uuid = :uuid")
    fun get(uuid: String): Utensil?
}