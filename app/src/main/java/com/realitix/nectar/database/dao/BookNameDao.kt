package com.realitix.nectar.database.dao

import androidx.room.Dao
import com.realitix.nectar.database.entity.AlimentNameRaw
import com.realitix.nectar.database.entity.BookNameRaw

@Dao
interface BookNameDao: BaseDao<BookNameRaw>