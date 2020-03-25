package com.realitix.mealassistant.database.dao

import androidx.room.Dao
import com.realitix.mealassistant.database.entity.StateNameRaw
import com.realitix.mealassistant.database.entity.TagNameRaw
import com.realitix.mealassistant.database.entity.UtensilNameRaw

@Dao
interface UtensilNameDao: BaseDao<UtensilNameRaw>