package com.code.fry.loggers

import com.code.fry.MQMain
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Logger {
    val Logger: Logger by lazy {
        LoggerFactory.getLogger(MQMain::class.java)
    }
}