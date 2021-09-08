package no.mestergruppen.jobapplication2021.outbox.database

import java.time.Instant
import java.util.*

data class Job(
        val id: UUID,
        val status: JobStatus,
        val executorClass: Class<*>,
        val data: String?,
        val requestId: UUID?,
        val createdAt: Instant = Instant.now(),
        val startTime: Instant? = null,
        val endTime: Instant? = null
)

enum class JobStatus {
    CREATED,
    FINISHED
}