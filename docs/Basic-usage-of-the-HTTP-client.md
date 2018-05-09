# Basic usage of the HTTP client

JDash provides a simple HTTP client to make requests to Geometry Dash servers. This section will show a basic example of its usage

## The `GDHttpClient` object

This object represents the client itself. The role of a client is to send requests to a server and return the response.
The client can be either anonymous or authenticated. When it's authenticated, it may have access to protected features in the API. JDash's HTTP client supports both.

### Sending a simple request with an anonymous client

Instanciating an anonymous client is very simple, just use the constructor without arguments, like this:

```Java
GDHttpClient client = new GDHttpClient();
```

Now let's consider you want to get data for the level Bloodbath by Riot. To get data for an online level, use the `GDLevelHttpRequest` object, that you will pass to the `fetch(...)` method of the client. The constructor of the request object takes the level ID you want to fetch as argument. Fetching from Geometry Dash servers might fail, so you must surround the fetch method with a try/catch block. If the request is successful, a `GDLevel` instance containing all info about the level will be returned.

```Java
// The ID of the desired online level
final long bloodbathLevelID = 10565740;

// The HTTP anonymous client
GDHttpClient client = new GDHttpClient();

// The result of the request will be stored here
GDLevel level = null;

// The request object taking the level ID in the constructor
GDLevelHttpRequest request = new GDLevelHttpRequest(bloodbathLevelID);

// Fetch using the client
try {
	level = client.fetch(request);
} catch (GDAPIException e) {
	e.printStackTrace(); // An error occured? It will just be printed.
}

// Display the result !
System.out.println(level); // This will call the default toString() of GDLevel which displays all fields
```

If you run this code in a main class, you will get something like this in the console (if the request was successful)

```
GDLevel [id=10565740, name=Bloodbath, creatorID=503085, description=Whose blood will be spilt in the Bloodbath? Who will the victors be? How many will survive? Good luck..., difficulty=INSANE, demonDifficulty=EXTREME, stars=10, featuredScore=10330, isEpic=false, downloads=10144723, likes=990316, length=XL, pass=-1, songID=467339, coinCount=0, hasCoinsVerified=false, levelVersion=1, gameVersion=19, objectCount=0, isDemon=true, isAuto=false, originalLevelID=7679228, audioTrack=0, requestedStars=0, uploadTimestamp=2 years, lastUpdatedTimestamp=2 years]

```

### Authenticated HTTP client

As said previously, you can make the client authenticated. This is done by giving an account ID and a password of an existing GD account to the constructor

```Java
GDHttpClient client = new GDHttpClient(123456789, "MyP@ssword");
```

With this, you can for example view your private messages inbox

```Java
// The HTTP authenticated client
GDHttpClient client = new GDHttpClient(123456789, "MyP@ssword");

// The result of the request will be stored here
GDComponentList<GDMessage> messages = null;

// The request object takes a page number in the constructor. First page is 0.
GDMessageListHttpRequest request = new GDMessageListHttpRequest(0);

// Fetch using the client
try {
	messages = client.fetch(request);
} catch (GDAPIException e) {
	e.printStackTrace(); // An error occured? It will just be printed.
}

// Display the result !
System.out.println(messages); // This will call the default toString() of GDLevel which displays all fields
```

Simple, isn't it?