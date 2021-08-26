package no.mestergruppen.jobapplication2021.outbox

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler(private val jobQueue: JobQueue) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(fixedDelay = 100L, initialDelay = 1000L)
    fun executeJob() {
        val (id, data) = jobQueue.getJob()
        log.info("Job with $id is executed.")
    }
}