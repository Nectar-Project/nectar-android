package com.realitix.nectar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.AlimentState
import com.realitix.nectar.database.entity.AlimentStateRaw


@Dao
interface AlimentStateDao: BaseDao<AlimentStateRaw> {
    @Transaction
    @Query("SELECT * FROM AlimentStateRaw WHERE uuid = :uuid")
    fun getLive(uuid: String): LiveData<AlimentState>

    @Transaction
    @Query("SELECT * FROM AlimentStateRaw WHERE uuid = :uuid")
    fun get(uuid: String): AlimentState?
}