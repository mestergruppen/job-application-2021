package no.mestergruppen.jobapplication2021.kafka.producer

import no.mestergruppen.jobapplication2021.kafka.KafkaConfig
import org.apache.kafka.clients.producer.KafkaProducer
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
                // todo: set producer properties
            }
}