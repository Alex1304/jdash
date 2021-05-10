package jdash.common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jdash.common.DemonDifficulty;
import jdash.common.Difficulty;
import jdash.common.Length;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link GDLevel}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableGDLevel.builder()}.
 */
@Generated(from = "GDLevel", generator = "Immutables")
@SuppressWarnings({"all"})
public final class ImmutableGDLevel implements GDLevel {
  private final long id;
  private final String name;
  private final long creatorId;
  private final String creatorName;
  private final String description;
  private final Difficulty difficulty;
  private final DemonDifficulty demonDifficulty;
  private final int stars;
  private final int featuredScore;
  private final boolean isEpic;
  private final int downloads;
  private final int likes;
  private final Length length;
  private final GDSong song;
  private final int coinCount;
  private final boolean hasCoinsVerified;
  private final int levelVersion;
  private final int gameVersion;
  private final int objectCount;
  private final boolean isDemon;
  private final boolean isAuto;
  private final long originalLevelId;
  private final int requestedStars;

  private ImmutableGDLevel(
      long id,
      String name,
      long creatorId,
      String creatorName,
      String description,
      Difficulty difficulty,
      DemonDifficulty demonDifficulty,
      int stars,
      int featuredScore,
      boolean isEpic,
      int downloads,
      int likes,
      Length length,
      GDSong song,
      int coinCount,
      boolean hasCoinsVerified,
      int levelVersion,
      int gameVersion,
      int objectCount,
      boolean isDemon,
      boolean isAuto,
      long originalLevelId,
      int requestedStars) {
    this.id = id;
    this.name = name;
    this.creatorId = creatorId;
    this.creatorName = creatorName;
    this.description = description;
    this.difficulty = difficulty;
    this.demonDifficulty = demonDifficulty;
    this.stars = stars;
    this.featuredScore = featuredScore;
    this.isEpic = isEpic;
    this.downloads = downloads;
    this.likes = likes;
    this.length = length;
    this.song = song;
    this.coinCount = coinCount;
    this.hasCoinsVerified = hasCoinsVerified;
    this.levelVersion = levelVersion;
    this.gameVersion = gameVersion;
    this.objectCount = objectCount;
    this.isDemon = isDemon;
    this.isAuto = isAuto;
    this.originalLevelId = originalLevelId;
    this.requestedStars = requestedStars;
  }

  /**
   * @return The value of the {@code id} attribute
   */
  @Override
  public long id() {
    return id;
  }

  /**
   * @return The value of the {@code name} attribute
   */
  @Override
  public String name() {
    return name;
  }

  /**
   * @return The value of the {@code creatorId} attribute
   */
  @Override
  public long creatorId() {
    return creatorId;
  }

  /**
   * @return The value of the {@code creatorName} attribute
   */
  @Override
  public String creatorName() {
    return creatorName;
  }

  /**
   * @return The value of the {@code description} attribute
   */
  @Override
  public String description() {
    return description;
  }

  /**
   * @return The value of the {@code difficulty} attribute
   */
  @Override
  public Difficulty difficulty() {
    return difficulty;
  }

  /**
   * @return The value of the {@code demonDifficulty} attribute
   */
  @Override
  public DemonDifficulty demonDifficulty() {
    return demonDifficulty;
  }

  /**
   * @return The value of the {@code stars} attribute
   */
  @Override
  public int stars() {
    return stars;
  }

  /**
   * @return The value of the {@code featuredScore} attribute
   */
  @Override
  public int featuredScore() {
    return featuredScore;
  }

  /**
   * @return The value of the {@code isEpic} attribute
   */
  @Override
  public boolean isEpic() {
    return isEpic;
  }

  /**
   * @return The value of the {@code downloads} attribute
   */
  @Override
  public int downloads() {
    return downloads;
  }

  /**
   * @return The value of the {@code likes} attribute
   */
  @Override
  public int likes() {
    return likes;
  }

  /**
   * @return The value of the {@code length} attribute
   */
  @Override
  public Length length() {
    return length;
  }

  /**
   * @return The value of the {@code song} attribute
   */
  @Override
  public GDSong song() {
    return song;
  }

  /**
   * @return The value of the {@code coinCount} attribute
   */
  @Override
  public int coinCount() {
    return coinCount;
  }

  /**
   * @return The value of the {@code hasCoinsVerified} attribute
   */
  @Override
  public boolean hasCoinsVerified() {
    return hasCoinsVerified;
  }

  /**
   * @return The value of the {@code levelVersion} attribute
   */
  @Override
  public int levelVersion() {
    return levelVersion;
  }

  /**
   * @return The value of the {@code gameVersion} attribute
   */
  @Override
  public int gameVersion() {
    return gameVersion;
  }

  /**
   * @return The value of the {@code objectCount} attribute
   */
  @Override
  public int objectCount() {
    return objectCount;
  }

  /**
   * @return The value of the {@code isDemon} attribute
   */
  @Override
  public boolean isDemon() {
    return isDemon;
  }

  /**
   * @return The value of the {@code isAuto} attribute
   */
  @Override
  public boolean isAuto() {
    return isAuto;
  }

  /**
   * @return The value of the {@code originalLevelId} attribute
   */
  @Override
  public long originalLevelId() {
    return originalLevelId;
  }

  /**
   * @return The value of the {@code requestedStars} attribute
   */
  @Override
  public int requestedStars() {
    return requestedStars;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#id() id} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withId(long value) {
    if (this.id == value) return this;
    return new ImmutableGDLevel(
        value,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#name() name} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for name
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withName(String value) {
    String newValue = Objects.requireNonNull(value, "name");
    if (this.name.equals(newValue)) return this;
    return new ImmutableGDLevel(
        this.id,
        newValue,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#creatorId() creatorId} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for creatorId
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withCreatorId(long value) {
    if (this.creatorId == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        value,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#creatorName() creatorName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for creatorName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withCreatorName(String value) {
    String newValue = Objects.requireNonNull(value, "creatorName");
    if (this.creatorName.equals(newValue)) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        newValue,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#description() description} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for description
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withDescription(String value) {
    String newValue = Objects.requireNonNull(value, "description");
    if (this.description.equals(newValue)) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        newValue,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#difficulty() difficulty} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for difficulty
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withDifficulty(Difficulty value) {
    Difficulty newValue = Objects.requireNonNull(value, "difficulty");
    if (this.difficulty == newValue) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        newValue,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#demonDifficulty() demonDifficulty} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for demonDifficulty
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withDemonDifficulty(DemonDifficulty value) {
    DemonDifficulty newValue = Objects.requireNonNull(value, "demonDifficulty");
    if (this.demonDifficulty == newValue) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        newValue,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#stars() stars} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for stars
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withStars(int value) {
    if (this.stars == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        value,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#featuredScore() featuredScore} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for featuredScore
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withFeaturedScore(int value) {
    if (this.featuredScore == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        value,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#isEpic() isEpic} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isEpic
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withIsEpic(boolean value) {
    if (this.isEpic == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        value,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#downloads() downloads} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for downloads
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withDownloads(int value) {
    if (this.downloads == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        value,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#likes() likes} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for likes
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withLikes(int value) {
    if (this.likes == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        value,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#length() length} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for length
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withLength(Length value) {
    Length newValue = Objects.requireNonNull(value, "length");
    if (this.length == newValue) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        newValue,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#song() song} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for song
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withSong(GDSong value) {
    if (this.song == value) return this;
    GDSong newValue = Objects.requireNonNull(value, "song");
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        newValue,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#coinCount() coinCount} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for coinCount
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withCoinCount(int value) {
    if (this.coinCount == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        value,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#hasCoinsVerified() hasCoinsVerified} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for hasCoinsVerified
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withHasCoinsVerified(boolean value) {
    if (this.hasCoinsVerified == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        value,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#levelVersion() levelVersion} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for levelVersion
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withLevelVersion(int value) {
    if (this.levelVersion == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        value,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#gameVersion() gameVersion} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for gameVersion
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withGameVersion(int value) {
    if (this.gameVersion == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        value,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#objectCount() objectCount} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for objectCount
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withObjectCount(int value) {
    if (this.objectCount == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        value,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#isDemon() isDemon} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isDemon
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withIsDemon(boolean value) {
    if (this.isDemon == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        value,
        this.isAuto,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#isAuto() isAuto} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isAuto
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withIsAuto(boolean value) {
    if (this.isAuto == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        value,
        this.originalLevelId,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#originalLevelId() originalLevelId} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for originalLevelId
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withOriginalLevelId(long value) {
    if (this.originalLevelId == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        value,
        this.requestedStars);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDLevel#requestedStars() requestedStars} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for requestedStars
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDLevel withRequestedStars(int value) {
    if (this.requestedStars == value) return this;
    return new ImmutableGDLevel(
        this.id,
        this.name,
        this.creatorId,
        this.creatorName,
        this.description,
        this.difficulty,
        this.demonDifficulty,
        this.stars,
        this.featuredScore,
        this.isEpic,
        this.downloads,
        this.likes,
        this.length,
        this.song,
        this.coinCount,
        this.hasCoinsVerified,
        this.levelVersion,
        this.gameVersion,
        this.objectCount,
        this.isDemon,
        this.isAuto,
        this.originalLevelId,
        value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableGDLevel} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableGDLevel
        && equalTo(0, (ImmutableGDLevel) another);
  }

  private boolean equalTo(int synthetic, ImmutableGDLevel another) {
    return id == another.id
        && name.equals(another.name)
        && creatorId == another.creatorId
        && creatorName.equals(another.creatorName)
        && description.equals(another.description)
        && difficulty.equals(another.difficulty)
        && demonDifficulty.equals(another.demonDifficulty)
        && stars == another.stars
        && featuredScore == another.featuredScore
        && isEpic == another.isEpic
        && downloads == another.downloads
        && likes == another.likes
        && length.equals(another.length)
        && song.equals(another.song)
        && coinCount == another.coinCount
        && hasCoinsVerified == another.hasCoinsVerified
        && levelVersion == another.levelVersion
        && gameVersion == another.gameVersion
        && objectCount == another.objectCount
        && isDemon == another.isDemon
        && isAuto == another.isAuto
        && originalLevelId == another.originalLevelId
        && requestedStars == another.requestedStars;
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code name}, {@code creatorId}, {@code creatorName}, {@code description}, {@code difficulty}, {@code demonDifficulty}, {@code stars}, {@code featuredScore}, {@code isEpic}, {@code downloads}, {@code likes}, {@code length}, {@code song}, {@code coinCount}, {@code hasCoinsVerified}, {@code levelVersion}, {@code gameVersion}, {@code objectCount}, {@code isDemon}, {@code isAuto}, {@code originalLevelId}, {@code requestedStars}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Long.hashCode(id);
    h += (h << 5) + name.hashCode();
    h += (h << 5) + Long.hashCode(creatorId);
    h += (h << 5) + creatorName.hashCode();
    h += (h << 5) + description.hashCode();
    h += (h << 5) + difficulty.hashCode();
    h += (h << 5) + demonDifficulty.hashCode();
    h += (h << 5) + stars;
    h += (h << 5) + featuredScore;
    h += (h << 5) + Boolean.hashCode(isEpic);
    h += (h << 5) + downloads;
    h += (h << 5) + likes;
    h += (h << 5) + length.hashCode();
    h += (h << 5) + song.hashCode();
    h += (h << 5) + coinCount;
    h += (h << 5) + Boolean.hashCode(hasCoinsVerified);
    h += (h << 5) + levelVersion;
    h += (h << 5) + gameVersion;
    h += (h << 5) + objectCount;
    h += (h << 5) + Boolean.hashCode(isDemon);
    h += (h << 5) + Boolean.hashCode(isAuto);
    h += (h << 5) + Long.hashCode(originalLevelId);
    h += (h << 5) + requestedStars;
    return h;
  }

  /**
   * Prints the immutable value {@code GDLevel} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "GDLevel{"
        + "id=" + id
        + ", name=" + name
        + ", creatorId=" + creatorId
        + ", creatorName=" + creatorName
        + ", description=" + description
        + ", difficulty=" + difficulty
        + ", demonDifficulty=" + demonDifficulty
        + ", stars=" + stars
        + ", featuredScore=" + featuredScore
        + ", isEpic=" + isEpic
        + ", downloads=" + downloads
        + ", likes=" + likes
        + ", length=" + length
        + ", song=" + song
        + ", coinCount=" + coinCount
        + ", hasCoinsVerified=" + hasCoinsVerified
        + ", levelVersion=" + levelVersion
        + ", gameVersion=" + gameVersion
        + ", objectCount=" + objectCount
        + ", isDemon=" + isDemon
        + ", isAuto=" + isAuto
        + ", originalLevelId=" + originalLevelId
        + ", requestedStars=" + requestedStars
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link GDLevel} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable GDLevel instance
   */
  public static ImmutableGDLevel copyOf(GDLevel instance) {
    if (instance instanceof ImmutableGDLevel) {
      return (ImmutableGDLevel) instance;
    }
    return ImmutableGDLevel.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableGDLevel ImmutableGDLevel}.
   * <pre>
   * ImmutableGDLevel.builder()
   *    .id(long) // required {@link GDLevel#id() id}
   *    .name(String) // required {@link GDLevel#name() name}
   *    .creatorId(long) // required {@link GDLevel#creatorId() creatorId}
   *    .creatorName(String) // required {@link GDLevel#creatorName() creatorName}
   *    .description(String) // required {@link GDLevel#description() description}
   *    .difficulty(jdash.common.Difficulty) // required {@link GDLevel#difficulty() difficulty}
   *    .demonDifficulty(jdash.common.DemonDifficulty) // required {@link GDLevel#demonDifficulty() demonDifficulty}
   *    .stars(int) // required {@link GDLevel#stars() stars}
   *    .featuredScore(int) // required {@link GDLevel#featuredScore() featuredScore}
   *    .isEpic(boolean) // required {@link GDLevel#isEpic() isEpic}
   *    .downloads(int) // required {@link GDLevel#downloads() downloads}
   *    .likes(int) // required {@link GDLevel#likes() likes}
   *    .length(jdash.common.Length) // required {@link GDLevel#length() length}
   *    .song(jdash.common.entity.GDSong) // required {@link GDLevel#song() song}
   *    .coinCount(int) // required {@link GDLevel#coinCount() coinCount}
   *    .hasCoinsVerified(boolean) // required {@link GDLevel#hasCoinsVerified() hasCoinsVerified}
   *    .levelVersion(int) // required {@link GDLevel#levelVersion() levelVersion}
   *    .gameVersion(int) // required {@link GDLevel#gameVersion() gameVersion}
   *    .objectCount(int) // required {@link GDLevel#objectCount() objectCount}
   *    .isDemon(boolean) // required {@link GDLevel#isDemon() isDemon}
   *    .isAuto(boolean) // required {@link GDLevel#isAuto() isAuto}
   *    .originalLevelId(long) // required {@link GDLevel#originalLevelId() originalLevelId}
   *    .requestedStars(int) // required {@link GDLevel#requestedStars() requestedStars}
   *    .build();
   * </pre>
   * @return A new ImmutableGDLevel builder
   */
  public static ImmutableGDLevel.Builder builder() {
    return new ImmutableGDLevel.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableGDLevel ImmutableGDLevel}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "GDLevel", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long INIT_BIT_NAME = 0x2L;
    private static final long INIT_BIT_CREATOR_ID = 0x4L;
    private static final long INIT_BIT_CREATOR_NAME = 0x8L;
    private static final long INIT_BIT_DESCRIPTION = 0x10L;
    private static final long INIT_BIT_DIFFICULTY = 0x20L;
    private static final long INIT_BIT_DEMON_DIFFICULTY = 0x40L;
    private static final long INIT_BIT_STARS = 0x80L;
    private static final long INIT_BIT_FEATURED_SCORE = 0x100L;
    private static final long INIT_BIT_IS_EPIC = 0x200L;
    private static final long INIT_BIT_DOWNLOADS = 0x400L;
    private static final long INIT_BIT_LIKES = 0x800L;
    private static final long INIT_BIT_LENGTH = 0x1000L;
    private static final long INIT_BIT_SONG = 0x2000L;
    private static final long INIT_BIT_COIN_COUNT = 0x4000L;
    private static final long INIT_BIT_HAS_COINS_VERIFIED = 0x8000L;
    private static final long INIT_BIT_LEVEL_VERSION = 0x10000L;
    private static final long INIT_BIT_GAME_VERSION = 0x20000L;
    private static final long INIT_BIT_OBJECT_COUNT = 0x40000L;
    private static final long INIT_BIT_IS_DEMON = 0x80000L;
    private static final long INIT_BIT_IS_AUTO = 0x100000L;
    private static final long INIT_BIT_ORIGINAL_LEVEL_ID = 0x200000L;
    private static final long INIT_BIT_REQUESTED_STARS = 0x400000L;
    private long initBits = 0x7fffffL;

    private long id;
    private String name;
    private long creatorId;
    private String creatorName;
    private String description;
    private Difficulty difficulty;
    private DemonDifficulty demonDifficulty;
    private int stars;
    private int featuredScore;
    private boolean isEpic;
    private int downloads;
    private int likes;
    private Length length;
    private GDSong song;
    private int coinCount;
    private boolean hasCoinsVerified;
    private int levelVersion;
    private int gameVersion;
    private int objectCount;
    private boolean isDemon;
    private boolean isAuto;
    private long originalLevelId;
    private int requestedStars;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code GDLevel} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(GDLevel instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      name(instance.name());
      creatorId(instance.creatorId());
      creatorName(instance.creatorName());
      description(instance.description());
      difficulty(instance.difficulty());
      demonDifficulty(instance.demonDifficulty());
      stars(instance.stars());
      featuredScore(instance.featuredScore());
      isEpic(instance.isEpic());
      downloads(instance.downloads());
      likes(instance.likes());
      length(instance.length());
      song(instance.song());
      coinCount(instance.coinCount());
      hasCoinsVerified(instance.hasCoinsVerified());
      levelVersion(instance.levelVersion());
      gameVersion(instance.gameVersion());
      objectCount(instance.objectCount());
      isDemon(instance.isDemon());
      isAuto(instance.isAuto());
      originalLevelId(instance.originalLevelId());
      requestedStars(instance.requestedStars());
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder id(long id) {
      this.id = id;
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#name() name} attribute.
     * @param name The value for name 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder name(String name) {
      this.name = Objects.requireNonNull(name, "name");
      initBits &= ~INIT_BIT_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#creatorId() creatorId} attribute.
     * @param creatorId The value for creatorId 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder creatorId(long creatorId) {
      this.creatorId = creatorId;
      initBits &= ~INIT_BIT_CREATOR_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#creatorName() creatorName} attribute.
     * @param creatorName The value for creatorName 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder creatorName(String creatorName) {
      this.creatorName = Objects.requireNonNull(creatorName, "creatorName");
      initBits &= ~INIT_BIT_CREATOR_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#description() description} attribute.
     * @param description The value for description 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder description(String description) {
      this.description = Objects.requireNonNull(description, "description");
      initBits &= ~INIT_BIT_DESCRIPTION;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#difficulty() difficulty} attribute.
     * @param difficulty The value for difficulty 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder difficulty(Difficulty difficulty) {
      this.difficulty = Objects.requireNonNull(difficulty, "difficulty");
      initBits &= ~INIT_BIT_DIFFICULTY;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#demonDifficulty() demonDifficulty} attribute.
     * @param demonDifficulty The value for demonDifficulty 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder demonDifficulty(DemonDifficulty demonDifficulty) {
      this.demonDifficulty = Objects.requireNonNull(demonDifficulty, "demonDifficulty");
      initBits &= ~INIT_BIT_DEMON_DIFFICULTY;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#stars() stars} attribute.
     * @param stars The value for stars 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder stars(int stars) {
      this.stars = stars;
      initBits &= ~INIT_BIT_STARS;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#featuredScore() featuredScore} attribute.
     * @param featuredScore The value for featuredScore 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder featuredScore(int featuredScore) {
      this.featuredScore = featuredScore;
      initBits &= ~INIT_BIT_FEATURED_SCORE;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#isEpic() isEpic} attribute.
     * @param isEpic The value for isEpic 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder isEpic(boolean isEpic) {
      this.isEpic = isEpic;
      initBits &= ~INIT_BIT_IS_EPIC;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#downloads() downloads} attribute.
     * @param downloads The value for downloads 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder downloads(int downloads) {
      this.downloads = downloads;
      initBits &= ~INIT_BIT_DOWNLOADS;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#likes() likes} attribute.
     * @param likes The value for likes 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder likes(int likes) {
      this.likes = likes;
      initBits &= ~INIT_BIT_LIKES;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#length() length} attribute.
     * @param length The value for length 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder length(Length length) {
      this.length = Objects.requireNonNull(length, "length");
      initBits &= ~INIT_BIT_LENGTH;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#song() song} attribute.
     * @param song The value for song 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder song(GDSong song) {
      this.song = Objects.requireNonNull(song, "song");
      initBits &= ~INIT_BIT_SONG;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#coinCount() coinCount} attribute.
     * @param coinCount The value for coinCount 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder coinCount(int coinCount) {
      this.coinCount = coinCount;
      initBits &= ~INIT_BIT_COIN_COUNT;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#hasCoinsVerified() hasCoinsVerified} attribute.
     * @param hasCoinsVerified The value for hasCoinsVerified 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder hasCoinsVerified(boolean hasCoinsVerified) {
      this.hasCoinsVerified = hasCoinsVerified;
      initBits &= ~INIT_BIT_HAS_COINS_VERIFIED;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#levelVersion() levelVersion} attribute.
     * @param levelVersion The value for levelVersion 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder levelVersion(int levelVersion) {
      this.levelVersion = levelVersion;
      initBits &= ~INIT_BIT_LEVEL_VERSION;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#gameVersion() gameVersion} attribute.
     * @param gameVersion The value for gameVersion 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder gameVersion(int gameVersion) {
      this.gameVersion = gameVersion;
      initBits &= ~INIT_BIT_GAME_VERSION;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#objectCount() objectCount} attribute.
     * @param objectCount The value for objectCount 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder objectCount(int objectCount) {
      this.objectCount = objectCount;
      initBits &= ~INIT_BIT_OBJECT_COUNT;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#isDemon() isDemon} attribute.
     * @param isDemon The value for isDemon 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder isDemon(boolean isDemon) {
      this.isDemon = isDemon;
      initBits &= ~INIT_BIT_IS_DEMON;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#isAuto() isAuto} attribute.
     * @param isAuto The value for isAuto 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder isAuto(boolean isAuto) {
      this.isAuto = isAuto;
      initBits &= ~INIT_BIT_IS_AUTO;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#originalLevelId() originalLevelId} attribute.
     * @param originalLevelId The value for originalLevelId 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder originalLevelId(long originalLevelId) {
      this.originalLevelId = originalLevelId;
      initBits &= ~INIT_BIT_ORIGINAL_LEVEL_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link GDLevel#requestedStars() requestedStars} attribute.
     * @param requestedStars The value for requestedStars 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder requestedStars(int requestedStars) {
      this.requestedStars = requestedStars;
      initBits &= ~INIT_BIT_REQUESTED_STARS;
      return this;
    }

    /**
     * Builds a new {@link ImmutableGDLevel ImmutableGDLevel}.
     * @return An immutable instance of GDLevel
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableGDLevel build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableGDLevel(
          id,
          name,
          creatorId,
          creatorName,
          description,
          difficulty,
          demonDifficulty,
          stars,
          featuredScore,
          isEpic,
          downloads,
          likes,
          length,
          song,
          coinCount,
          hasCoinsVerified,
          levelVersion,
          gameVersion,
          objectCount,
          isDemon,
          isAuto,
          originalLevelId,
          requestedStars);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_NAME) != 0) attributes.add("name");
      if ((initBits & INIT_BIT_CREATOR_ID) != 0) attributes.add("creatorId");
      if ((initBits & INIT_BIT_CREATOR_NAME) != 0) attributes.add("creatorName");
      if ((initBits & INIT_BIT_DESCRIPTION) != 0) attributes.add("description");
      if ((initBits & INIT_BIT_DIFFICULTY) != 0) attributes.add("difficulty");
      if ((initBits & INIT_BIT_DEMON_DIFFICULTY) != 0) attributes.add("demonDifficulty");
      if ((initBits & INIT_BIT_STARS) != 0) attributes.add("stars");
      if ((initBits & INIT_BIT_FEATURED_SCORE) != 0) attributes.add("featuredScore");
      if ((initBits & INIT_BIT_IS_EPIC) != 0) attributes.add("isEpic");
      if ((initBits & INIT_BIT_DOWNLOADS) != 0) attributes.add("downloads");
      if ((initBits & INIT_BIT_LIKES) != 0) attributes.add("likes");
      if ((initBits & INIT_BIT_LENGTH) != 0) attributes.add("length");
      if ((initBits & INIT_BIT_SONG) != 0) attributes.add("song");
      if ((initBits & INIT_BIT_COIN_COUNT) != 0) attributes.add("coinCount");
      if ((initBits & INIT_BIT_HAS_COINS_VERIFIED) != 0) attributes.add("hasCoinsVerified");
      if ((initBits & INIT_BIT_LEVEL_VERSION) != 0) attributes.add("levelVersion");
      if ((initBits & INIT_BIT_GAME_VERSION) != 0) attributes.add("gameVersion");
      if ((initBits & INIT_BIT_OBJECT_COUNT) != 0) attributes.add("objectCount");
      if ((initBits & INIT_BIT_IS_DEMON) != 0) attributes.add("isDemon");
      if ((initBits & INIT_BIT_IS_AUTO) != 0) attributes.add("isAuto");
      if ((initBits & INIT_BIT_ORIGINAL_LEVEL_ID) != 0) attributes.add("originalLevelId");
      if ((initBits & INIT_BIT_REQUESTED_STARS) != 0) attributes.add("requestedStars");
      return "Cannot build GDLevel, some of required attributes are not set " + attributes;
    }
  }
}
