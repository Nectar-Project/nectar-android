package com.realitix.nectar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.realitix.nectar.database.entity.Aliment
import com.realitix.nectar.repository.AlimentRepository

class AlimentsViewModel(
    rAliment: AlimentRepository
): ViewModel() {
    val aliments: LiveData<List<Aliment>> = rAliment.listLive()
}