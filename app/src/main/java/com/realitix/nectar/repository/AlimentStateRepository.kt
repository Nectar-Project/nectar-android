package com.realitix.nectar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.EntityType
import com.realitix.nectar.util.NectarUtil

class AlimentStateRepository(val context: Context, updater: EntityUpdaterInterface<AlimentStateRaw> = Updater(context)):
    GenericGetJoinRepository<AlimentStateRaw, AlimentState>(updater) {
    override fun getDao() = NectarDatabase.getInstance(context).alimentStateDao()

    fun getUuid(uuid: String): AlimentState? = getDao().getUuid(uuid)
    fun getUuidLive(uuid: String): LiveData<AlimentState> = getDao().getUuidLive(uuid)
    suspend fun getUuidSuspend(uuid: String): AlimentState? = getDao().getUuidSuspend(uuid)

    suspend fun listByAlimentSuspend(uuid: String): List<AlimentState> {
        return getDao().listByAlimentSuspend(uuid)
    }

    class Updater(context: Context): GenericEntityUpdater<AlimentStateRaw>(context) {
        override fun getUuidType(entity: AlimentStateRaw): Pair<String, EntityType> = Pair(entity.alimentUuid, EntityType.ALIMENT)
    }
}