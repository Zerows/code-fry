package com.code.fry.util

import redis.clients.jedis.Jedis


class RedisUtil {

    companion object {
        private val jedis: Jedis = Jedis("localhost", 6379)

        init {
            try {
                jedis.connect()
                jedis.auth("redis")
                jedis.select(15)
            } catch (e: Exception) {
                println(e)
                throw(e)
            }
        }

        fun setValue(id: String, message: String) {
            jedis.set(id, message)
        }

        fun getValue(id: String): String? {
            return if (jedis.exists(id)) {
                jedis.get(id).toString()
            } else {
                null
            }
        }
    }
}