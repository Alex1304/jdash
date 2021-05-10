# JDash

[![Maven Central](https://img.shields.io/maven-central/v/com.github.alex1304/jdash.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.alex1304%22%20AND%20a:%22jdash%22) [![Documentation Status](https://readthedocs.org/projects/jdash/badge/?version=latest)](https://jdash.readthedocs.io/en/latest/?badge=latest)

A Java library for Geometry Dash.

# Features

- Provides a high-level HTTP client to fetch data from Geometry Dash servers
- Supports authentication with Geometry Dash account username / password
- Allows for both blocking and reactive/non-blocking programming styles
- Supports request caching for better performances
- Provides a convenient way to generate player icons.

# Get started

In a Maven project, add the following dependency to your `pom.xml`:

```xml
<dependencies>

	<!-- ... your other dependencies -->
	
	<dependency>
		<groupId>com.github.alex1304</groupId>
		<artifactId>jdash</artifactId>
		<version><!-- Latest version number --></version>
	</dependency>
</dependencies>
```

Or download the latest version at the [releases](https://github.com/Alex1304/jdash/releases) page, and add all required
JAR files into the build path of your Java project.

# How to use it ?

There are many ways to use the library. Here are a few examples:

## Anonymous client, blocking style

```Java
import jdash.jdash.client.AnonymousGDClient;
import jdash.jdash.client.GDClientBuilder;
import jdash.common.entity.GDLevel;
import jdash.jdash.common.entity.GDUser;

public final class TestMain {

    public static void main(String[] args) {
        // Build an anonymous client
        AnonymousGDClient client = GDClientBuilder.create().buildAnonymous();
        // Fetch level of ID 10565740
        GDLevel level = client.getLevelById(10565740).block();
        // Fetch user of name "RobTop"
        GDUser user = client.searchUser("RobTop").block();
        // Do stuff with them, for example print them
        System.out.println(level);
        System.out.println(user);
    }

}

```

## Anonymous client, non-blocking style

```Java
import jdash.jdash.client.AnonymousGDClient;
import jdash.jdash.client.GDClientBuilder;

public final class TestMain {

    public static void main(String[] args) throws Exception {
        // Build an anonymous client
        AnonymousGDClient client = GDClientBuilder.create().buildAnonymous();
        // Fetch level of ID 10565740 then print it
        client.getLevelById(10565740).doOnSuccess(level -> System.out.println(level)).subscribe();
        // Fetch user of name "RobTop" then print it
        client.searchUser("RobTop").doOnSuccess(user -> System.out.println(user)).subscribe();
        // The above calls are non-blocking, meaning that you can do something else in parallel while the client is doing its job!
        // Let's put that in evidence:
        System.out.println("Start another long-running task here...");
        Thread.sleep(5000);
        System.out.println("Bye!");
    }

}

```

## Authenticated client

```Java
import jdash.jdash.client.AuthenticatedGDClient;
import jdash.jdash.client.GDClientBuilder;
import jdash.jdash.common.entity.GDMessage;
import jdash.jdash.client.exception.LoginFailedException;
import jdash.jdash.util.GDPaginator;

public final class TestMain {

    public static void main(String[] args) throws Exception {
        // Build an anonymous client
        try {
            AuthenticatedGDClient client = GDClientBuilder.create().buildAuthenticated(new Credentials("MyUsername", "MyP@ssw0rd")).block();
            // With an authenticated client, you can do cool stuff like this:
            client.getPrivateMessages(0).doOnSuccess(messages -> {
                for (GDMessage message : messages) {
                    System.out.println(message);
                }
            }).subscribe();
            // Pretty self-explanatory, right? It's fetching your in-game private messages!
            // Here's a blocking version:
            GDPaginator<GDMessage> messages = client.getPrivateMessages(0).block();
            for (GDMessage message : messages) {
                System.out.println(message);
            }
            // GDPaginator is basically a List but with extra metadata info related to pagination
            // (number of pages, number of items per page, etc). RTFM for more details.
        } catch (GDLoginFailedException e) {
            System.err.println("Oops! Login failed.");
        }
    }

}

```

The full documentation is available [here](http://jdash.readthedocs.io/en/latest).

# License

MIT

# Contribute

Issues and Pull requests are more than welcome ! There is no guide that tells how to structure them, but if you explain
clearly what you did in your pull request, we will be able to dicuss about it and getting it eventually merged. This is
the same for issues, feel free to submit them as long as they are clear.

# Contact

E-mail: mirandaa1304@gmail.com

Discord: Alex1304#9704

Twitter: @gd_alex1304
