package com.realitix.nectar.repository

import android.content.Context
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.dao.BaseDao
import com.realitix.nectar.database.entity.DatabaseUpdateRaw
import com.realitix.nectar.database.entity.UuidEntity
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

abstract class TrackingGenericRepository<ERaw: UuidEntity, E>(val context: Context): GenericRepository<ERaw, E>() {
    abstract fun getEntityType(): EntityType

    private fun track(uuid: String) {
        NectarDatabase.getInstance(context).databaseUpdateDao().insert(
            DatabaseUpdateRaw(uuid, getEntityType(), NectarUtil.timestamp())
        )
    }

    override fun insert(i: ERaw) {
        super.insert(i)
        track(i.uuid)
    }

    override suspend fun insertSuspend(i: ERaw) {
        super.insert(i)
        track(i.uuid)
    }

    override fun update(i: ERaw) {
        super.insert(i)
        track(i.uuid)
    }

    override suspend fun updateSuspend(i: ERaw) {
        super.insert(i)
        track(i.uuid)
    }
}