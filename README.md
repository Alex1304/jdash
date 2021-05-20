# JDash

![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/Alex1304/jdash?sort=semver)
[![Maven Central](https://img.shields.io/maven-central/v/com.alex1304.jdash/jdash.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.alex1304.jdash%22%20AND%20a:%22jdash%22)
![License](https://img.shields.io/github/license/Alex1304/jdash)
[![javadoc](https://javadoc.io/badge2/com.alex1304.jdash/jdash-client/javadoc.svg)](https://javadoc.io/doc/com.alex1304.jdash/jdash-client)

A reactive Geometry Dash API wrapper for Java.

## Overview

JDash is a multi-module library **requiring [JDK 11](https://adoptopenjdk.net/?variant=openjdk11&jvmVariant=hotspot) or above since version 4.0**. There are currently 4 modules.

### JDash Common module

Contains utility classes and data types to encode the different entities of Geometry Dash (levels, users...) required by all other modules.

Maven dependency (**if you are already using one of the other modules, you don't need to add this dependency as other modules transitively depend on it**):

```xml
<dependency>
    <groupId>com.alex1304.jdash</groupId>
    <artifactId>jdash-common</artifactId>
    <version>${version}</version> <!-- replace with latest version -->
</dependency>
```

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

Maven dependency:

```xml
<dependency>
    <groupId>com.alex1304.jdash</groupId>
    <artifactId>jdash-client</artifactId>
    <version>${version}</version> <!-- replace with latest version -->
</dependency>
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

Maven dependency:

```xml
<dependency>
    <groupId>com.alex1304.jdash</groupId>
    <artifactId>jdash-events</artifactId>
    <version>${version}</version> <!-- replace with latest version -->
</dependency>
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

Maven dependency:

```xml
<dependency>
    <groupId>com.alex1304.jdash</groupId>
    <artifactId>jdash-graphics</artifactId>
    <version>${version}</version> <!-- replace with latest version -->
</dependency>
```

## Documentation

The full documentation is available at: https://jdash.alex1304.com

Javadoc:
* For the common module: https://javadoc.io/doc/com.alex1304.jdash/jdash-common
* For the client module: https://javadoc.io/doc/com.alex1304.jdash/jdash-client
* For the events module: https://javadoc.io/doc/com.alex1304.jdash/jdash-events
* For the graphics module: https://javadoc.io/doc/com.alex1304.jdash/jdash-graphics

## License

This project is licensed under the MIT licence.

## Contribute

Have a feature to suggest or a bug to report ? Issues and pull requests are more than welcome! Make sure to follow the template and share your ideas.

## Contact

E-mail: mirandaa1304@gmail.com

Discord: Alex1304#9704

Twitter: @gd_alex1304
