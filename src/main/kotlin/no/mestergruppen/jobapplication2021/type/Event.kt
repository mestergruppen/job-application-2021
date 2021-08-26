package no.mestergruppen.jobapplication2021.type

import com.fasterxml.jackson.databind.JsonNode
import java.time.OffsetDateTime

data class Event(
        // Timestamp for when this event actually happened in the source system, example = "2020-02-17T17:22:45.00000Z
        val timestamp: OffsetDateTime,

        // A GUID defined by publisher for the event published to DataHub, example = "4d29cd85-79e9-4b99-a5cd-0c582c50b642
        val eventId: String,
        // Name of the event, example = "product-updated"
        val eventName: String,

        // The actual data payload. This can be any type of JSON structure, but needs to be compatible with the event schema.
        val eventPayload: JsonNode? = null,

        // GUID tracking the request
        val requestId: String? = null
)
