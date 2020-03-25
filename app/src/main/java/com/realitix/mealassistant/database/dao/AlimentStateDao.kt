package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.realitix.mealassistant.database.entity.AlimentStateRaw
import com.realitix.mealassistant.database.entity.AlimentTagRaw
import com.realitix.mealassistant.database.entity.StateRaw

@Dao
interface AlimentStateDao: BaseDao<AlimentStateRaw>