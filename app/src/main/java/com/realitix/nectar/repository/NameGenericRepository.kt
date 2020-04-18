package com.realitix.nectar.repository


abstract class NameGenericRepository<ERaw, E>: GenericRepository<ERaw, E>() {
    abstract fun getNameUuid(uuid: String): String
}