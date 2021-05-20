(self.webpackChunkdocs=self.webpackChunkdocs||[]).push([[645],{1711:function(e,t,n){"use strict";n.r(t),n.d(t,{frontMatter:function(){return s},metadata:function(){return c},toc:function(){return u},default:function(){return h}});var a=n(2122),i=n(9756),l=(n(7294),n(3905)),o=n(1395),r=n(8215),s={title:"JDash Client"},c={unversionedId:"jdash-client",id:"jdash-client",isDocsHomePage:!1,title:"JDash Client",description:"This section will cover the features of the JDash Client module.",source:"@site/docs/jdash-client.md",sourceDirName:".",slug:"/jdash-client",permalink:"/jdash-client",editUrl:"https://github.com/Alex1304/jdash/edit/master/website/docs/jdash-client.md",version:"current",frontMatter:{title:"JDash Client"},sidebar:"someSidebar",previous:{title:"Introduction",permalink:"/"},next:{title:"JDash Events",permalink:"/jdash-events"}},u=[{value:"Installation",id:"installation",children:[]},{value:"Using the client",id:"using-the-client",children:[{value:"Creating a client",id:"creating-a-client",children:[]},{value:"Making requests",id:"making-requests",children:[]},{value:"The router",id:"the-router",children:[]},{value:"The cache",id:"the-cache",children:[]},{value:"Authentication",id:"authentication",children:[]}]},{value:"Handling exceptions",id:"handling-exceptions",children:[]}],d={toc:u};function h(e){var t=e.components,n=(0,i.Z)(e,["components"]);return(0,l.kt)("wrapper",(0,a.Z)({},d,n,{components:t,mdxType:"MDXLayout"}),(0,l.kt)("p",null,"This section will cover the features of the JDash Client module."),(0,l.kt)("h2",{id:"installation"},"Installation"),(0,l.kt)("p",null,"Add the Maven or Gradle dependency to your project as described below:"),(0,l.kt)(o.Z,{groupId:"build-tools",defaultValue:"maven",values:[{label:"Maven",value:"maven"},{label:"Gradle",value:"gradle"}],mdxType:"Tabs"},(0,l.kt)(r.Z,{value:"maven",mdxType:"TabItem"},(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-xml"},"<dependency>\n    <groupId>com.alex1304.jdash</groupId>\n    <artifactId>jdash-client</artifactId>\n    <version>${version}</version> \x3c!-- replace with latest version --\x3e\n</dependency>\n"))),(0,l.kt)(r.Z,{value:"gradle",mdxType:"TabItem"},(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-groovy"},"repositories {\n    mavenCentral()\n}\n\ndependencies {\n    // Replace ${version} with latest version\n    implementation 'com.alex1304.jdash:jdash-client:${version}'\n}\n")))),(0,l.kt)("h2",{id:"using-the-client"},"Using the client"),(0,l.kt)("h3",{id:"creating-a-client"},"Creating a client"),(0,l.kt)("p",null,"Very simple:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-java"},"GDClient client = GDClient.create();\n")),(0,l.kt)("p",null,"The above will create a client with the following default characteristics:"),(0,l.kt)("ul",null,(0,l.kt)("li",{parentName:"ul"},"The default router: will route requests to the official Geometry Dash server"),(0,l.kt)("li",{parentName:"ul"},"No caching: results of requests will not be cached"),(0,l.kt)("li",{parentName:"ul"},"A random UUID: it has no practical use but may be used by the server to uniquely identify the client"),(0,l.kt)("li",{parentName:"ul"},'An empty set of followed account IDs: when requesting the "Followed" category of levels, the client follows nobody by default'),(0,l.kt)("li",{parentName:"ul"},"No authentication: the client will not be logged in to any account.")),(0,l.kt)("p",null,"You may customize each of the above properties by chaining ",(0,l.kt)("inlineCode",{parentName:"p"},"with*")," methods:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-java"},"GDClient client = GDClient.create()\n        .withRouter(/* router */)\n        .withCache(/* cache */)\n        .withUniqueDeviceId(/* UUID */)\n        .withFollowedAccountIds(/* Set of followed account IDs */)\n        .withAuthentication(/* player ID, account ID, account password */);\n")),(0,l.kt)("div",{className:"admonition admonition-info alert alert--info"},(0,l.kt)("div",{parentName:"div",className:"admonition-heading"},(0,l.kt)("h5",{parentName:"div"},(0,l.kt)("span",{parentName:"h5",className:"admonition-icon"},(0,l.kt)("svg",{parentName:"span",xmlns:"http://www.w3.org/2000/svg",width:"14",height:"16",viewBox:"0 0 14 16"},(0,l.kt)("path",{parentName:"svg",fillRule:"evenodd",d:"M7 2.3c3.14 0 5.7 2.56 5.7 5.7s-2.56 5.7-5.7 5.7A5.71 5.71 0 0 1 1.3 8c0-3.14 2.56-5.7 5.7-5.7zM7 1C3.14 1 0 4.14 0 8s3.14 7 7 7 7-3.14 7-7-3.14-7-7-7zm1 3H6v5h2V4zm0 6H6v2h2v-2z"}))),"info")),(0,l.kt)("div",{parentName:"div",className:"admonition-content"},(0,l.kt)("p",{parentName:"div"},(0,l.kt)("inlineCode",{parentName:"p"},"GDClient")," instances are immutable, meaning that calling one of the ",(0,l.kt)("inlineCode",{parentName:"p"},"with*")," methods alone without storing the return value will do nothing. Just like doing ",(0,l.kt)("inlineCode",{parentName:"p"},"String.toUpperCase()")," without storing the return value will not have any effect. Each ",(0,l.kt)("inlineCode",{parentName:"p"},"with*")," method creates a new copy of ",(0,l.kt)("inlineCode",{parentName:"p"},"GDClient")," with the new characteristics without altering the existing instance."))),(0,l.kt)("h3",{id:"making-requests"},"Making requests"),(0,l.kt)("p",null,"Once your client is ready, you can use it to make requests to get data from Geometry Dash. Here is an example:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-java"},"GDLevel level = client.findLevelById(10565740).block();\nSystem.out.println(level);\n")),(0,l.kt)("p",null,"As the name suggests, ",(0,l.kt)("inlineCode",{parentName:"p"},"findLevelById")," allows to retrieve data on a level by providing its ID. You can check the ",(0,l.kt)("a",{parentName:"p",href:"https://javadoc.io/doc/com.alex1304.jdash/jdash-client"},"Javadoc")," to get the full list of methods that the client supports."),(0,l.kt)("p",null,"Did you notice the ",(0,l.kt)("inlineCode",{parentName:"p"},".block()")," at the end? This is because all methods performing a request return a ",(0,l.kt)("inlineCode",{parentName:"p"},"org.reactivestreams.Publisher")," (more specifically a ",(0,l.kt)("inlineCode",{parentName:"p"},"reactor.core.publisher.Mono")," for requests expecting a single result, and a ",(0,l.kt)("inlineCode",{parentName:"p"},"reactor.core.publisher.Flux")," for requests expecting multiple results). The request is executed if and only if the ",(0,l.kt)("inlineCode",{parentName:"p"},"Mono")," or the ",(0,l.kt)("inlineCode",{parentName:"p"},"Flux")," is subscribed to. The ",(0,l.kt)("inlineCode",{parentName:"p"},".block()")," method subscribes to the publisher and blocks the current thread until the request completes and emits the result. You can use alternatively ",(0,l.kt)("inlineCode",{parentName:"p"},".subscribe(Consumer)")," to handle the result in a callback without blocking the current thread:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-java"},"client.getUserProfile(98006).subscribe(System.out::println); // will not block\n")),(0,l.kt)("p",null,"You also have many useful methods like ",(0,l.kt)("inlineCode",{parentName:"p"},".map"),", ",(0,l.kt)("inlineCode",{parentName:"p"},".filter"),", ",(0,l.kt)("inlineCode",{parentName:"p"},".flatMap"),", etc to process results and chain requests in a pipelined way, similar to ",(0,l.kt)("inlineCode",{parentName:"p"},"java.util.Stream")," but even more powerful:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-java"},'// Prints the title of the song used in the level of ID 10565740 (Bloodbath)\nclient.downloadLevel(10565740)\n        .filter(level -> level.songId().isPresent())\n        .flatMap(level -> client.getSongInfo(level.songId().orElseThrow()))\n        .map(GDSong::title)\n        .subscribe(System.out::println); // Prints "At the Speed of Light"\n')),(0,l.kt)("div",{className:"admonition admonition-tip alert alert--success"},(0,l.kt)("div",{parentName:"div",className:"admonition-heading"},(0,l.kt)("h5",{parentName:"div"},(0,l.kt)("span",{parentName:"h5",className:"admonition-icon"},(0,l.kt)("svg",{parentName:"span",xmlns:"http://www.w3.org/2000/svg",width:"12",height:"16",viewBox:"0 0 12 16"},(0,l.kt)("path",{parentName:"svg",fillRule:"evenodd",d:"M6.5 0C3.48 0 1 2.19 1 5c0 .92.55 2.25 1 3 1.34 2.25 1.78 2.78 2 4v1h5v-1c.22-1.22.66-1.75 2-4 .45-.75 1-2.08 1-3 0-2.81-2.48-5-5.5-5zm3.64 7.48c-.25.44-.47.8-.67 1.11-.86 1.41-1.25 2.06-1.45 3.23-.02.05-.02.11-.02.17H5c0-.06 0-.13-.02-.17-.2-1.17-.59-1.83-1.45-3.23-.2-.31-.42-.67-.67-1.11C2.44 6.78 2 5.65 2 5c0-2.2 2.02-4 4.5-4 1.22 0 2.36.42 3.22 1.19C10.55 2.94 11 3.94 11 5c0 .66-.44 1.78-.86 2.48zM4 14h5c-.23 1.14-1.3 2-2.5 2s-2.27-.86-2.5-2z"}))),"tip")),(0,l.kt)("div",{parentName:"div",className:"admonition-content"},(0,l.kt)("p",{parentName:"div"},"If a method returns a ",(0,l.kt)("inlineCode",{parentName:"p"},"Flux<T>"),", you can convert it to a ",(0,l.kt)("inlineCode",{parentName:"p"},"Mono<List<T>>")," using ",(0,l.kt)("inlineCode",{parentName:"p"},"Flux.collectList()"),"."))),(0,l.kt)("div",{className:"admonition admonition-info alert alert--info"},(0,l.kt)("div",{parentName:"div",className:"admonition-heading"},(0,l.kt)("h5",{parentName:"div"},(0,l.kt)("span",{parentName:"h5",className:"admonition-icon"},(0,l.kt)("svg",{parentName:"span",xmlns:"http://www.w3.org/2000/svg",width:"14",height:"16",viewBox:"0 0 14 16"},(0,l.kt)("path",{parentName:"svg",fillRule:"evenodd",d:"M7 2.3c3.14 0 5.7 2.56 5.7 5.7s-2.56 5.7-5.7 5.7A5.71 5.71 0 0 1 1.3 8c0-3.14 2.56-5.7 5.7-5.7zM7 1C3.14 1 0 4.14 0 8s3.14 7 7 7 7-3.14 7-7-3.14-7-7-7zm1 3H6v5h2V4zm0 6H6v2h2v-2z"}))),"info")),(0,l.kt)("div",{parentName:"div",className:"admonition-content"},(0,l.kt)("p",{parentName:"div"},"Check out the ",(0,l.kt)("a",{parentName:"p",href:"https://projectreactor.io"},"Project Reactor docs")," to learn more."))),(0,l.kt)("h3",{id:"the-router"},"The router"),(0,l.kt)("p",null,"The router is in charge of accepting requests and sending them over to the server. By default, the client routes requests to the official Geometry Dash server. By customizing the router, you will be able to:"),(0,l.kt)("ul",null,(0,l.kt)("li",{parentName:"ul"},"Change the server URL for example to route requests to a Geometry Dash private server (GDPS)"),(0,l.kt)("li",{parentName:"ul"},"Set a limit in the number of requests that can be sent within a certain timeframe to avoid spamming the server with requests"),(0,l.kt)("li",{parentName:"ul"},"Set a timeout to make requests fail when they take too long to be processed"),(0,l.kt)("li",{parentName:"ul"},"Change the scheduler on which to emit responses.")),(0,l.kt)("p",null,"You can customize any of the aforementioned parameters by doing this:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-java"},'GDRouter router = GDRouter.builder()\n        // You can set the URL to a GDPS here\n        .setBaseUrl("https://gdps.alex1304.com/database")\n        // We are limiting to 1 request per second. Excessive requests will be queued.\n        .setRequestLimiter(RequestLimiter.of(1, Duration.ofSeconds(1)))\n        // Make requests fail if they take more than 3 seconds to complete\n        .setRequestTimeout(Duration.ofSeconds(3))\n        // Schedulers.immediate() can improve performance, but you are not allowed to\n        // use .block() if you use that. The one by default is Schedulers.boundedElastic()\n        // which allows blocking calls.\n        .setScheduler(Schedulers.immediate())\n        .build();\n\n// You can then pass this router to a client\nGDClient client = GDClient.create().withRouter(router);\n')),(0,l.kt)("h3",{id:"the-cache"},"The cache"),(0,l.kt)("p",null,"If you are doing the same requests multiple times, you may want a cache system to avoid sending a request to the server for data you've already fetched previously. JDash comes with a cache API to easily get that up for you."),(0,l.kt)("p",null,"A cache is represented by the ",(0,l.kt)("inlineCode",{parentName:"p"},"GDCache")," interface. Three implementations are provided by default:"),(0,l.kt)("ul",null,(0,l.kt)("li",{parentName:"ul"},(0,l.kt)("inlineCode",{parentName:"li"},"GDCache.disabled()"),": an implementation that does nothing. Its purpose is to simulate a disabled cache."),(0,l.kt)("li",{parentName:"ul"},(0,l.kt)("inlineCode",{parentName:"li"},"GDCache.inMemory()"),": an implementation based on a simple ",(0,l.kt)("inlineCode",{parentName:"li"},"ConcurrentHashMap")," that caches everything indefinitely. It has the advantage of not requiring any extra configuration for it to work, but may cause memory leaks if not manually cleared regularly."),(0,l.kt)("li",{parentName:"ul"},(0,l.kt)("inlineCode",{parentName:"li"},"GDCache.caffeine(UnaryOperator)"),": an implementation based on the ",(0,l.kt)("a",{parentName:"li",href:"https://github.com/ben-manes/caffeine"},"Caffeine library")," which allows to customize things such as the duration data should stay in cache after last access and the maximum size.")),(0,l.kt)("p",null,"An example of cache automatically clearing elements 10 minutes after last access:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-java"},"GDCache cache = GDCache.caffeine(caffeine -> caffeine.expireAfterAccess(Duration.ofMinutes(10)));\n\nGDClient client = GDClient.create().withCache(cache);\n")),(0,l.kt)("p",null,"It is possible to disable the cache for specific requests. For example if you want to be sure to get the latest Daily level info and not something potentially outdated from cache:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-java"},"GDTimelyInfo info = client.withCacheDisabled().getDailyLevelInfo().block();\n")),(0,l.kt)("h3",{id:"authentication"},"Authentication"),(0,l.kt)("p",null,"Some requests require authentication in order to be executed. For example, the following will fail:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-java"},"GDClient client = GDClient.create();\n\nList<GDPrivateMessage> privateMessages = client.getPrivateMessages(0) // IllegalStateException\n        .collectList()\n        .block();\n")),(0,l.kt)("p",null,"In order to authenticate the client, you have two options:"),(0,l.kt)("ul",null,(0,l.kt)("li",{parentName:"ul"},"Use ",(0,l.kt)("inlineCode",{parentName:"li"},"client.withAuthentication(playerId, accountId, accountPassword)"),". This method returns a ",(0,l.kt)("inlineCode",{parentName:"li"},"GDClient")," directly, but it requires you to know your player ID and your account ID. Also, it won't validate if the password is correct."),(0,l.kt)("li",{parentName:"ul"},"Use ",(0,l.kt)("inlineCode",{parentName:"li"},"client.login(username, password)"),". It allows to login using your username instead of your account and player IDs, and will properly validate your credentials. However, it will require the client to make a request to the server, so it will return a ",(0,l.kt)("inlineCode",{parentName:"li"},"Mono<GDClient>")," instead of a ",(0,l.kt)("inlineCode",{parentName:"li"},"GDClient")," like the first method.")),(0,l.kt)("p",null,"The following will work, assuming credentials are valid:"),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-java"},'GDClient client = GDClient.create().withAuthentication(123, 456, "MyP@ssw0rd");\n// OR\nGDClient client = GDClient.create().login("Player123", "MyP@ssw0rd").block();\n\nList<GDPrivateMessage> privateMessages = client.getPrivateMessages(0)\n        .collectList()\n        .block(); // It works!\n')),(0,l.kt)("h2",{id:"handling-exceptions"},"Handling exceptions"),(0,l.kt)("p",null,"When using the client, you will inevitably come across exceptions when executing requests. Any error that is emitted through ",(0,l.kt)("inlineCode",{parentName:"p"},"Mono")," and ",(0,l.kt)("inlineCode",{parentName:"p"},"Flux")," is wrapped inside a ",(0,l.kt)("inlineCode",{parentName:"p"},"GDClientException"),". So all you need to do is to catch ",(0,l.kt)("inlineCode",{parentName:"p"},"GDClientException")," when blocking, or use ",(0,l.kt)("inlineCode",{parentName:"p"},"Flux/Mono.onErrorResume(GDClientException.class, e -> ...)")," if you don't block. Use ",(0,l.kt)("inlineCode",{parentName:"p"},"GDClientException.getCause()")," to get the original exception."),(0,l.kt)("pre",null,(0,l.kt)("code",{parentName:"pre",className:"language-java"},"try {\n    GDUserProfile user = client.getUserProfile(98006).block();\n} catch (GDClientException e) {\n    // Handle error here\n    Throwable cause = e.getCause(); // the original exception\n}\n")),(0,l.kt)("p",null,"The original exception obtained via ",(0,l.kt)("inlineCode",{parentName:"p"},"getCause()")," can be of any nature, but here are the most frequent ones:"),(0,l.kt)("ul",null,(0,l.kt)("li",{parentName:"ul"},(0,l.kt)("inlineCode",{parentName:"li"},"ActionFailedException"),": emitted when the GD server returns an error code like ",(0,l.kt)("inlineCode",{parentName:"li"},"-1")),(0,l.kt)("li",{parentName:"ul"},(0,l.kt)("inlineCode",{parentName:"li"},"HttpResponseException"),": emitted when the GD server returns an unusual HTTP error code like ",(0,l.kt)("inlineCode",{parentName:"li"},"500 Internal Server Error")),(0,l.kt)("li",{parentName:"ul"},(0,l.kt)("inlineCode",{parentName:"li"},"ResponseDeserializationException"),": emitted when the GD server returns data that could not be decoded into a valid Java object like ",(0,l.kt)("inlineCode",{parentName:"li"},"GDLevel")," or ",(0,l.kt)("inlineCode",{parentName:"li"},"GDUserProfile"),". Generally, this exception also carries its own ",(0,l.kt)("inlineCode",{parentName:"li"},"getCause()")," providing details on why the deserialization failed."),(0,l.kt)("li",{parentName:"ul"},(0,l.kt)("inlineCode",{parentName:"li"},"RetryExhaustedException"),": emitted when there are communication problems with the GD server. By default, the request is retried automatically up to 10 times when the connection fails, but if all attempts are exhausted it will result in this exception being emitted."),(0,l.kt)("li",{parentName:"ul"},(0,l.kt)("inlineCode",{parentName:"li"},"EmissionException"),": emitted when the request could not be queued. It can happen if the request queue of the router is full.")),(0,l.kt)("div",{className:"admonition admonition-caution alert alert--warning"},(0,l.kt)("div",{parentName:"div",className:"admonition-heading"},(0,l.kt)("h5",{parentName:"div"},(0,l.kt)("span",{parentName:"h5",className:"admonition-icon"},(0,l.kt)("svg",{parentName:"span",xmlns:"http://www.w3.org/2000/svg",width:"16",height:"16",viewBox:"0 0 16 16"},(0,l.kt)("path",{parentName:"svg",fillRule:"evenodd",d:"M8.893 1.5c-.183-.31-.52-.5-.887-.5s-.703.19-.886.5L.138 13.499a.98.98 0 0 0 0 1.001c.193.31.53.501.886.501h13.964c.367 0 .704-.19.877-.5a1.03 1.03 0 0 0 .01-1.002L8.893 1.5zm.133 11.497H6.987v-2.003h2.039v2.003zm0-3.004H6.987V5.987h2.039v4.006z"}))),"caution")),(0,l.kt)("div",{parentName:"div",className:"admonition-content"},(0,l.kt)("p",{parentName:"div"},"The ",(0,l.kt)("inlineCode",{parentName:"p"},"IllegalStateException")," that happens when calling a method requiring authentication with a non-authenticated client is thrown immediately upon calling the method; it is not emitted through the ",(0,l.kt)("inlineCode",{parentName:"p"},"Flux"),"/",(0,l.kt)("inlineCode",{parentName:"p"},"Mono")," upon subscription."))))}h.isMDXComponent=!0}}]);