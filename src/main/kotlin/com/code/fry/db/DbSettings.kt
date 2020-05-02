package com.code.fry.db

import com.code.fry.Constants
import org.apache.commons.dbcp2.BasicDataSource
import org.jetbrains.exposed.sql.Database

object DbSettings {
    val DB by lazy {
        val ds = BasicDataSource()
        ds.url = Constants.DB_URL
        ds.username = Constants.DB_USERNAME
        ds.password = Constants.DB_PASSWORD
        ds.maxOpenPreparedStatements = 2
        ds.maxIdle = 2
        ds.minIdle = 1
        ds.maxTotal = 2
        Database.connect(ds)
    }
}
