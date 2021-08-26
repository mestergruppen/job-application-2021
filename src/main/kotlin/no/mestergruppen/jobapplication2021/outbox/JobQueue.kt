package no.mestergruppen.jobapplication2021.outbox

import org.springframework.stereotype.Component
import java.util.*

@Component
class JobQueue {

    private val jobs: Queue<Pair<String, String>> = LinkedList()

    fun addJob(id: String, data: String):Boolean = jobs.add(Pair(id, data))

    fun getJob(): Pair<String, String> = jobs.remove()
}