---
title: Introduction
slug: /
---

## What is JDash?

JDash is a Java library that allows to collect and process data related to the popular platform game Geometry Dash. This library aims at providing Java developers tools to deal with game data without too much effort, with incentives to enforce good practices of Java development, such as immutability, proper use of null values, and design patterns.

## Overview

JDash is a multi-module library **requiring [JDK 11](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot) or above since version 4.0**. There are currently 4 modules.

### JDash Common module

Contains utility classes and data types to encode the different entities of Geometry Dash (levels, users...) required by all other modules.

### JDash Client module

Provides a high-level client to request data from Geometry Dash servers. It is powered by [Project Reactor](https://projectreactor.io) which allows to make requests in an efficient and non-blocking manner with backpressure handling (requests are queued internally and processed when resources are available, allowing requests to fail-fast in case the queue is full).

```java
GDClient client = GDClient.create();

// Block until request completes
GDLevel level = client.findLevelById(10565740).block();
System.out.println(level);

// But we can also choose to make it asychronous and non-blocking
client.getUserProfile(98006).subscribe(System.out::println); // will not block
```

### JDash Events module

Provides an event loop that can be subscribed to in order to detect when new levels get rated and when the Daily level or Weekly demon changes.

```java
GDClient client = GDClient.create();
GDEventLoop eventLoop = GDEventLoop.startWithDefaults(client);

eventLoop.on(AwardedAdd.class)
        .subscribe(event -> System.out.println("New level rated: "
                + event.addedLevel().name()));

eventLoop.on(DailyLevelChange.class)
        .subscribe(event -> System.out.println("New Daily level: "
                + event.after().name()));
```

### JDash Graphics module

Allows to generate icon images from game assets.

```java
// Get the user profile from the client or construct it manually
GDUserProfile user = ...;
// Will load game assets to memory. You should only need 1 instance in your application.
SpriteFactory spriteFactory = SpriteFactory.create();
// Create an icon set for the given user
GDUserIconSet iconSet = GDUserIconSet.create(user, spriteFactory);
// Generates the icon for the desired type, in this example the ball icon
BufferedImage icon = iconSet.generateIcon(IconType.BALL);
// Do anything with it, for example save it in tmp directory
String fileName = System.getProperty("java.io.tmpdir") + File.separator + "icon.png";
ImageIO.write(icon, "png", new File(fileName));
```

Result:

![icon](https://i.imgur.com/jZZdkRu.png)

Read the next guides to have more in-depth understanding on each of the modules described above.
