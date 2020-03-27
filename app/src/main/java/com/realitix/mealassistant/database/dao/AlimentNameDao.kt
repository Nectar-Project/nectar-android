package com.realitix.mealassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.realitix.mealassistant.database.entity.AlimentName
import com.realitix.mealassistant.database.entity.AlimentNameRaw

@Dao
interface AlimentNameDao: BaseDao<AlimentNameRaw>