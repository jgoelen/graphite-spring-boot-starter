# Spring Boot starter for Graphite

Adds the Codahale [metrics-graphite](https://dropwizard.github.io/metrics/3.1.0/manual/graphite/#manual-graphite)
module to your Spring Boot application. It allows your application to constantly stream metric
values to a [Graphite](http://graphite.wikidot.com/) server.

## Usage

Make sure the [Spring Boot Actuator](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready)
dependency is declared in the Maven build file:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Add the following

```xml
<dependency>
    <groupId>com.github.jgoelen</groupId>
    <artifactId>graphite-spring-boot-starter</artifactId>
    <version>1.3.x</version>
</dependency>
```

## Configuration

Feature flag to enable/disable the reporter.

```
graphite.enabled=true
```

Host and UDP port of the Graphite server.

```
graphite.host=graphite.server.io
graphite.port=2300
```

The reporting interval in seconds.

```
graphite.report-interval=5
```

The prefix for all metric names that get published to Graphite.

```
graphite.prefix=production.applications.user_service.host
```

## Supported versions

For the moment Spring Boot version 1.3.x is supported.