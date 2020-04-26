package com.realitix.nectar.repository

import com.realitix.nectar.database.entity.UuidInterface


abstract class NameGenericRepository<ERaw, E: UuidInterface>(updater: EntityUpdaterInterface<ERaw>):
    GenericGetUuidRepository<ERaw, E>(updater) {
    abstract fun getNameUuid(uuid: String): String
}