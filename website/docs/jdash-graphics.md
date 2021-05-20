---
title: JDash Graphics
---

import Tabs from '@theme/Tabs';
import TabItem from '@theme/TabItem';

This section will cover the features of the JDash Graphics module.

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
    <artifactId>jdash-graphics</artifactId>
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
    implementation 'com.alex1304.jdash:jdash-graphics:${version}'
}
```

</TabItem>
</Tabs>

## The `SpriteFactory`

The JDash Graphics module is quite small so there is not much to say here. It only contains two important classes, `SpriteFactory` and `GDUserIconSet`. Let's talk about the first one to begin with.

`SpriteFactory` is the class that will load the game assets in memory in order to do some image processing on them. You can then use it to generate icons on demand.

You create a new `SpriteFactory` with the `create()` method. Easy.

```java
SpriteFactory spriteFactory = SpriteFactory.create();
```

Now you can use `makeSprite` to generate the icon of your choice:

```java
BufferedImage image = spriteFactory.makeSprite(type, id, color1Id, color2Id, withGlowOutline);
```

There are quite a lot of parameters. You need to specify the type of icon (cube, ship, UFO...), the icon ID, the color 1 ID, the color 2 ID, and whether to add the glow outline on the icon.

## The `GDUserIconSet`

This class is more handy to generate icons when you have a `GDUserProfile` object. You can create one like this:

```java
GDUserProfile user = ...;
GDUserIconSet iconSet = GDUserIconSet.create(user, spriteFactory);
```

Now if you want to generate let's say the ship of the user, you only need to provide 1 parameter instead of the 5 of `SpriteFactory.makeSprite`:

```java
BufferedImage image = iconSet.generateIcon(IconType.SHIP);
```

:::tip
`BufferedImage` is a type from the Java Graphics API, you can for example save it to a file with `ImageIO.write()`:
```java
ImageIO.write(image, "png", new File("icon.png"));
```
:::
