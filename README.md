# JDash

A Java library for Geometry Dash.

# Features

- HTTP client for reading data from Geometry Dash servers
- Support for authentication with GD account ID / password
- A set of objects to manipulate the data

# Get started

Want to use JDash in your Java project? Great. Here are the instructions to get started:

## Pre-requisites

- Java Runtime Environment (version 8 or above)
- [Apache Maven](https://maven.apache.org)

## Installation

First, clone the project in the directory of your choice.

```sh
git clone https://github.com/Alex1304/jdash.git
```

Go to the newly created directory and compile the sources using Apache Maven.


```sh
cd jdash
mvn package
```

This will create a JAR file in the folder `target/`. Copy this JAR and paste it in the lib directory of your Java project (don't forget to update the classpath).

# How to use it ?

Here is a quick example of the usage of the library:

```Java
import com.alex1304dev.jdash.api.GDHttpClient;
import com.alex1304dev.jdash.api.request.GDLevelHttpRequest;
import com.alex1304dev.jdash.component.GDLevel;
import com.alex1304dev.jdash.exceptions.GDAPIException;

public class TestMain {

	public static void main(String[] args) {
		GDHttpClient client = new GDHttpClient();
		GDLevelHttpRequest req = new GDLevelHttpRequest(38693063);
		GDLevel level = null;
		
		try {
			level = client.fetch(req);
		} catch (GDAPIException e) {
			e.printStackTrace();
		}
		
		System.out.println(level);
	}

}

```

The full documentation hasn't been made yet, but it will come very soon in the next versions.

# License

MIT

# Contribute

Pull requests are more than welcome ! There is no guide that tells how to structure them, but if you explain clearly what you did in your pull request, we will be able to dicuss about it and getting it eventually merged. This is the same for issues, feel free to submit them as long as they are clear.

# Contact

E-mail: mirandaa1304@gmail.com

Discord: Alex1304#9704

Twitter: @gd_alex1304