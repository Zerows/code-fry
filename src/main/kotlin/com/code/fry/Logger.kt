package com.code.fry

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Logger {
    val Logger: Logger by lazy {
        LoggerFactory.getLogger(MQMain::class.java)
    }
}