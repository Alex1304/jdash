package jdash.common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.immutables.value.Generated;

/**
 * Immutable implementation of {@link GDSong}.
 * <p>
 * Use the builder to create immutable instances:
 * {@code ImmutableGDSong.builder()}.
 */
@Generated(from = "GDSong", generator = "Immutables")
@SuppressWarnings({"all"})
public final class ImmutableGDSong implements GDSong {
  private final long id;
  private final String songAuthorName;
  private final String songSize;
  private final String songTitle;
  private final String downloadUrl;
  private final boolean isCustom;

  private ImmutableGDSong(
      long id,
      String songAuthorName,
      String songSize,
      String songTitle,
      String downloadUrl,
      boolean isCustom) {
    this.id = id;
    this.songAuthorName = songAuthorName;
    this.songSize = songSize;
    this.songTitle = songTitle;
    this.downloadUrl = downloadUrl;
    this.isCustom = isCustom;
  }

  /**
   * @return The value of the {@code id} attribute
   */
  @Override
  public long id() {
    return id;
  }

  /**
   * @return The value of the {@code songAuthorName} attribute
   */
  @Override
  public String songAuthorName() {
    return songAuthorName;
  }

  /**
   * @return The value of the {@code songSize} attribute
   */
  @Override
  public String songSize() {
    return songSize;
  }

  /**
   * @return The value of the {@code songTitle} attribute
   */
  @Override
  public String songTitle() {
    return songTitle;
  }

  /**
   * @return The value of the {@code downloadUrl} attribute
   */
  @Override
  public String downloadUrl() {
    return downloadUrl;
  }

  /**
   * @return The value of the {@code isCustom} attribute
   */
  @Override
  public boolean isCustom() {
    return isCustom;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDSong#id() id} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDSong withId(long value) {
    if (this.id == value) return this;
    return new ImmutableGDSong(value, this.songAuthorName, this.songSize, this.songTitle, this.downloadUrl, this.isCustom);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDSong#songAuthorName() songAuthorName} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for songAuthorName
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDSong withSongAuthorName(String value) {
    String newValue = Objects.requireNonNull(value, "songAuthorName");
    if (this.songAuthorName.equals(newValue)) return this;
    return new ImmutableGDSong(this.id, newValue, this.songSize, this.songTitle, this.downloadUrl, this.isCustom);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDSong#songSize() songSize} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for songSize
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDSong withSongSize(String value) {
    String newValue = Objects.requireNonNull(value, "songSize");
    if (this.songSize.equals(newValue)) return this;
    return new ImmutableGDSong(this.id, this.songAuthorName, newValue, this.songTitle, this.downloadUrl, this.isCustom);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDSong#songTitle() songTitle} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for songTitle
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDSong withSongTitle(String value) {
    String newValue = Objects.requireNonNull(value, "songTitle");
    if (this.songTitle.equals(newValue)) return this;
    return new ImmutableGDSong(this.id, this.songAuthorName, this.songSize, newValue, this.downloadUrl, this.isCustom);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDSong#downloadUrl() downloadUrl} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for downloadUrl
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDSong withDownloadUrl(String value) {
    String newValue = Objects.requireNonNull(value, "downloadUrl");
    if (this.downloadUrl.equals(newValue)) return this;
    return new ImmutableGDSong(this.id, this.songAuthorName, this.songSize, this.songTitle, newValue, this.isCustom);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDSong#isCustom() isCustom} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for isCustom
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDSong withIsCustom(boolean value) {
    if (this.isCustom == value) return this;
    return new ImmutableGDSong(this.id, this.songAuthorName, this.songSize, this.songTitle, this.downloadUrl, value);
  }

  /**
   * This instance is equal to all instances of {@code ImmutableGDSong} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof ImmutableGDSong
        && equalTo(0, (ImmutableGDSong) another);
  }

  private boolean equalTo(int synthetic, ImmutableGDSong another) {
    return id == another.id
        && songAuthorName.equals(another.songAuthorName)
        && songSize.equals(another.songSize)
        && songTitle.equals(another.songTitle)
        && downloadUrl.equals(another.downloadUrl)
        && isCustom == another.isCustom;
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code songAuthorName}, {@code songSize}, {@code songTitle}, {@code downloadUrl}, {@code isCustom}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Long.hashCode(id);
    h += (h << 5) + songAuthorName.hashCode();
    h += (h << 5) + songSize.hashCode();
    h += (h << 5) + songTitle.hashCode();
    h += (h << 5) + downloadUrl.hashCode();
    h += (h << 5) + Boolean.hashCode(isCustom);
    return h;
  }

  /**
   * Prints the immutable value {@code GDSong} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "GDSong{"
        + "id=" + id
        + ", songAuthorName=" + songAuthorName
        + ", songSize=" + songSize
        + ", songTitle=" + songTitle
        + ", downloadUrl=" + downloadUrl
        + ", isCustom=" + isCustom
        + "}";
  }

  /**
   * Creates an immutable copy of a {@link GDSong} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable GDSong instance
   */
  public static ImmutableGDSong copyOf(GDSong instance) {
    if (instance instanceof ImmutableGDSong) {
      return (ImmutableGDSong) instance;
    }
    return ImmutableGDSong.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link ImmutableGDSong ImmutableGDSong}.
   * <pre>
   * ImmutableGDSong.builder()
   *    .id(long) // required {@link GDSong#id() id}
   *    .songAuthorName(String) // required {@link GDSong#songAuthorName() songAuthorName}
   *    .songSize(String) // required {@link GDSong#songSize() songSize}
   *    .songTitle(String) // required {@link GDSong#songTitle() songTitle}
   *    .downloadUrl(String) // required {@link GDSong#downloadUrl() downloadUrl}
   *    .isCustom(boolean) // required {@link GDSong#isCustom() isCustom}
   *    .build();
   * </pre>
   * @return A new ImmutableGDSong builder
   */
  public static ImmutableGDSong.Builder builder() {
    return new ImmutableGDSong.Builder();
  }

  /**
   * Builds instances of type {@link ImmutableGDSong ImmutableGDSong}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  @Generated(from = "GDSong", generator = "Immutables")
  public static final class Builder {
    private static final long INIT_BIT_ID = 0x1L;
    private static final long INIT_BIT_SONG_AUTHOR_NAME = 0x2L;
    private static final long INIT_BIT_SONG_SIZE = 0x4L;
    private static final long INIT_BIT_SONG_TITLE = 0x8L;
    private static final long INIT_BIT_DOWNLOAD_URL = 0x10L;
    private static final long INIT_BIT_IS_CUSTOM = 0x20L;
    private long initBits = 0x3fL;

    private long id;
    private String songAuthorName;
    private String songSize;
    private String songTitle;
    private String downloadUrl;
    private boolean isCustom;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code GDSong} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(GDSong instance) {
      Objects.requireNonNull(instance, "instance");
      id(instance.id());
      songAuthorName(instance.songAuthorName());
      songSize(instance.songSize());
      songTitle(instance.songTitle());
      downloadUrl(instance.downloadUrl());
      isCustom(instance.isCustom());
      return this;
    }

    /**
     * Initializes the value for the {@link GDSong#id() id} attribute.
     * @param id The value for id 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder id(long id) {
      this.id = id;
      initBits &= ~INIT_BIT_ID;
      return this;
    }

    /**
     * Initializes the value for the {@link GDSong#songAuthorName() songAuthorName} attribute.
     * @param songAuthorName The value for songAuthorName 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder songAuthorName(String songAuthorName) {
      this.songAuthorName = Objects.requireNonNull(songAuthorName, "songAuthorName");
      initBits &= ~INIT_BIT_SONG_AUTHOR_NAME;
      return this;
    }

    /**
     * Initializes the value for the {@link GDSong#songSize() songSize} attribute.
     * @param songSize The value for songSize 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder songSize(String songSize) {
      this.songSize = Objects.requireNonNull(songSize, "songSize");
      initBits &= ~INIT_BIT_SONG_SIZE;
      return this;
    }

    /**
     * Initializes the value for the {@link GDSong#songTitle() songTitle} attribute.
     * @param songTitle The value for songTitle 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder songTitle(String songTitle) {
      this.songTitle = Objects.requireNonNull(songTitle, "songTitle");
      initBits &= ~INIT_BIT_SONG_TITLE;
      return this;
    }

    /**
     * Initializes the value for the {@link GDSong#downloadUrl() downloadUrl} attribute.
     * @param downloadUrl The value for downloadUrl 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder downloadUrl(String downloadUrl) {
      this.downloadUrl = Objects.requireNonNull(downloadUrl, "downloadUrl");
      initBits &= ~INIT_BIT_DOWNLOAD_URL;
      return this;
    }

    /**
     * Initializes the value for the {@link GDSong#isCustom() isCustom} attribute.
     * @param isCustom The value for isCustom 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder isCustom(boolean isCustom) {
      this.isCustom = isCustom;
      initBits &= ~INIT_BIT_IS_CUSTOM;
      return this;
    }

    /**
     * Builds a new {@link ImmutableGDSong ImmutableGDSong}.
     * @return An immutable instance of GDSong
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public ImmutableGDSong build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new ImmutableGDSong(id, songAuthorName, songSize, songTitle, downloadUrl, isCustom);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_SONG_AUTHOR_NAME) != 0) attributes.add("songAuthorName");
      if ((initBits & INIT_BIT_SONG_SIZE) != 0) attributes.add("songSize");
      if ((initBits & INIT_BIT_SONG_TITLE) != 0) attributes.add("songTitle");
      if ((initBits & INIT_BIT_DOWNLOAD_URL) != 0) attributes.add("downloadUrl");
      if ((initBits & INIT_BIT_IS_CUSTOM) != 0) attributes.add("isCustom");
      return "Cannot build GDSong, some of required attributes are not set " + attributes;
    }
  }
}
