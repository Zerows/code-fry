package com.code.fry.queue

import com.code.fry.Constants
import com.code.fry.loggers.Logger
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

class Queue {
    companion object {
        private val RPC_QUEUE_NAME = Constants.QUEUE_NAME
        fun start() {
            val factory = ConnectionFactory()
            factory.host = Constants.QUEUE_HOST
            factory.port = Constants.QUEUE_PORT
            val connection: Connection?
            connection = factory.newConnection()
            val channel = connection!!.createChannel()
            channel.basicQos(2, true)
            channel.queueDeclare(RPC_QUEUE_NAME, true, false, false, null)
            val consumer = QueueConsumer(channel)
            try {
                Logger.Logger.info("Listening on Queue $RPC_QUEUE_NAME")
                channel.basicConsume(RPC_QUEUE_NAME, false, consumer)
            } catch (e: Exception) {
                Logger.Logger.error("", e)
            }
        }
    }
}