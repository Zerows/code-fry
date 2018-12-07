package com.code.fry.db

import org.jetbrains.exposed.sql.Database

object DbSettings {
    val DB by lazy {
        Database.connect("jdbc:postgresql://localhost:5433/codefry", driver = "org.postgresql.Driver",
                user = "postgres", password = "example")
    }
}
