package no.mestergruppen.jobapplication2021.outbox.database

import org.jetbrains.exposed.sql.Database
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import javax.sql.DataSource

@Configuration
@ConditionalOnBean(DataSourceProperties::class)
class DatabaseFactory(private val dataSource: DataSource) {

    private val log = LoggerFactory.getLogger(javaClass)

    @EventListener
    fun exposedDatabase(event: ContextRefreshedEvent) {
        log.info("Running Database.connect(DataSource)")

        Database.connect(dataSource)
    }
}