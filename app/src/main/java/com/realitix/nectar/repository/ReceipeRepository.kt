package com.realitix.nectar.repository

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import com.realitix.nectar.database.NectarDatabase
import com.realitix.nectar.database.entity.*
import com.realitix.nectar.util.NectarUtil

class ReceipeRepository(val context: Context) {
    fun getReceipes(): LiveData<List<Receipe>> {
        return NectarDatabase.getInstance(context).receipeDao().list()
    }

    fun search(name: String): LiveData<List<Receipe>> {
        return NectarDatabase.getInstance(context).receipeDao().search(NectarUtil.searchMaker(name))
    }

    fun getReceipeLive(receipeUuid: String): LiveData<Receipe> {
        return NectarDatabase.getInstance(context).receipeDao().getLive(receipeUuid)
    }

    fun getReceipe(uuid: String): Receipe? {
        return NectarDatabase.getInstance(context).receipeDao().get(uuid)
    }

    fun getReceipeFull(receipeUuid: String): LiveData<Receipe> {
        return NectarDatabase.getInstance(context).receipeDao().getFull(receipeUuid)
    }

    fun getReceipeStepFull(stepUuid: String): LiveData<ReceipeStep> {
        return NectarDatabase.getInstance(context).receipeStepDao().getFull(stepUuid)
    }

    suspend fun update(receipe: Receipe) {
        NectarDatabase.getInstance(context).receipeDao().update(receipe)
    }

    suspend fun hasReceipe(receipeUuid: String): Boolean {
        if(NectarDatabase.getInstance(context).receipeDao().has(receipeUuid) != null)
            return true
        return false
    }

    suspend fun hasReceipeStep(stepUuid: String): Boolean {
        if(NectarDatabase.getInstance(context).receipeStepDao().has(stepUuid) != null)
            return true
        return false
    }

    suspend fun createReceipeSuspend(receipe: ReceipeRaw) {
        NectarDatabase.getInstance(context).receipeDao().insertSuspended(receipe)
    }

    fun insertReceipe(receipe: ReceipeRaw) {
        NectarDatabase.getInstance(context).receipeDao().insert(receipe)
    }

    fun insertReceipeTag(tag: ReceipeTagRaw) {
        NectarDatabase.getInstance(context).receipeTagDao().insert(tag)
    }

    fun insertReceipeUtensil(utensil: ReceipeUtensilRaw) {
        NectarDatabase.getInstance(context).receipeUtensilDao().insert(utensil)
    }

    fun insertReceipeStep(step: ReceipeStepRaw) {
        return NectarDatabase.getInstance(context).receipeStepDao().insert(step)
    }

    fun insertReceipeStepAliment(receipeStepAliment: ReceipeStepAlimentRaw) {
        return NectarDatabase.getInstance(context).receipeStepAlimentDao().insert(receipeStepAliment)
    }

    fun insertReceipeStepReceipe(r: ReceipeStepReceipeRaw) {
        return NectarDatabase.getInstance(context).receipeStepReceipeDao().insert(r)
    }

    suspend fun insertReceipeStepSuspend(receipeStep: ReceipeStepRaw) {
        return NectarDatabase.getInstance(context).receipeStepDao().insertSuspended(receipeStep)
    }

    suspend fun createReceipeStepAliment(receipeStepAliment: ReceipeStepAlimentRaw): Boolean {
        return try {
            NectarDatabase.getInstance(context).receipeStepAlimentDao().insertSuspended(receipeStepAliment)
            true
        } catch (e: SQLiteConstraintException) {
            // If aliment is already in this step, UNIQUE constraint error, so we return false
            Log.e("Nectar", "Aliment ${receipeStepAliment.alimentUuid} is already in step ${receipeStepAliment.stepUuid}")
            false
        }
    }

    suspend fun createReceipeStepReceipe(receipeStepReceipe: ReceipeStepReceipeRaw) {
        return NectarDatabase.getInstance(context).receipeStepReceipeDao().insert(receipeStepReceipe)
    }
}