package com.code.fry.dao

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Pad(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Pad>(Pads)

    var language by Pads.language
    var filename by Pads.filename
    var content by Pads.content
    var createdAt by Pads.createdAt
    var updatedAt by Pads.updatedAt
}