package com.realitix.nectar.repository


interface NameRepositoryInterface<U> {
    fun insert(i: U)
    fun getRaw(uuid: String): U?
    fun getNameUuid(uuid: String): String
}