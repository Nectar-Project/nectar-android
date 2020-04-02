package com.realitix.nectar.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.nectar.database.entity.*

@Dao
interface BookReceipeDao: BaseDao<BookReceipeRaw>