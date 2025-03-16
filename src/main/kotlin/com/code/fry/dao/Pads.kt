package com.code.fry.dao

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.date

object Pads : IntIdTable() {
    val language = text("language")
    val filename = text("filename")
    val content = text("content")
    val createdAt = date("created_at")
    val updatedAt = date("updated_at")
}
