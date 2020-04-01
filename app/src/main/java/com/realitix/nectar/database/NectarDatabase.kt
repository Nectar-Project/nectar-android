package com.realitix.nectar.database

import android.content.ContentValues
import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.realitix.nectar.database.dao.*
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid


@Database(
    entities = [
        AlimentRaw::class,
        AlimentImageRaw::class,
        AlimentNameRaw::class,
        AlimentNameFts::class,
        AlimentStateRaw::class,
        AlimentStateMeasureRaw::class,
        AlimentTagRaw::class,
        DatabaseUpdateRaw::class,
        GitRepositoryRaw::class,
        ImageRaw::class,
        MealRaw::class,
        MealAlimentRaw::class,
        MealReceipeRaw::class,
        MeasureRaw::class,
        MeasureNameRaw::class,
        ReceipeRaw::class,
        ReceipeImageRaw::class,
        ReceipeNameRaw::class,
        ReceipeNameFts::class,
        ReceipeStepRaw::class,
        ReceipeStepAlimentRaw::class,
        ReceipeStepReceipeRaw::class,
        ReceipeTagRaw::class,
        ReceipeUtensilRaw::class,
        StateRaw::class,
        StateNameRaw::class,
        TagRaw::class,
        TagNameRaw::class,
        UtensilRaw::class,
        UtensilNameRaw::class
    ],
    exportSchema = false,
    version = 5
)
@TypeConverters(Converter::class)
abstract class NectarDatabase : RoomDatabase() {
    abstract fun alimentDao(): AlimentDao
    abstract fun alimentImageDao(): AlimentImageDao
    abstract fun alimentNameDao(): AlimentNameDao
    abstract fun alimentStateDao(): AlimentStateDao
    abstract fun alimentStateMeasureDao(): AlimentStateMeasureDao
    abstract fun alimentTagDao(): AlimentTagDao
    abstract fun databaseUpdateDao(): DatabaseUpdateDao
    abstract fun gitRepositoryDao(): GitRepositoryDao
    abstract fun imageDao(): ImageDao
    abstract fun mealAlimentDao(): MealAlimentDao
    abstract fun mealDao(): MealDao
    abstract fun mealReceipeDao(): MealReceipeDao
    abstract fun measureDao(): MeasureDao
    abstract fun measureNameDao(): MeasureNameDao
    abstract fun receipeDao(): ReceipeDao
    abstract fun receipeNameDao(): ReceipeNameDao
    abstract fun receipeTagDao(): ReceipeTagDao
    abstract fun receipeUtensilDao(): ReceipeUtensilDao
    abstract fun receipeStepAlimentDao(): ReceipeStepAlimentDao
    abstract fun receipeStepDao(): ReceipeStepDao
    abstract fun receipeStepReceipeDao(): ReceipeStepReceipeDao
    abstract fun stateDao(): StateDao
    abstract fun stateNameDao(): StateNameDao
    abstract fun tagDao(): TagDao
    abstract fun tagNameDao(): TagNameDao
    abstract fun utensilDao(): UtensilDao
    abstract fun utensilNameDao(): UtensilNameDao

    companion object {
        private var instance: NectarDatabase? = null
        @Synchronized
        fun getInstance(context: Context): NectarDatabase {
            if (instance == null) {
                val callback = object: Callback() {
                    fun init(db: SupportSQLiteDatabase) {
                        // Create DB Entry for default repository
                        val repo = GitRepository(
                            generateUuid(),
                            NectarUtil.getProperty(context, "defaultGitRepositoryName"),
                            NectarUtil.getProperty(context, "defaultGitRepositoryUrl"),
                            rescan = true,
                            readOnly = true,
                            lastCheck = 0,
                            frequency = 60*60*6,
                            credentials = null
                        )
                        val contentValues = ContentValues()
                        contentValues.put("uuid", repo.uuid)
                        contentValues.put("name", repo.name)
                        contentValues.put("url", repo.url)
                        contentValues.put("rescan", repo.rescan)
                        contentValues.put("readOnly", repo.readOnly)
                        contentValues.put("lastCheck", repo.lastCheck)
                        contentValues.put("frequency", repo.frequency)
                        contentValues.putNull("credentials_username")
                        contentValues.putNull("credentials_password")
                        db.insert("GitRepositoryRaw", OnConflictStrategy.IGNORE, contentValues)
                    }

                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        init(db)
                    }

                    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
                        super.onDestructiveMigration(db)
                        init(db)
                    }
                }

                instance = Room.databaseBuilder(
                        context.applicationContext,
                        NectarDatabase::class.java,
                        NectarUtil.getProperty(context, "databaseName"))
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build()
            }
            return instance as NectarDatabase
        }
    }
}