# Mestergruppen Job Application

This is a simple Spring Boot application  

## Requirements

* Java 11
* Docker/Docker Compose

## Build

```./gradlew clean build```

## Development

To start all dependencies with docker-compose, run this in the project root folder:
```docker-compose up -d```

This starts up Apache Zookeeper, Apache Kafka, Confluent Schema Registry and a Postgres database.

## Run

```./gradlew bootRun```