package com.code.fry

import com.code.fry.DbSettings.db
import com.code.fry.command.Job
import com.code.fry.command.Resource
import com.code.fry.dao.Result
import com.code.fry.dao.Submission
import com.code.fry.dao.Submissions
import com.code.fry.languages.Language
import com.google.gson.Gson
import com.rabbitmq.client.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.atomic.AtomicInteger

class MQMain {
    companion object {
        private const val RPC_QUEUE_NAME = "rpc_queue"
        private val RETRY_COUNT = AtomicInteger()

        @JvmStatic
        fun main(args: Array<String>) {
            connectToQueue()
            db
            transaction {
                SchemaUtils.create(Submissions)

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
            println(" [x] Awaiting RPC requests")
            val consumer = object : DefaultConsumer(channel) {
                @Throws(IOException::class)
                override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties, body: ByteArray?) {

                    try {
                        val message = String(body!!, Charset.forName("UTF-8"))
                        val job = Gson().fromJson(message, Job::class.java)
                        transaction {
                            val submission = Submission.find { Submissions.id eq job.jobId.toInt() }.first()
                            val resource = Resource(job.jobId, submission.language,
                                    submission.filename, submission.content, null)
                            val programOutput = Language.run(resource.language, resource)
                            if (programOutput != null) {
                                val result = Result.new {
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

