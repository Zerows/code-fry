package com.code.fry.dao

import org.jetbrains.exposed.dao.IntIdTable

object Submissions : IntIdTable() {
    val language = text("language")
    val filename = text("filename")
    val content = text("content")
    val createdAt = Results.date("created_at")
    val updatedAt = Results.date("updated_at")
}
