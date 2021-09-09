package no.mestergruppen.jobapplication2021.kafka

import org.apache.kafka.clients.CommonClientConfigs
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("kafka")
@ConditionalOnProperty(prefix = "kafka", name = ["enabled"], matchIfMissing = false)
data class KafkaConfig(
        val bootstrapServer: String,
        val securityProtocol: String = CommonClientConfigs.DEFAULT_SECURITY_PROTOCOL,
        val schemaRegistry: SchemaRegistryConfig?
)

@ConstructorBinding
@ConfigurationProperties("kafka.schema-registry")
@ConditionalOnProperty(prefix = "kafka.schema-registry", name = ["enabled"], matchIfMissing = false)
data class SchemaRegistryConfig(
        val url: String
)