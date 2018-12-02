package com.code.fry

import com.code.fry.command.Job
import com.code.fry.command.Resource
import com.code.fry.languages.Language
import com.code.fry.util.RedisUtil
import com.google.gson.Gson
import com.rabbitmq.client.*
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.atomic.AtomicInteger

class MQMain {
    companion object {
        private const val RPC_QUEUE_NAME = "rpc_queue"
        private val RETRY_COUNT = AtomicInteger()

        @JvmStatic
        fun main(args: Array<String>) {
            RedisUtil.getValue("1")
            connectToQueue()

        }

        private fun connectToQueue() {
            val factory = ConnectionFactory()
            factory.host = "localhost"
            factory.port = 5672
            val connection: Connection?
            connection = factory.newConnection()
            val channel = connection!!.createChannel()
            channel.basicQos(1)
            channel.queueDeclare(RPC_QUEUE_NAME, true, false, false, null)
            println(" [x] Awaiting RPC requests")
            val consumer = object : DefaultConsumer(channel) {
                @Throws(IOException::class)
                override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties, body: ByteArray?) {


                    try {
                        val message = String(body!!, Charset.forName("UTF-8"))
                        val job = Gson().fromJson(message, Job::class.java)
                        val content = RedisUtil.getValue(job.jobId)
                        val resource = Gson().fromJson(content, Resource::class.java)
                        val result = Language.run(resource.language, resource)
                        RedisUtil.setValue(job.jobId, Gson().toJson(result))
                        println("Completed Job ${job.jobId}")
                    } catch (e: Exception) {
                        println(e)
                    } finally {

                    }
                }
            }
            try {
                channel.basicConsume(RPC_QUEUE_NAME, true, consumer)
            } catch (e: Exception) {
                println(e)
            }
        }
    }

}