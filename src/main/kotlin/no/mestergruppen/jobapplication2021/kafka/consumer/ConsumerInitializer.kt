package no.mestergruppen.jobapplication2021.kafka.consumer

import no.mestergruppen.jobapplication2021.kafka.KafkaConfig
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.annotation.PreDestroy

@Configuration
@ConditionalOnProperty(prefix = "kafka", name = ["enabled"], matchIfMissing = false)
class ConsumerInitializer(private val kafkaConfig: KafkaConfig) : ApplicationListener<ContextRefreshedEvent> {

    private val log = LoggerFactory.getLogger(javaClass)

    private val kafkaConsumer: KafkaConsumer<String, String> = KafkaConsumer(configureProperties())
    private val eventConsumer = EventConsumer(kafkaConsumer)
    private val executorService = Executors.newFixedThreadPool(1)

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        executorService.submit(eventConsumer)
    }

    @PreDestroy
    fun onDestroy() {
        log.info("Shutting down EventConsumer...")

        eventConsumer.shutdown()
        kafkaConsumer.close()

        executorService.awaitTermination(5, TimeUnit.SECONDS)
    }

    private fun configureProperties() =
            Properties().also {
                it[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaConfig.bootstrapServer
                it[ConsumerConfig.GROUP_ID_CONFIG] = "my-consumer-group"
                it[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
                it[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = true
                it[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringDeserializer"
                it[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringDeserializer"

                kafkaConfig.schemaRegistry?.let { schemaRegistryConfig ->
                    it["schema.registry.url"] = schemaRegistryConfig.url
                }

                it[CommonClientConfigs.SECURITY_PROTOCOL_CONFIG] = kafkaConfig.securityProtocol
            }
}