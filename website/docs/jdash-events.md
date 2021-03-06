---
title: JDash Events
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

This section will cover the features of the JDash Events module.

## Installation

Add the Maven or Gradle dependency to your project as described below:

<Tabs
    groupId="build-tools"
    defaultValue="maven"
    values={[
        {label: 'Maven', value: 'maven'},
        {label: 'Gradle', value: 'gradle'},
    ]}>
<TabItem value="maven">

```xml
<dependency>
    <groupId>com.alex1304.jdash</groupId>
    <artifactId>jdash-events</artifactId>
    <version>${version}</version> <!-- replace with latest version -->
</dependency>
```

</TabItem>
<TabItem value="gradle">

```groovy
repositories {
    mavenCentral()
}

dependencies {
    // Replace ${version} with latest version
    implementation 'com.alex1304.jdash:jdash-events:${version}'
}
```

</TabItem>
</Tabs>

## The event loop

An event loop is a task that runs in the background that will repeat some identical requests at regular intervals, and emit events in response to something that is detected when comparing the results of those repeated requests. JDash provides an event loop that allows to perform specific requests using the client in order to emit events such as new rated levels or Daily level changes.

### Starting an event loop with default parameters

Very simple:

```java
GDClient client = GDClient.create();

GDEventLoop eventLoop = GDEventLoop.startWithDefaults(client);
```

This starts an event loop with the following default characteristics:
* Produces events related to changes in the Awarded category and changes in the Daily level/Weekly demon
* A default interval of 1 minute: the loop will repeat requests every 1 minute
* Emits events on `Schedulers.boundedElastic()`

### Building a custom event loop

It is possible to customize the event loop using the builder:

```java
GDEventLoop customLoop = GDEventLoop.builder(client)
        .setEventProducers(Set.of(GDEventProducer.awardedLevels()))
        .setInterval(Duration.ofSeconds(15))
        .setScheduler(Schedulers.immediate())
        .buildAndStart();
```

Here we are building a loop that only produces events related to awarded levels; it won't make requests for Daily levels and Weekly demons. It will make requests every 15 seconds and will emit events on `Schedulers.immediate()`.

### Subscribing to events

Use `eventLoop.on(type)` to subscribe to the event flux:

```java
eventLoop.on(AwardedAdd.class)
        .subscribe(event -> System.out.println("New level rated: "
                + event.addedLevel().name()));

eventLoop.on(AwardedRemove.class)
        .subscribe(event -> System.out.println("Level unrated: "
                + event.removedLevel().name()));

eventLoop.on(AwardedUpdate.class)
        .subscribe(event -> System.out.println("Level rating changed. Before: "
                + event.oldData() + " - After: " + event.newData()));

eventLoop.on(DailyLevelChange.class)
        .subscribe(event -> System.out.println("New Daily level: "
                + event.after().name()));

eventLoop.on(WeeklyDemonChange.class)
        .subscribe(event -> System.out.println("New Weekly demon: "
                + event.after().name()));
```

### Stopping an event loop

Just invoke `eventLoop.stop()`. The event loop instance will stop making requests and will terminate all current subscriptions to `eventLoop.on(type)`.
