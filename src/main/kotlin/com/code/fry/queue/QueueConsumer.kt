package com.code.fry.queue

import com.code.fry.Logger
import com.code.fry.command.Job
import com.code.fry.command.Resource
import com.code.fry.dao.Pad
import com.code.fry.dao.Pads
import com.code.fry.dao.Result
import com.code.fry.dao.Results
import com.code.fry.languages.Language
import com.code.fry.util.FileName
import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.nio.charset.Charset

class QueueConsumer(myChannel: Channel) : DefaultConsumer(myChannel) {

    override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties, body: ByteArray?) {
        try {
            val message = String(body!!, Charset.forName("UTF-8"))
            val job = Gson().fromJson(message, Job::class.java)
            Logger.Logger.info("Started Job ${job.id}")
            Logger.Logger.info("Message: $message")
            transaction {
                val submission = Pad.find { Pads.id eq job.id.toInt() }.first()
                val fileName = FileName.valueOf(submission.language)
                val resource = Resource(job.id, submission.language,
                        fileName.customName, submission.content, null)
                val programOutput = Language.run(resource.language, resource)
                if (programOutput != null) {
                    val result = Result.find { Results.id eq job.resultId}.first()
                    if (programOutput.output != null)
                        result.output = programOutput.output
                    if (programOutput.error != null)
                        result.error = programOutput.error
                    result.updatedAt = DateTime.now()
                }
            }
            Logger.Logger.info("Completed Job ${job.id}")
        } catch (e: Exception) {
            Logger.Logger.error("", e)
        } finally {
            val deliveryTag = envelope?.deliveryTag
            if (deliveryTag != null) {
                channel.basicAck(deliveryTag, false)
            }
            Logger.Logger.info("Acknowleged")
        }
    }
}