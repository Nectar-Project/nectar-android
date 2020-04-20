package com.realitix.nectar.repository


abstract class NameGenericRepository<ERaw, E>(updater: EntityUpdaterInterface<ERaw>):
    GenericGetUuidRepository<ERaw, E>(updater) {
    abstract fun getNameUuid(uuid: String): String
}