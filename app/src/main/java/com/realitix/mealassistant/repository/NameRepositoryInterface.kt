package com.realitix.mealassistant.repository


interface NameRepositoryInterface<U, V> {
    fun insert(i: U)
    fun insertName(i: V)
    fun getRaw(uuid: String): U?
}