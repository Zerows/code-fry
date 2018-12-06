package com.code.fry

import org.jetbrains.exposed.sql.Database

object DbSettings {
    val db by lazy {
        Database.connect("jdbc:postgresql://localhost:5433/codefry", driver = "org.postgresql.Driver",
                user = "postgres", password = "example")
    }
}
