package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import com.realitix.mealassistant.database.entity.ReceipeNameRaw
import com.realitix.mealassistant.database.entity.StateNameRaw

@Dao
interface ReceipeNameDao: BaseDao<ReceipeNameRaw>