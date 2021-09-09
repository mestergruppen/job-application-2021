package no.mestergruppen.jobapplication2021.kafka.consumer

import no.mestergruppen.jobapplication2021.kafka.KafkaConfig
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@ConditionalOnProperty(prefix = "kafka", name = ["enabled"], matchIfMissing = false)
class ConsumerConfiguration(private val kafkaConfig: KafkaConfig) {

    @Bean
    fun kafkaConsumer(): KafkaConsumer<String, String> = KafkaConsumer(configureProperties())

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