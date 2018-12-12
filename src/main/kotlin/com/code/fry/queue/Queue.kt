package com.code.fry.queue

import com.code.fry.loggers.Logger
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

class Queue {
    companion object {
        private const val RPC_QUEUE_NAME = "rpc_queue"
        fun start() {
            val factory = ConnectionFactory()
            factory.host = "localhost"
            factory.port = 5672
            val connection: Connection?
            connection = factory.newConnection()
            val channel = connection!!.createChannel()
            channel.basicQos(2, true)
            channel.queueDeclare(RPC_QUEUE_NAME, true, false, false, null)
            val consumer = QueueConsumer(channel)
            try {
                channel.basicConsume(RPC_QUEUE_NAME, false, consumer)
            } catch (e: Exception) {
                Logger.Logger.error("", e)
            }
        }
    }
}