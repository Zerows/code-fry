package com.code.fry

class Constants{
    companion object {
        const val TIMEOUT: Long = 10
        val DB_URL = System.getenv("DB_URL") as String
        val DB_USERNAME = System.getenv("DB_USERNAME") as String
        val DB_PASSWORD = System.getenv("DB_PASSWORD") as String
        val QUEUE_HOST = System.getenv("QUEUE_HOST") as String
        val QUEUE_NAME = System.getenv("QUEUE_NAME") as String
        val QUEUE_PORT = System.getenv("QUEUE_PORT").toLong()
    }
}