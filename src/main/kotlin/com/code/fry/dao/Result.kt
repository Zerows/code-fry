package com.code.fry.dao

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Result(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Result>(Results)

    var submission by Pad referencedOn Results.pad
    var output by Results.output
    var error by Results.error
    var createdAt by Results.createdAt
    var updatedAt by Results.updatedAt
}
