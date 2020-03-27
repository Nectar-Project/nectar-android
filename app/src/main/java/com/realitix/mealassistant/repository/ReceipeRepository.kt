package com.realitix.mealassistant.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.realitix.mealassistant.database.MealDatabase
import com.realitix.mealassistant.database.entity.*

class ReceipeRepository(val context: Context) {
    fun getReceipes(): LiveData<List<Receipe>> {
        return MealDatabase.getInstance(context).receipeDao().list()
    }

    fun search(name: String): LiveData<List<Receipe>> {
        return MealDatabase.getInstance(context).receipeDao().search(name)
    }

    fun getReceipeLive(receipeUuid: String): LiveData<Receipe> {
        return MealDatabase.getInstance(context).receipeDao().getLive(receipeUuid)
    }

    fun getReceipe(uuid: String): Receipe? {
        return MealDatabase.getInstance(context).receipeDao().get(uuid)
    }

    fun getReceipeFull(receipeUuid: String): LiveData<Receipe> {
        return MealDatabase.getInstance(context).receipeDao().getFull(receipeUuid)
    }

    fun getReceipeStepFull(stepUuid: String): LiveData<ReceipeStep> {
        return MealDatabase.getInstance(context).receipeStepDao().getFull(stepUuid)
    }

    suspend fun update(receipe: Receipe) {
        MealDatabase.getInstance(context).receipeDao().update(receipe)
    }

    suspend fun updateReceipeName(receipeName: ReceipeName) {
        MealDatabase.getInstance(context).receipeDao().updateName(receipeName)
    }

    suspend fun hasReceipe(receipeUuid: String): Boolean {
        if(MealDatabase.getInstance(context).receipeDao().has(receipeUuid) != null)
            return true
        return false
    }

    suspend fun hasReceipeStep(stepUuid: String): Boolean {
        if(MealDatabase.getInstance(context).receipeStepDao().has(stepUuid) != null)
            return true
        return false
    }

    suspend fun createReceipe(receipe: ReceipeRaw) {
        MealDatabase.getInstance(context).receipeDao().insertSuspended(receipe)
    }

    fun insertReceipe(receipe: ReceipeRaw) {
        MealDatabase.getInstance(context).receipeDao().insert(receipe)
    }

    fun insertReceipeName(name: ReceipeNameRaw) {
        MealDatabase.getInstance(context).receipeNameDao().insert(name)
    }

    fun insertReceipeTag(tag: ReceipeTagRaw) {
        MealDatabase.getInstance(context).receipeTagDao().insert(tag)
    }

    fun insertReceipeUtensil(utensil: ReceipeUtensilRaw) {
        MealDatabase.getInstance(context).receipeUtensilDao().insert(utensil)
    }

    fun insertReceipeStep(step: ReceipeStepRaw) {
        return MealDatabase.getInstance(context).receipeStepDao().insert(step)
    }

    fun insertReceipeStepAliment(receipeStepAliment: ReceipeStepAlimentRaw) {
        return MealDatabase.getInstance(context).receipeStepAlimentDao().insert(receipeStepAliment)
    }

    fun insertReceipeStepReceipe(r: ReceipeStepReceipeRaw) {
        return MealDatabase.getInstance(context).receipeStepReceipeDao().insert(r)
    }

    suspend fun createReceipeName(receipeName: ReceipeNameRaw) {
        MealDatabase.getInstance(context).receipeDao().insertName(receipeName)
    }

    suspend fun createReceipeStep(receipeStep: ReceipeStep) {
        return MealDatabase.getInstance(context).receipeStepDao().insertSuspended(receipeStep)
    }

    suspend fun createReceipeStepAliment(receipeStepAliment: ReceipeStepAliment) {
        return MealDatabase.getInstance(context).receipeStepAlimentDao().insertSuspended(receipeStepAliment)
    }

    suspend fun createReceipeStepReceipe(receipeStepReceipe: ReceipeStepReceipe) {
        return MealDatabase.getInstance(context).receipeStepReceipeDao().insert(receipeStepReceipe)
    }

    companion object {
        private var instance: ReceipeRepository? = null
        @Synchronized
        fun getInstance(context: Context): ReceipeRepository {
            if (instance == null) {
                instance = ReceipeRepository(context)
            }
            return instance!!
        }
    }
}