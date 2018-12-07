package com.code.fry

import com.code.fry.DbSettings.db
import com.code.fry.Logger.logger
import com.code.fry.command.Job
import com.code.fry.command.Resource
import com.code.fry.dao.Pad
import com.code.fry.dao.Pads
import com.code.fry.dao.Result
import com.code.fry.languages.Language
import com.google.gson.Gson
import com.rabbitmq.client.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.io.IOException
import java.nio.charset.Charset

class MQMain {
    companion object {
        private const val RPC_QUEUE_NAME = "rpc_queue"

        @JvmStatic
        fun main(args: Array<String>) {
            connectToQueue()
            db
            logger
            transaction {
                SchemaUtils.create(Pads)

            }

        }

        private fun connectToQueue() {
            val factory = ConnectionFactory()
            factory.host = "localhost"
            factory.port = 5672
            val connection: Connection?
            connection = factory.newConnection()
            val channel = connection!!.createChannel()
            channel.basicQos(2, true)
            channel.queueDeclare(RPC_QUEUE_NAME, true, false, false, null)
            logger.info(" [x] Awaiting RPC requests")
            val consumer = object : DefaultConsumer(channel) {
                @Throws(IOException::class)
                override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties, body: ByteArray?) {

                    try {
                        val message = String(body!!, Charset.forName("UTF-8"))
                        val job = Gson().fromJson(message, Job::class.java)
                        transaction {
                            val submission = Pad.find { Pads.id eq job.jobId.toInt() }.first()
                            val resource = Resource(job.jobId, submission.language,
                                    submission.filename, submission.content, null)
                            val programOutput = Language.run(resource.language, resource)
                            if (programOutput != null) {
                                Result.new {
                                    if (programOutput.output != null)
                                        output = programOutput.output
                                    if (programOutput.error != null)
                                        error = programOutput.error

                                    createdAt = DateTime.now()
                                    updatedAt = DateTime.now()
                                    this.submission = submission

                                }
                            }
                        }
                        logger.info("Completed Job ${job.jobId}")
                    } catch (e: Exception) {
                        logger.error("", e)
                    } finally {
                    }
                }
            }
            try {
                channel.basicConsume(RPC_QUEUE_NAME, true, consumer)
            } catch (e: Exception) {
                logger.error("", e)
            }
        }
    }

}

