package no.mestergruppen.jobapplication2021.kafka.consumer

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.temporal.ChronoUnit
import javax.annotation.PreDestroy

class EventConsumer(private val kafkaConsumer: KafkaConsumer<String, String>) : Runnable {

    private val log = LoggerFactory.getLogger(javaClass)

    @Volatile
    private var running: Boolean = true

    override fun run() {
        kafkaConsumer.subscribe(listOf(EVENT_TOPIC))

        while (running) {
            val records = kafkaConsumer.poll(Duration.of(100, ChronoUnit.MILLIS))

            records.forEach {
                log.info("${it.key()}: ${it.value()}")
            }
        }
    }

    fun shutdown() {
        running = false
    }
}

const val EVENT_TOPIC = "events"