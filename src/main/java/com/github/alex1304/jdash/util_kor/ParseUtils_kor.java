package com.github.alex1304.jdash.util;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.github.alex1304.jdash.entity.GDSong;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;
public final class ParseUtils
{
private ParseUtils()
{}

/**
* Transforms a string into a map. The string must be in a specific format for (문자열을 지도로 변환한다. 문자열은 다음에 대한 특정 형식이어야 함)
 * this method to work. For example, a string formatted as
 * <code>"1:abc:2:def:3:xyz"</code> will return the following map:
(이 방법은 효과가 있다. 예를 들어 다음과 같이 포맷된 문자열)
* 
 * <pre>
 * 1 =&gt; abc
 * 2 =&gt; def
	 * 3 =&gt; xyz
* </pre>
(<코드>1:abc:2:def:3:xyz"</code>는 다음 지도를 반환한다.)
* 
 * @param str   the string to convert to map

 * @param regex the regex representing the separator between values in the
 *string. In the example above, it would be ":"

 * @return a Map of Integer, String

 * @throws NumberFormatException if a key of the map couldn't be converted to

* int. Example, the string

*<code>1:abc:A:xyz</code> would throw this
 
* exception.
 */
public static Map<Integer, String> splitToMap(String str, String regex)
{

Map<Integer, String> map = new HashMap<>();
String[] splitted = str.split(regex);

for (int i = 0; i < splitted.length - 1; i += 2)

map.put(Integer.parseInt(splitted[i]), splitted[i + 1]);

return map.isEmpty() ? Collections.emptyMap() : map;

}

/**
 * Parses the String representing level creators into a Map that associates the

 * creator ID with their name
 
* 
 * @param creatorsInfoRD the String representing the creators
	 
* @return a Map of Long, String
(레벨 크리에이터를 나타내는 문자열을 레벨 크리에이터를 연결하는 맵으로 구문 분석) */
public static Map<Long, String> structureCreatorsInfo(String creatorsInfoRD)
{
if (creatorsInfoRD.isEmpty())

return Collections.emptyMap();
String[] arrayCreatorsRD = creatorsInfoRD.split("\\|");
Map<Long, String> structuredCreatorsInfo = new HashMap<>();
for (String creatorRD : arrayCreatorsRD)
{
structuredCreatorsInfo.put(Long.parseLong(creatorRD.split(":")[0]), creatorRD.split(":")[1]);
}

return structuredCreatorsInfo;
}
/**
 * Parses the String representing level songs into a Map that associates the

 * song ID with their title

* 
 * @param songsInfoRD the String representing the songs

* @return a Map of Long, String
(수준 곡을 나타내는 문자열을 수준과 연관시키는 맵으로 구문 분석) */
public static Map<Long, GDSong> structureSongsInfo(String songsInfoRD)
{
if (songsInfoRD.isEmpty())
return Collections.emptyMap();
String[] arraySongsRD = songsInfoRD.split("~:~");

Map<Long, GDSong> result = new HashMap<>();
for (String songRD : arraySongsRD)
{
Map<Integer, String> songMap = splitToMap(songRD, "~\\|~");
long songID = Long.parseLong(Utils.defaultStringIfEmptyOrNull(songMap.get(Indexes.SONG_ID), "0"));
String songTitle = Utils.defaultStringIfEmptyOrNull(songMap.get(Indexes.SONG_TITLE), "");
String songAuthor = Utils.defaultStringIfEmptyOrNull(songMap.get(Indexes.SONG_AUTHOR), "");
String songSize = Utils.defaultStringIfEmptyOrNull(songMap.get(Indexes.SONG_SIZE), "");
String songURL = Utils.urlDecode(Utils.defaultStringIfEmptyOrNull(songMap.get(Indexes.SONG_URL), ""));
result.put(songID, new GDSong(songID, songAuthor, songSize, songTitle, songURL, true));
}
return result;
}

/**
 * Extracts a triplet (a set of exactly 3 integer values) from the given raw

 * data string. The string is expected to match the regex:

* 
 * <pre>
* [0-9]+:[0-9]+:[0-9]+
 * </pre>
	 
*  * @param raw the String to parse

 * @return an int array of length 3 containing the three values of the triplet. (주어진 원시 값에서 트리플릿(정확히 3개의 정수 값의 집합)을 추출한다.
데이터 문자열. 문자열이 regex와 일치해야 함. 삼중의 세 값이 들어 있는 길이 3의 인트 어레이를 반환한다 )
*/
public static Tuple3<Integer, Integer, Integer> extractPageInfo(String raw)
{
if (!raw.matches("[0-9]*:[0-9]*:[0-9]*"))
{
throw new IllegalArgumentException("Malformed page info string");
}
String[] pageInfo = raw.split(":");
return Tuples.of(Integer.parseInt(Utils.defaultStringIfEmptyOrNull(pageInfo[0], "0")),Integer.parseInt(Utils.defaultStringIfEmptyOrNull(pageInfo[1], "0")),Integer.parseInt(Utils.defaultStringIfEmptyOrNull(pageInfo[2], "0")));
}
}