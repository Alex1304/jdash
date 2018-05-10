# Get started

Want to use JDash in your Java project? Great. This section covers the installation and the configuration part.

## Installation

### By downloading the JAR

This is the most simple way to get the library. Go to the [releases](https://github.com/Alex1304/jdash/releases) page and download the JAR file of the latest version.

### By compiling the sources

You can also get the JAR file by compiling the sources. To do that, first clone the project in the directory of your choice.

```sh
git clone https://github.com/Alex1304/jdash.git
```

Go to the newly created directory and compile the sources using [Apache Maven](https://maven.apache.org).


```sh
cd jdash
mvn package
```

This will create the JAR file in the `target/` directory.

**Note:** `mvn package` will execute the unitary tests before compiling. Since it's testing the HTTP client, an internet connection is required. If you want to compile it while you are offline, please delete the contents of the `src/test/` directory and do the command again.

## Configuration

The lib itself is ready to use. All you have to do is to paste the JAR file into your project's lib directory, and link it to your project in the build path, as you do for any other external library.
