package com.realitix.nectar.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
interface StringKeyValueDao: BaseDao<StringKeyValueRaw> {
    @Transaction
    @Query("SELECT * FROM StringKeyValueRaw WHERE stringKeyUuid=:uuid AND language=:lang")
    fun get(uuid: String, lang: String): StringKeyValue?
}