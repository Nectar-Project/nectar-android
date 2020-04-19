package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.DatabaseUpdateRaw
import com.realitix.nectar.database.entity.UuidEntity
import com.realitix.nectar.database.entity.UuidEntityInterface
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

abstract class TrackingGenericRepository<ERaw: UuidEntityInterface, E>(val context: Context, val trackingEnabled: Boolean = true):
    GenericGetUuidRepository<ERaw, E>() {
    abstract fun getEntityType(): EntityType

    private fun track(uuid: String) {
        if(trackingEnabled) {
            NectarDatabase.getInstance(context).databaseUpdateDao().insert(
                DatabaseUpdateRaw(uuid, getEntityType(), NectarUtil.timestamp())
            )
        }
    }

    override fun insert(i: ERaw) {
        super.insert(i)
        track(i.getEntityUuid())
    }

    override suspend fun insertSuspend(i: ERaw) {
        super.insert(i)
        track(i.getEntityUuid())
    }

    override fun update(i: ERaw) {
        super.insert(i)
        track(i.getEntityUuid())
    }

    override suspend fun updateSuspend(i: ERaw) {
        super.insert(i)
        track(i.getEntityUuid())
    }

    override fun delete(i: ERaw) {
        super.insert(i)
        track(i.getEntityUuid())
    }

    override suspend fun deleteSuspend(i: ERaw) {
        super.insert(i)
        track(i.getEntityUuid())
    }
}