package com.code.fry.queue

import com.code.fry.command.Job
import com.code.fry.command.Resource
import com.code.fry.dao.*
import com.code.fry.languages.Runners
import com.code.fry.loggers.ExposedLogger
import com.code.fry.loggers.Logger
import com.code.fry.util.FileName
import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.nio.charset.Charset

class QueueConsumer(myChannel: Channel) : DefaultConsumer(myChannel) {

    override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties, body: ByteArray?) {
        val message = String(body!!, Charset.forName("UTF-8"))
        if (message.isEmpty()) {
            Logger.Logger.info("Message From Queue is empty ${message}")
        }
        try {
            val job = Gson().fromJson(message, Job::class.java)
            Logger.Logger.info("Started Job ${job.id}")
            Logger.Logger.info("Message: $message")
            transaction {
                addLogger(ExposedLogger)
                //Find submission and Results
                val submission: Pad = Pad.find { Pads.id eq job.id.toInt() }.first()
                val result: Result = Result.find { Results.id eq job.resultId }.first()

                //Update result status to inprogress
                result.status = ResultStatus.InProgress
                commit()
                Logger.Logger.info("Results status Updated: In Progress")

                //Start the program to run
                val fileName = FileName.valueOf(submission.language)
                val resource = Resource(job.id, submission.language,
                        fileName.customName, submission.content, null)
                val programOutput = Runners.run(resource.language, resource)

                //Update results based on output
                if (programOutput != null) {
                    if (programOutput.output != null)
                        result.output = programOutput.output
                    if (programOutput.error != null)
                        result.error = programOutput.error
                    result.updatedAt = DateTime.now()
                    result.status = ResultStatus.Completed
                }
                result.status = ResultStatus.Completed
            }
            Logger.Logger.info("Completed Job ${job.id}")
        } catch (e: Exception) {
            Logger.Logger.error("", e)
            val job = Gson().fromJson(message, Job::class.java)
            transaction {
                try {
                    val result: Result = Result.find { Results.id eq job.resultId }.first()
                    result.status = ResultStatus.Cancelled
                }catch(e: Exception){
                    Logger.Logger.error("", e)
                }
            }
            Logger.Logger.info("Results status Update: In Cancelled")
        } finally {
            val deliveryTag = envelope?.deliveryTag
            if (deliveryTag != null) {
                channel.basicAck(deliveryTag, false)
            }
            Logger.Logger.info("Acknowledge")
        }
    }
}