# Spring Boot starter for Graphite

[![Build Status](https://travis-ci.org/jgoelen/graphite-spring-boot-starter.svg?branch=master)](https://travis-ci.org/jgoelen/graphite-spring-boot-starter)

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

Add the following dependency:

```xml
<dependency>
    <groupId>com.github.jgoelen</groupId>
    <artifactId>graphite-spring-boot-starter</artifactId>
    <version>0.1.x</version>
</dependency>
```

It can be found in the following maven repository:

```xml
<repository>
    <snapshots>
        <enabled>false</enabled>
    </snapshots>
    <id>central</id>
    <name>bintray</name>
    <url>http://jcenter.bintray.com</url>
</repository>
```

## Configuration

Feature flag to enable/disable the reporter.

```
graphite.enabled=true
```

Host and port of the Graphite server.

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

The graphite sender type ([udp](https://dropwizard.github.io/metrics/3.1.0/apidocs/com/codahale/metrics/graphite/GraphiteUDP.html),
[tcp](https://dropwizard.github.io/metrics/3.1.0/apidocs/com/codahale/metrics/graphite/Graphite.html) or
[pickled](https://dropwizard.github.io/metrics/3.1.0/apidocs/com/codahale/metrics/graphite/PickledGraphite.html)).

```
graphite.sender-type=udp
```

## Supported versions

For the moment Spring Boot version 1.5.x is supported.
