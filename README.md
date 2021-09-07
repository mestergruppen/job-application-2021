# Mestergruppen Job Application

This is a simple Spring Boot application with Spring Web included. 
A controller, an `Event` type, a very simple job queue and a Spring Scheduler is
already set up. For the actual task, this is just a starting point, and you will 
have to write and change all the code necessary to fulfill the task requirements 
below. Your implementation will be evaluated and considered as a replacement for 
the traditional job application.

We expect that this task should take between 16-32 hours (2-4 work days) to complete. 
All necessary dependencies should already be added, but feel free to use any 
tools and libraries you are most comfortable with.

Even though the initial application is written in Kotlin, we will also accept 
submissions written in Java. Just note that you will have to set up everything from 
scratch as we do not provide a Java version of this application.

## Evaluation criteria

The categories you will be evaluated in:
* Clean code
* Problem solving ability
* Performance
* State management
* Modern syntax

## Requirements

* Java 11+
* Docker/Docker Compose

## Build

```./gradlew clean build```

## Development

To start all dependencies with docker-compose, run this in the project root folder:
```docker-compose up -d```

This starts up Apache Zookeeper, Apache Kafka, Confluent Schema Registry and a Postgres database.

## Run

```./gradlew bootRun```

## Task

### Description

The purpose of this application is to provide a simple JSON over HTTP interface for clients to send us data. This data will then be delivered into our central datahub (in this case, Kafka). 
However, there are several problems arising once we decide to make it production ready in the cloud. 

1. If the application instance goes down, for any reason, all other applications depending on this one will fail.
2. There is no way of knowing if jobs added to the job queue were executed or not in case of application failure as application state is fully in memory.
3. Scaling introduces more problems we have to solve when we still want to retain the resiliency of the application.

We want this application to have high-availability, be resilient to failure, manage data reliably, and be scalable to accommodate the high pressures of a production environment. 
For this to work, you need to pinpoint where the problems are and implement a solution that satisfies our production requirements.

* Multiple instances of the application should be able to run simultaneously without impacting other requirements.
* When a job is added to the queue, the client should receive a 202 Accepted status. This application will have to guarantee that the job(s) will eventually be completed.
* No job should ever be executed more than once (even with a multi-instance configuration).
* We should be able to deploy new versions of the application at any point in time without worrying about jobs not being successfully completed.
* Even with longer job execution times combined with high pressure, the server should strive to be highly available to clients with minimal response times. In other words, the server's thread pool should not suffer from long job execution times.
* All jobs have to publish its data to a Kafka topic (`events` is the one used in the provided `KafkaConsumer`). It is not necessary to use a schema if you don't want to.

### Submission
Fork this repository and implement your solution. Provide a link to your fork with your application.

### Tips & tricks

Uncomment the flyway and datasource config in `application.yml` to start working with the database (expects the docker containers to be running).
All DDL (table creation etc) can be written in the file `V20210827.1500__Initial_script.sql`. Flyway will then take care of schema migration on application startup. 
If you don't want to destroy and recreate your database after changing the schema, you will have to write changes in new files with a newer date/time then the initial one.

For the Kafka part, you can use the serialization format of your choice. Keep it simple as this is not the most important part of your task.
There is already a consumer configured and implemented to output the data for each event published to the `events` topic, so you should be able to confirm that everything is working 
by looking at the server log. All you have to do to enable this is to uncomment the `enabled`-config line in `application.yml` (expects the docker containers to be running).

Just autowire in the Kafka producer to use it: `private val kafkaProducer: KafkaProducer<String, String>`.