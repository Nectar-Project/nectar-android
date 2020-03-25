package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import com.realitix.mealassistant.database.entity.AlimentTagRaw
import com.realitix.mealassistant.database.entity.ReceipeTagRaw
import com.realitix.mealassistant.database.entity.ReceipeUtensilRaw

@Dao
interface ReceipeUtensilDao: BaseDao<ReceipeUtensilRaw>