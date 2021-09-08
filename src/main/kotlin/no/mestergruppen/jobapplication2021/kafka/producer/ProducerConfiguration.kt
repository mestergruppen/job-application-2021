package no.mestergruppen.jobapplication2021.kafka.producer

import no.mestergruppen.jobapplication2021.kafka.KafkaConfig
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@ConditionalOnProperty(prefix = "kafka", name = ["enabled"], matchIfMissing = false)
class ProducerConfiguration(private val kafkaConfig: KafkaConfig) {

    @Bean
    fun kafkaProducer(): KafkaProducer<String, String> = KafkaProducer(configureProperties())

    private fun configureProperties() =
            Properties().also {
                it[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaConfig.bootstrapServers
                it[ProducerConfig.ACKS_CONFIG] = "all"
                it[ProducerConfig.RETRIES_CONFIG] = Integer.MAX_VALUE
                it[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = true
                it[ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG] = 100
                it[ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION] = 5

                kafkaConfig.schemaRegistry?.let { schemaRegistryConfig ->
                    it["schema.registry.url"] = schemaRegistryConfig.url
                }

                it[CommonClientConfigs.SECURITY_PROTOCOL_CONFIG] = kafkaConfig.securityProtocol
            }
}