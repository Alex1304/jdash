package com.github.alex1304.jdash.util;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import com.github.alex1304.jdash.entity.DemonDifficulty;
import com.github.alex1304.jdash.entity.Difficulty;
import com.github.alex1304.jdash.entity.GDLevel;
import com.github.alex1304.jdash.entity.GDSong;
import com.github.alex1304.jdash.entity.Length;
/**
 * Allows to define search filters for levels. (level에 대한 검색 필터를 정의할 수 있음)
 */
public final class LevelSearchFilters
{
public static enum Toggle
{
STAR, NO_STAR, UNCOMPLETED, ONLY_COMPLETED, FEATURED, ORIGINAL, TWO_PLAYER, COINS, EPIC;
}
private EnumSet<Toggle> toggles;
private EnumSet<Length> lengths;
private EnumSet<Difficulty> difficulties;
private Optional<DemonDifficulty> demon;
private Optional<GDSong> song;
private Collection<? extends GDLevel> completedLevels;
private LevelSearchFilters(EnumSet<Toggle> toggles, EnumSet<Length> lengths, EnumSet<Difficulty> difficulties,
Optional<DemonDifficulty> demon, Optional<GDSong> song)
{
this.toggles = toggles;
this.lengths = lengths;
this.difficulties = difficulties;
this.demon = demon;
this.song = song;
this.completedLevels = Collections.emptySet();
}
/**
* Creates a new instance of search filters.
(검색 필터의 새 인스턴스를 생성하십시오.)
* 
* @return a new instance of {@link LevelSearchFilters}
(새 {@link LevelSearchFilters} 인스턴스 반환)*/
public static LevelSearchFilters create()
{
return new LevelSearchFilters(EnumSet.noneOf(Toggle.class), EnumSet.noneOf(Length.class),
EnumSet.noneOf(Difficulty.class), Optional.empty(), Optional.empty());
}

/**
 * Defines which options are toggled on.
(토클할 옵션 정의)
*  * @param toggles the set of toggles that are on
(@param이 켜져 있는 토글 집합을 토글함)
 * @return this (for method chaining purposes)
(@이 값을 반환하십시오(메서드 체인 용도).)	 */
public LevelSearchFilters withToggles(EnumSet<Toggle> toggles)
{
this.toggles = Objects.requireNonNull(toggles);
return this;
}

/**
* Defines the level lengths to filter on.
(필터링할 수준 길이 정의)
*  * @param lengths the set of lengths
(@param 길이 집합)
 * @return this (for method chaining purposes)
(@이 값을 반환하십시오(메서드 체인 용도).)	*/
public LevelSearchFilters withLengths(EnumSet<Length> lengths)
{
this.lengths = Objects.requireNonNull(lengths);
return this;
}

/**  * Defines the level difficulties to filter on.
 (필터링할 레벨 난이도 정의)
*  * @param difficulties the set of difficulties (@param 난이도)
 * @return this (for method chaining purposes)
(@이 값을 반환하십시오(메서드 체인 용도).) */
public LevelSearchFilters withDifficulties(EnumSet<Difficulty> difficulties)
{
this.difficulties = Objects.requireNonNull(difficulties);
return this;
}

/**
 * Defines the demon filter.
 (demon 필터 정의)
*  * @param demonDiff the demon difficulty to filter on

* @return this (for method chaining purposes) */
public LevelSearchFilters withDemonFilter(DemonDifficulty demonDiff)
{
this.demon = Optional.of(Objects.requireNonNull(demonDiff));
return this;
}

/**
 * Removes any demon filter previously defined by a previous call of
(이전 호출에 정의된 모든 악마 필터 제거)
 * {@link #withDemonFilter(DemonDifficulty)}.
 
* * @return this (for method chaining purposes)
(@이 값을 반환하십시오(메서드 체인 용도).) */
public LevelSearchFilters removeDemonFilter()
{
this.demon = Optional.empty();
return this;
}

/**
 * Defines the song filter.
(노래 필터 정의)
*  * @param isCustom whether to filter on a custom song or a regular one
(사용자 지정 노래로 필터링할지 아니면 일반 노래로 필터링할지 사용자 지정)
 * @param songId   the ID of the song to filter on. If the previous pareameter
	
 *was set to <code>false</code>, then it refers to the index of 
* the level that has the song in game (Stereo Madness is 1,

*Back On Track is 2, and so on) (앞의 파레미터가 <code>false </code>로 설정되어 있다면, 그 곡이 게임 내에 있는 레벨의 지수(Stereo Madness는 1, Back On Track은 2 등)를 가리킨다.)
* @return this (for method chaining purposes)
(@이 값을 반환하십시오(메서드 체인 용도).)*/
public LevelSearchFilters withSongFilter(boolean isCustom, long songId)
{
this.song = Optional.of(isCustom ? GDSong.unknownSong(songId) : Utils.getAudioTrack((int) songId));
return this;
}
/**
 * Removes any song filter previously defined by a previous call of (이전 호출에 의해 정의된 모든 노래 필터 제거)
 * {@link #removeSongFilter()}.
 *  * @return this (for method chaining purposes) (@이 값을 반환하십시오(메서드 체인 용도).) */
public LevelSearchFilters removeSongFilter()
{
this.song = Optional.empty();
return this;
}
/** * Defines the list of completed levels. Only relevant if either (완료된 레벨 목록을 정의한다. 다음 중 하나에 해당하는 경우에만 관련됨)
 * {@link Toggle#ONLY_COMPLETED} or {@link Toggle#UNCOMPLETED} is set on.
 
*  * @param completedLevels a Collection of levels that are considered "completed"
 * @return this (for method chaining purposes)
(@이 값을 반환하십시오(메서드 체인 용도))*/
public LevelSearchFilters withCompletedLevels(Collection<? extends GDLevel> completedLevels)
{
this.completedLevels = Objects.requireNonNull(completedLevels);
return this;
}
public EnumSet<Toggle> getToggles()
{
return toggles;
}
public EnumSet<Length> getLengths()
{
return lengths;
}
public EnumSet<Difficulty> getDifficulties()
{
return difficulties;
}
public Optional<DemonDifficulty> getDemon()
{
return demon;
}
public Optional<GDSong> getSongFilter()
{
return song;
}

/**
* Checks whether the given toggle was set by a previous call of (지정된 토글이 이전 호출에 의해 설정되었는지 확인)
 * {@link #withToggles(EnumSet)} 
*  * @param t the toggle to test
 * @return true if included, false otherwise
(포함된 경우 true 반환, 그렇지 않으면 false) */
public boolean hasToggle(Toggle t)
{
return toggles.contains(t);
}
public Collection<? extends GDLevel> getCompletedLevels()
{
return Collections.unmodifiableCollection(completedLevels);
}

@Override
public int hashCode()
{
return toggles.hashCode() ^ lengths.hashCode() ^ difficulties.hashCode() ^ demon.hashCode() ^ song.hashCode()
^ completedLevels.hashCode();
}

@Override
public boolean equals(Object obj)
{
if (!(obj instanceof LevelSearchFilters))
{
return false;
}
LevelSearchFilters o = (LevelSearchFilters) obj;
return o.toggles.equals(toggles) && o.lengths.equals(lengths) && o.difficulties.equals(difficulties);
}
}