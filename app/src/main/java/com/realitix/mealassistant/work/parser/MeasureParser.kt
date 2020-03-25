package com.realitix.mealassistant.work.parser

import android.content.Context
import com.realitix.mealassistant.database.entity.*
import com.realitix.mealassistant.repository.MeasureRepository
import com.realitix.mealassistant.repository.NameRepositoryInterface

class MeasureParser: NameBaseParser<MeasureRaw, MeasureNameRaw>() {
    override fun getRepository(context: Context): NameRepositoryInterface<MeasureRaw, MeasureNameRaw> = MeasureRepository.getInstance(context)
    override fun getNew(uuid: String): MeasureRaw = MeasureRaw(uuid)
    override fun getNewName(uuid: String, lang: String, name: String): MeasureNameRaw = MeasureNameRaw(uuid, lang, name)
    override fun getSourceFolder(): String = "measures"
}