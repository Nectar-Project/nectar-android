package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import com.realitix.mealassistant.database.entity.AlimentStateMeasure
import com.realitix.mealassistant.database.entity.AlimentStateMeasureRaw

@Dao
interface AlimentStateMeasureDao: BaseDao<AlimentStateMeasureRaw>