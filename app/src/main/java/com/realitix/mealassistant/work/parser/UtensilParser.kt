package com.realitix.mealassistant.work.parser

import android.content.Context
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.NameRepositoryInterface
import com.realitix.mealassistant.repository.TagRepository
import com.realitix.mealassistant.repository.UtensilRepository

class UtensilParser: NameBaseParser<UtensilRaw, UtensilNameRaw>() {
    override fun getRepository(context: Context): NameRepositoryInterface<UtensilRaw, UtensilNameRaw> = UtensilRepository.getInstance(context)
    override fun getNew(uuid: String): UtensilRaw = UtensilRaw(uuid)
    override fun getNewName(uuid: String, lang: String, name: String): UtensilNameRaw = UtensilNameRaw(uuid, lang, name)
    override fun getSourceFolder(): String = "utensils"
}