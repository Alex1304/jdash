# JDash

[![Maven Central](https://img.shields.io/maven-central/v/com.github.alex1304/jdash.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.alex1304%22%20AND%20a:%22jdash%22) [![Documentation Status](https://readthedocs.org/projects/jdash/badge/?version=latest)](https://jdash.readthedocs.io/en/latest/?badge=latest)


A Java library for Geometry Dash. (Geometry Dash를 위한 Java라이브러리.)

# Features (특징)

- Provides a high-level HTTP client to fetch data from Geometry Dash servers  
  (Geometry Dash 서버에서 데이터를 수집할 수 있는 고급 HTTP 클라이언트 제공)
- Supports authentication with Geometry Dash account username / password  
  (Geometry Dash 계정 사용자 이름/암호로 인증 지원)
- Allows for both blocking and reactive/non-blocking programming styles  
  (차단 및 활성/비 차단 프로그래밍 스타일 모두 허용)
- Supports request caching for better performances  
  (성능 향상을 위한 요청 캐싱 지원)
- Provides a convenient way to generate player icons.  
  (플레이어 아이콘을 생성하는 연결 방식 제공)

# Get started (시작)

In a Maven project, add the following dependency to your `pom.xml`:  
(Maven 프로젝트에서`pom.xml`에 다음 종속성을 추가하십시오.)

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

Or download the latest version at the [releases](https://github.com/Alex1304/jdash/releases) page, and add all required JAR files into the build path of your Java project.  
(또는 릴리스 페이지에서 최신 버전을 다운로드 하고 Java 프로젝트의 빌드 경로에 필요한 모든 JAR 파일을 추가하십시오.)

# How to use it ? (어떻게 이용하는가?)  

There are many ways to use the library. Here are a few examples:  
(이 library를 이용하는 방법은 다양하다. 몇 가지 예는 다음과 같다.)

## Anonymous client, blocking style (익명 클라이언트, 차단 스타일)  

```Java
import com.github.alex1304.jdash.client.AnonymousGDClient;
import com.github.alex1304.jdash.client.GDClientBuilder;
import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.entity.GDUser;

public class TestMain {

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

## Anonymous client, non-blocking style (익명 클라이언트, 비 차단 스타일)  

```Java
import com.github.alex1304.jdash.client.AnonymousGDClient;
import com.github.alex1304.jdash.client.GDClientBuilder;

public class TestMain {

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

## Authenticated client (인증된 클라이언트)

```Java
import com.github.alex1304.jdash.client.AuthenticatedGDClient;
import com.github.alex1304.jdash.client.GDClientBuilder;
import com.github.alex1304.jdash.entity.GDMessage;
import com.github.alex1304.jdash.exception.GDLoginFailedException;
import com.github.alex1304.jdash.util.GDPaginator;

public class TestMain {

	public static void main(String[] args) throws Exception {
		// Build an anonymous client
		try {
			AuthenticatedGDClient client = GDClientBuilder.create().buildAuthenticated("MyUsername", "MyP@ssw0rd");
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
(전체 문서를 보고싶다면 here를 누르세요.)  

# License (라이선스)

MIT

# Contribute (기여)

Issues and Pull requests are more than welcome ! There is no guide that tells how to structure them, but if you explain clearly what you did in your pull request, we will be able to dicuss about it and getting it eventually merged. This is the same for issues, feel free to submit them as long as they are clear.  
(Issues와 Pull requests들은 환영한다! 그것들을 어떻게 구조화해야 하는지 말해주는 가이드가 없지만, 만약 너가 너의 Pull requests에서 무엇을 했는지 명화하게 설명해준다면, 우리는 그것에 대해 상의할 것이고 그것을 결국 합칠 수 있을 것이다. 이것은 issues에 관해서도 마찬가지다, 그것들이 명확하다면 그것들을 자유롭게 제출해줘.)

# Contact (연락)

E-mail: mirandaa1304@gmail.com

Discord: Alex1304#9704

Twitter: @gd_alex1304
