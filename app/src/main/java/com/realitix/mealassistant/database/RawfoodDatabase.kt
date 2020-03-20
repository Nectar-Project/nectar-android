package com.realitix.mealassistant.database

import android.content.ContentValues
import android.content.Context
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.realitix.mealassistant.database.dao.*
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.util.MealUtil
import com.realitix.mealassistant.util.MealUtil.Companion.generateUuid
import com.realitix.mealassistant.util.ZipUtil
import java.io.File


@Database(
    entities = [
        AlimentRaw::class,
        AlimentNameFts::class,
        AlimentNameRaw::class,
        AlimentStateRaw::class,
        AlimentTagRaw::class,
        GitRepositoryRaw::class,
        MealRaw::class,
        MealAlimentRaw::class,
        MealReceipeRaw::class,
        MeasureRaw::class,
        MeasureNameRaw::class,
        ReceipeRaw::class,
        ReceipeNameRaw::class,
        ReceipeNameFts::class,
        ReceipeStepRaw::class,
        ReceipeStepAlimentRaw::class,
        ReceipeStepReceipeRaw::class,
        ReceipeTagRaw::class,
        ReceipeUtensilRaw::class,
        TagRaw::class,
        TagNameRaw::class,
        UtensilRaw::class
    ],
    exportSchema = false,
    version = 4
)
abstract class MealDatabase : RoomDatabase() {
    abstract fun alimentDao(): AlimentDao
    abstract fun mealDao(): MealDao
    abstract fun receipeDao(): ReceipeDao
    abstract fun receipeStepDao(): ReceipeStepDao
    abstract fun receipeStepAlimentDao(): ReceipeStepAlimentDao
    abstract fun receipeStepReceipeDao(): ReceipeStepReceipeDao
    abstract fun mealAlimentDao(): MealAlimentDao
    abstract fun mealReceipeDao(): MealReceipeDao

    companion object {
        private var instance: MealDatabase? = null
        @Synchronized
        fun getInstance(context: Context): MealDatabase {
            if (instance == null) {
                val callback = object: Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        // Create DB Entry for default repository
                        val repo = GitRepository(
                            generateUuid(),
                            MealUtil.getProperty(context, "defaultGitRepositoryName"),
                            MealUtil.getProperty(context, "defaultGitRepositoryUrl"),
                            true,
                            null
                        )
                        val contentValues = ContentValues()
                        contentValues.put("uuid", repo.uuid)
                        contentValues.put("name", repo.name)
                        contentValues.put("url", repo.url)
                        contentValues.put("readOnly", repo.readOnly)
                        contentValues.putNull("credentials_username")
                        contentValues.putNull("credentials_password")
                        db.insert("GitRepositoryRaw", OnConflictStrategy.IGNORE, contentValues)

                        // Uncompress the default repository
                        val zipName = MealUtil.getProperty(context, "defaultGitRepositoryName")
                        val rName = MealUtil.getProperty(context, "repositoryNameFolder")
                        ZipUtil.unzipFromAssets(context, zipName, File(context.filesDir.toString(), rName).toString())
                    }
                }

                instance = Room.databaseBuilder(
                        context.applicationContext,
                        MealDatabase::class.java,
                        MealUtil.getProperty(context, "databaseName"))
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build()
            }
            return instance as MealDatabase
        }
    }
}