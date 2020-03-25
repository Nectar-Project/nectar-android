package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import com.realitix.mealassistant.database.entity.AlimentTagRaw
import com.realitix.mealassistant.database.entity.ReceipeTagRaw

@Dao
interface ReceipeTagDao: BaseDao<ReceipeTagRaw>