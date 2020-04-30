package com.realitix.nectar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.database.entity.AlimentRaw
import com.realitix.nectar.database.entity.StringKeyRaw
import com.realitix.nectar.database.entity.StringKeyValue
import com.realitix.nectar.repository.AlimentRepository
import com.realitix.nectar.repository.StringKeyRepository
import com.realitix.nectar.repository.StringKeyValueRepository
import com.realitix.nectar.util.NectarUtil.Companion.generateUuid
import kotlinx.coroutines.runBlocking

class AlimentViewModel(
    rAliment: AlimentRepository,
    alimentUuid: String
): ViewModel() {
    val aliment: LiveData<Aliment> = rAliment.getLive(alimentUuid)
}