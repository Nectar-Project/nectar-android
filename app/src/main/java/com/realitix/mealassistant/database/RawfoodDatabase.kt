package com.realitix.mealassistant.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.realitix.mealassistant.database.dao.*
import com.realitix.mealassistant.database.entity.*

@Database(
    entities = arrayOf(
        Aliment::class,
        AlimentCategory::class,
        Receipe::class,
        ReceipeStep::class,
        ReceipeStepAliment::class,
        ReceipeStepReceipe::class,
        Meal::class,
        MealAliment::class,
        MealReceipe::class),
    exportSchema = false,
    version = 1
)
abstract class RawfoodDatabase : RoomDatabase() {
    abstract fun alimentCategoryDao(): AlimentCategoryDao?
    abstract fun alimentDao(): AlimentDao?
    abstract fun mealDao(): MealDao?
    abstract fun receipeDao(): ReceipeDao?
    abstract fun receipeStepDao(): ReceipeStepDao?
    abstract fun receipeStepAlimentDao(): ReceipeStepAlimentDao?
    abstract fun receipeStepReceipeDao(): ReceipeStepReceipeDao?
    abstract fun mealAlimentDao(): MealAlimentDao?
    abstract fun mealReceipeDao(): MealReceipeDao?

    companion object {
        private const val DB_NAME = "rawfood_db"
        private var instance: RawfoodDatabase? = null
        @Synchronized
        fun getInstance(context: Context): RawfoodDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    RawfoodDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}