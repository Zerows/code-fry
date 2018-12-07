package com.code.fry.dao

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass

class Submission(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Submission>(Submissions)

    var language by Submissions.language
    var filename by Submissions.filename
    var content by Submissions.content
    var createdAt by Submissions.createdAt
    var updatedAt by Submissions.updatedAt
}