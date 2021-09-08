package no.mestergruppen.jobapplication2021.outbox.database

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.`java-time`.timestamp
import java.sql.ResultSet
import java.time.Instant
import java.util.*

object Table : UUIDTable("job") {

    val status = enumeration("status", JobStatus::class).default(JobStatus.CREATED)
    val executorClass = text("executor_class")
    val data = text("context").nullable()
    val requestId = uuid("request_id").nullable()
    val createdAt = timestamp("created_at").clientDefault { Instant.now() }
    val startTime = timestamp("start_time").nullable()
    val endTime = timestamp("end_time").nullable()

    fun toJob(row: ResultRow): Job =
            Job(
                    id = row[id].value,
                    status = row[status],
                    executorClass = Class.forName(row[executorClass]),
                    data = row[data],
                    requestId = row[requestId],
                    createdAt = row[createdAt],
                    startTime = row[startTime],
                    endTime = row[endTime]
            )

    fun toJob(resultSet: ResultSet): Job =
            Job(
                    id = UUID.fromString(resultSet.getString(id.name)),
                    status = JobStatus.values()[resultSet.getInt(status.name)],
                    executorClass = Class.forName(resultSet.getString(executorClass.name)),
                    data = resultSet.getString(data.name),
                    requestId = UUID.fromString(resultSet.getString(requestId.name)),
                    createdAt = resultSet.getTimestamp(createdAt.name).toInstant(),
                    startTime = resultSet.getTimestamp(startTime.name)?.toInstant(),
                    endTime = resultSet.getTimestamp(endTime.name)?.toInstant()
            )
}