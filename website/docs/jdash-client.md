---
title: JDash Client
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

This section will cover the features of the JDash Client module.

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
    <artifactId>jdash-client</artifactId>
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
    implementation 'com.alex1304.jdash:jdash-client:${version}'
}
```

</TabItem>
</Tabs>

## Using the client

### Creating a client

Very simple:

```java
GDClient client = GDClient.create();
```

The above will create a client with the following default characteristics:
* The default router: will route requests to the official Geometry Dash server
* No caching: results of requests will not be cached
* A random UUID: it has no practical use but may be used by the server to uniquely identify the client
* An empty set of followed account IDs: when requesting the "Followed" category of levels, the client follows nobody by default
* No authentication: the client will not be logged in to any account.

You may customize each of the above properties by chaining `with*` methods:

```java
GDClient client = GDClient.create()
        .withRouter(/* router */)
        .withCache(/* cache */)
        .withUniqueDeviceId(/* UUID */)
        .withFollowedAccountIds(/* Set of followed account IDs */)
        .withAuthentication(/* player ID, account ID, account password */);
```

:::info
`GDClient` instances are immutable, meaning that calling one of the `with*` methods alone without storing the return value will do nothing. Just like doing `String.toUpperCase()` without storing the return value will not have any effect. Each `with*` method creates a new copy of `GDClient` with the new characteristics without altering the existing instance.
:::

### Making requests

Once your client is ready, you can use it to make requests to get data from Geometry Dash. Here is an example:

```java
GDLevel level = client.findLevelById(10565740).block();
System.out.println(level);
```

As the name suggests, `findLevelById` allows to retrieve data on a level by providing its ID. You can check the [Javadoc](https://javadoc.io/doc/com.alex1304.jdash/jdash-client) to get the full list of methods that the client supports.

Did you notice the `.block()` at the end? This is because all methods performing a request return a `org.reactivestreams.Publisher` (more specifically a `reactor.core.publisher.Mono` for requests expecting a single result, and a `reactor.core.publisher.Flux` for requests expecting multiple results). The request is executed if and only if the `Mono` or the `Flux` is subscribed to. The `.block()` method subscribes to the publisher and blocks the current thread until the request completes and emits the result. You can use alternatively `.subscribe(Consumer)` to handle the result in a callback without blocking the current thread:

```java
client.getUserProfile(98006).subscribe(System.out::println); // will not block
```

You also have many useful methods like `.map`, `.filter`, `.flatMap`, etc to process results and chain requests in a pipelined way, similar to `java.util.Stream` but even more powerful:

```java
// Prints the title of the song used in the level of ID 10565740 (Bloodbath)
client.downloadLevel(10565740)
        .filter(level -> level.songId().isPresent())
        .flatMap(level -> client.getSongInfo(level.songId().orElseThrow()))
        .map(GDSong::title)
        .subscribe(System.out::println); // Prints "At the Speed of Light"
```

:::tip
If a method returns a `Flux<T>`, you can convert it to a `Mono<List<T>>` using `Flux.collectList()`.
:::

:::info
Check out the [Project Reactor docs](https://projectreactor.io) to learn more.
:::

### The router

The router is in charge of accepting requests and sending them over to the server. By default, the client routes requests to the official Geometry Dash server. By customizing the router, you will be able to:
* Change the server URL for example to route requests to a Geometry Dash private server (GDPS)
* Set a limit in the number of requests that can be sent within a certain timeframe to avoid spamming the server with requests
* Set a timeout to make requests fail when they take too long to be processed
* Change the scheduler on which to emit responses.

You can customize any of the aforementioned parameters by doing this:

```java
GDRouter router = GDRouter.builder()
        // You can set the URL to a GDPS here
        .setBaseUrl("https://gdps.alex1304.com/database")
        // We are limiting to 1 request per second. Excessive requests will be queued.
        .setRequestLimiter(RequestLimiter.of(1, Duration.ofSeconds(1)))
        // Make requests fail if they take more than 3 seconds to complete
        .setRequestTimeout(Duration.ofSeconds(3))
        // Schedulers.immediate() can improve performance, but you are not allowed to
        // use .block() if you use that. The one by default is Schedulers.boundedElastic()
        // which allows blocking calls.
        .setScheduler(Schedulers.immediate())
        .build();

// You can then pass this router to a client
GDClient client = GDClient.create().withRouter(router);
```

### The cache

If you are doing the same requests multiple times, you may want a cache system to avoid sending a request to the server for data you've already fetched previously. JDash comes with a cache API to easily get that up for you.

A cache is represented by the `GDCache` interface. Three implementations are provided by default:
* `GDCache.disabled()`: an implementation that does nothing. Its purpose is to simulate a disabled cache.
* `GDCache.inMemory()`: an implementation based on a simple `ConcurrentHashMap` that caches everything indefinitely. It has the advantage of not requiring any extra configuration for it to work, but may cause memory leaks if not manually cleared regularly.
* `GDCache.caffeine(UnaryOperator)`: an implementation based on the [Caffeine library](https://github.com/ben-manes/caffeine) which allows to customize things such as the duration data should stay in cache after last access and the maximum size.

An example of cache automatically clearing elements 10 minutes after last access:
```java
GDCache cache = GDCache.caffeine(caffeine -> caffeine.expireAfterAccess(Duration.ofMinutes(10)));

GDClient client = GDClient.create().withCache(cache);
```

It is possible to disable the cache for specific requests. For example if you want to be sure to get the latest Daily level info and not something potentially outdated from cache:

```java
GDTimelyInfo info = client.withCacheDisabled().getDailyLevelInfo().block();
```

### Authentication

Some requests require authentication in order to be executed. For example, the following will fail:

```java
GDClient client = GDClient.create();

List<GDPrivateMessage> privateMessages = client.getPrivateMessages(0) // IllegalStateException
        .collectList()
        .block();
```

In order to authenticate the client, you have two options:
* Use `client.withAuthentication(playerId, accountId, accountPassword)`. This method returns a `GDClient` directly, but it requires you to know your player ID and your account ID. Also, it won't validate if the password is correct.
* Use `client.login(username, password)`. It allows to login using your username instead of your account and player IDs, and will properly validate your credentials. However, it will require the client to make a request to the server, so it will return a `Mono<GDClient>` instead of a `GDClient` like the first method.

The following will work, assuming credentials are valid:
```java
GDClient client = GDClient.create().withAuthentication(123, 456, "MyP@ssw0rd");
// OR
GDClient client = GDClient.create().login("Player123", "MyP@ssw0rd").block();

List<GDPrivateMessage> privateMessages = client.getPrivateMessages(0)
        .collectList()
        .block(); // It works!
```

## Handling exceptions

When using the client, you will inevitably come across exceptions when executing requests. Any error that is emitted through `Mono` and `Flux` is wrapped inside a `GDClientException`. So all you need to do is to catch `GDClientException` when blocking, or use `Flux/Mono.onErrorResume(GDClientException.class, e -> ...)` if you don't block. Use `GDClientException.getCause()` to get the original exception.

```java
try {
    GDUserProfile user = client.getUserProfile(98006).block();
} catch (GDClientException e) {
    // Handle error here
    Throwable cause = e.getCause(); // the original exception
}
```

The original exception obtained via `getCause()` can be of any nature, but here are the most frequent ones:
* `ActionFailedException`: emitted when the GD server returns an error code like `-1`
* `HttpResponseException`: emitted when the GD server returns an unusual HTTP error code like `500 Internal Server Error`
* `ResponseDeserializationException`: emitted when the GD server returns data that could not be decoded into a valid Java object like `GDLevel` or `GDUserProfile`. Generally, this exception also carries its own `getCause()` providing details on why the deserialization failed.
* `RetryExhaustedException`: emitted when there are communication problems with the GD server. By default, the request is retried automatically up to 10 times when the connection fails, but if all attempts are exhausted it will result in this exception being emitted.
* `EmissionException`: emitted when the request could not be queued. It can happen if the request queue of the router is full.

:::caution
The `IllegalStateException` that happens when calling a method requiring authentication with a non-authenticated client is thrown immediately upon calling the method; it is not emitted through the `Flux`/`Mono` upon subscription.
:::
