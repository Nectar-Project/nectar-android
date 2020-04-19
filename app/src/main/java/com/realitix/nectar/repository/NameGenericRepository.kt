package com.realitix.nectar.repository


abstract class NameGenericRepository<ERaw, E>: GenericCrudRepository<ERaw, E>() {
    abstract fun getNameUuid(uuid: String): String
}