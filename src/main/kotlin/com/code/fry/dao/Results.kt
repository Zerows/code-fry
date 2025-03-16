package com.code.fry.dao

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.date

object Results : IntIdTable() {
    val pad = reference("pad_id", Pads)
    val output = text("output").nullable()
    val error = text("error").nullable()
    val status = enumeration("status", ResultStatus::class) // will create integer column
    val createdAt = date("created_at")
    val updatedAt = date("updated_at")
}
