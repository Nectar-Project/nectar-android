package com.realitix.nectar.repository

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.DatabaseUpdate
import com.realitix.nectar.database.entity.DatabaseUpdateRaw
import com.realitix.nectar.database.entity.UuidInterface
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil
import com.realitix.nectar.util.UpdateType

class DatabaseUpdateRepository(val context: Context, updater: EntityUpdaterInterface<DatabaseUpdateRaw> = NoTrackEntityUpdater()):
    GenericCrudRepository<DatabaseUpdateRaw, DatabaseUpdate>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).databaseUpdateDao()

    // insert
    override fun insert(i: DatabaseUpdateRaw) {
        try {
            getDao().insert(i)
        }
        catch (e: SQLiteConstraintException) {
            // Do nothing
        }
    }

    override suspend fun insertSuspend(i: DatabaseUpdateRaw) {
        try {
            getDao().insertSuspend(i)
        }
        catch (e: SQLiteConstraintException) {
            // Do nothing
        }
    }

    // Return all entities of database as DatabaseUpdate
    fun rescan(): List<DatabaseUpdate> {
        val result = mutableListOf<DatabaseUpdate>()

        val entitiesMap = mapOf<EntityType, GenericGetUuidRepository<*, *>> (
            EntityType.STATE to StateRepository(context),
            EntityType.TAG to TagRepository(context),
            EntityType.MEASURE to MeasureRepository(context),
            EntityType.ALIMENT to AlimentRepository(context),
            EntityType.UTENSIL to UtensilRepository(context),
            EntityType.RECEIPE to ReceipeRepository(context),
            EntityType.MEAL to MealRepository(context),
            EntityType.IMAGE to ImageRepository(context),
            EntityType.BOOK to BookRepository(context),
            EntityType.STRING_KEY to StringKeyRepository(context)
        )

        for((k, v) in entitiesMap) {
            val entities = v.list()
            for(e in entities) {
                result.add(DatabaseUpdate(e.getEntityUuid(), k, UpdateType.UPDATE, NectarUtil.timestamp()))
            }
        }

        // Add deletes if exists
        for(ud in list()) {
            if(ud.updateType == UpdateType.DELETE) {
                result.add(DatabaseUpdate(ud.entityUuid, ud.entityType, UpdateType.DELETE, NectarUtil.timestamp()))
            }
        }

        return result
    }
}