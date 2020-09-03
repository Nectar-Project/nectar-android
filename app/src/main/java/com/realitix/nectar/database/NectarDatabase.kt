package com.realitix.nectar.database

import android.content.ContentValues
import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.realitix.nectar.database.dao.*
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid


@Database(
    entities = [
        AlimentRaw::class,
        AlimentImageRaw::class,
        AlimentPriceRaw::class,
        AlimentStateRaw::class,
        AlimentStateMeasureRaw::class,
        AlimentTagRaw::class,
        BookRaw::class,
        BookImageRaw::class,
        BookReceipeRaw::class,
        DatabaseUpdateRaw::class,
        GitRepositoryRaw::class,
        ImageRaw::class,
        MealRaw::class,
        MealAlimentStateRaw::class,
        MealReceipeRaw::class,
        MeasureRaw::class,
        StringKeyRaw::class,
        StringKeyValueRaw::class,
        StringKeyValueFts::class,
        ReceipeRaw::class,
        ReceipeImageRaw::class,
        ReceipeMeasureRaw::class,
        ReceipeStepRaw::class,
        ReceipeStepAlimentStateRaw::class,
        ReceipeStepReceipeRaw::class,
        ReceipeTagRaw::class,
        ReceipeUtensilRaw::class,
        ShoppingListRaw::class,
        ShoppingListAlimentStateRaw::class,
        StateRaw::class,
        TagRaw::class,
        UtensilRaw::class
    ],
    exportSchema = false,
    version = 3
)
@TypeConverters(Converter::class)
abstract class NectarDatabase : RoomDatabase() {
    abstract fun alimentDao(): AlimentDao
    abstract fun alimentImageDao(): AlimentImageDao
    abstract fun alimentPriceDao(): AlimentPriceDao
    abstract fun alimentStateDao(): AlimentStateDao
    abstract fun alimentStateMeasureDao(): AlimentStateMeasureDao
    abstract fun alimentTagDao(): AlimentTagDao
    abstract fun bookDao(): BookDao
    abstract fun bookImageDao(): BookImageDao
    abstract fun bookReceipeDao(): BookReceipeDao
    abstract fun databaseUpdateDao(): DatabaseUpdateDao
    abstract fun gitRepositoryDao(): GitRepositoryDao
    abstract fun imageDao(): ImageDao
    abstract fun mealAlimentDao(): MealAlimentStateDao
    abstract fun mealDao(): MealDao
    abstract fun mealReceipeDao(): MealReceipeDao
    abstract fun measureDao(): MeasureDao
    abstract fun receipeDao(): ReceipeDao
    abstract fun receipeMeasureDao(): ReceipeMeasureDao
    abstract fun receipeTagDao(): ReceipeTagDao
    abstract fun receipeUtensilDao(): ReceipeUtensilDao
    abstract fun receipeStepAlimentDao(): ReceipeStepAlimentStateDao
    abstract fun receipeStepDao(): ReceipeStepDao
    abstract fun receipeStepReceipeDao(): ReceipeStepReceipeDao
    abstract fun shoppingListAlimentStateDao(): ShoppingListAlimentStateDao
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun stateDao(): StateDao
    abstract fun stringKeyDao(): StringKeyDao
    abstract fun stringKeyValueDao(): StringKeyValueDao
    abstract fun tagDao(): TagDao
    abstract fun utensilDao(): UtensilDao

    companion object {
        private var instance: NectarDatabase? = null
        private var callback: Callback? = null

        @Synchronized
        fun getInstance(context: Context): NectarDatabase {
            if (instance == null) {
                if(callback == null) {
                    setInitCallback(getDefaultInitCallback(context))
                }

                instance = Room.databaseBuilder(
                        context.applicationContext,
                        NectarDatabase::class.java,
                        NectarUtil.getProperty(context, "databaseName"))
                    .fallbackToDestructiveMigration()
                    .addCallback(callback!!)
                    .build()
            }
            return instance as NectarDatabase
        }

        @Synchronized
        fun setInitCallback(cb: Callback?) {
            callback = cb
        }

        private fun getDefaultInitCallback(context: Context): Callback {
            return object: Callback() {
                fun init(db: SupportSQLiteDatabase) {
                    // Create DB Entry for default repository
                    val selective = GitSelectiveSynchronization(
                        GitSelectiveSynchronization.SynchronizationType.SELECTIVE,
                        EntityType.ALIMENT
                    )
                    val repo = GitRepositoryRaw(
                        generateUuid(),
                        NectarUtil.getProperty(context, "defaultGitRepositoryName"),
                        NectarUtil.getProperty(context, "defaultGitRepositoryUrl"),
                        enabled = true,
                        rescan = true,
                        readOnly = true,
                        lastCheck = 0,
                        frequency = 60 * 60 * 6,
                        selectiveSynchronization = selective,
                        credentials = null
                    )
                    db.insert("GitRepositoryRaw", OnConflictStrategy.IGNORE, gitRepositoryRawToContentValues(repo))
                }

                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    init(db)
                }

                /*override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    // Check if not exist and create it
                    init(db)
                }*/
            }
        }

        fun gitRepositoryRawToContentValues(repo: GitRepositoryRaw): ContentValues {
            val contentValues = ContentValues()
            contentValues.put("uuid", repo.uuid)
            contentValues.put("name", repo.name)
            contentValues.put("url", repo.url)
            contentValues.put("enabled", repo.enabled)
            contentValues.put("rescan", repo.rescan)
            contentValues.put("readOnly", repo.readOnly)
            contentValues.put("lastCheck", repo.lastCheck)
            contentValues.put("frequency", repo.frequency)

            if(repo.selectiveSynchronization == null) {
                contentValues.putNull("selective_synchronizationType")
                contentValues.putNull("selective_level")
                contentValues.putNull("selective_bookUuid")
            }
            else {
                contentValues.put("selective_synchronizationType", repo.selectiveSynchronization!!.synchronizationType.ordinal)

                if(repo.selectiveSynchronization!!.level == null) contentValues.putNull("selective_level")
                else contentValues.put("selective_level", repo.selectiveSynchronization!!.level!!.ordinal)

                if(repo.selectiveSynchronization!!.bookUuid == null) contentValues.putNull("selective_bookUuid")
                else contentValues.put("selective_bookUuid", repo.selectiveSynchronization!!.bookUuid!!)
            }

            if(repo.credentials == null) {
                contentValues.putNull("credentials_username")
                contentValues.putNull("credentials_password")
            }
            else {
                contentValues.put("credentials_username", repo.credentials!!.username)
                contentValues.put("credentials_password", repo.credentials!!.password)
            }
            return contentValues
        }

        @Synchronized
        fun reloadInstance() {
            if(instance != null) {
                instance = null
            }
        }
    }
}