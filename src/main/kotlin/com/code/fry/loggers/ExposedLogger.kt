package com.code.fry.loggers

import org.jetbrains.exposed.sql.SqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs

object ExposedLogger: SqlLogger{
    override fun log(context: StatementContext, transaction: Transaction) {
        Logger.Logger.info("SQL: ${context.expandArgs(transaction)}")
    }

}