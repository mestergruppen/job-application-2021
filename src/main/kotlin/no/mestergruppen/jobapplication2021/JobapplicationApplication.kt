package no.mestergruppen.jobapplication2021

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class JobapplicationApplication

fun main(args: Array<String>) {
	runApplication<JobapplicationApplication>(*args)
}
