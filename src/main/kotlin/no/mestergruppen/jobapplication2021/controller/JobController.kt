package no.mestergruppen.jobapplication2021.controller

import com.fasterxml.jackson.databind.ObjectMapper
import no.mestergruppen.jobapplication2021.outbox.JobQueue
import no.mestergruppen.jobapplication2021.type.Event
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@Component
class JobController(private val jobQueue: JobQueue, private val objectMapper: ObjectMapper) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping("/jobs", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun postEvents(@RequestHeader("X-Request-ID") requestId: String, @RequestBody events: List<Event>) {
        log.info("POST /events, X-Request-ID: $requestId")

        val results = events.map {
            jobQueue.addJob(it.eventId, objectMapper.writeValueAsString(it.eventPayload))
        }

        log.info("Jobs added: ${results.filter { it }.size}")
    }
}