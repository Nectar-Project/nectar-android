package com.realitix.nectar.repository


interface NameRepositoryInterface<U, V> {
    fun insert(i: U)
    fun insertName(i: V)
    fun getRaw(uuid: String): U?
    fun getNamesMap(uuid: String): Map<String, String>
}