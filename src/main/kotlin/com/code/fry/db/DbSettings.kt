package com.code.fry.db

import org.apache.commons.dbcp2.BasicDataSource
import org.jetbrains.exposed.sql.Database

object DbSettings {
    val DB by lazy {
        val ds = BasicDataSource()
        ds.url = "jdbc:postgresql://localhost:5433/codefry"
        ds.username = "postgres"
        ds.password = "example"
        ds.maxOpenPreparedStatements = 2
        ds.maxIdle = 2
        ds.minIdle = 1
        ds.maxTotal = 2
        Database.connect(ds)
    }
}
