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

@Component
@ConditionalOnProperty(prefix = "kafka", name = ["enabled"], matchIfMissing = false)
class EventConsumer(private val kafkaConsumer: KafkaConsumer<String, String>) {

    private val log = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun consume(event: ContextRefreshedEvent) {
        kafkaConsumer.subscribe(listOf(EVENT_TOPIC))

        while (true) {
            val records = kafkaConsumer.poll(Duration.of(100, ChronoUnit.MILLIS))

            records.forEach {
                log.info("${it.key()}: ${it.value()}")
            }
        }
    }

    @PreDestroy
    fun close() {
        kafkaConsumer.close()
    }
}

const val EVENT_TOPIC = "events"