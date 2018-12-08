package com.code.fry.queue

import com.code.fry.Logger
import com.code.fry.command.Job
import com.code.fry.command.Resource
import com.code.fry.dao.Pad
import com.code.fry.dao.Pads
import com.code.fry.dao.Result
import com.code.fry.languages.Language
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
            Logger.Logger.info("Started Job ${job.jobId}")
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
            Logger.Logger.info("Completed Job ${job.jobId}")
        } catch (e: Exception) {
            Logger.Logger.error("", e)
        } finally {
            val deliveryTag = envelope?.deliveryTag
            if (deliveryTag != null) {
                channel.basicAck(deliveryTag, false)
            }
        }
    }
}