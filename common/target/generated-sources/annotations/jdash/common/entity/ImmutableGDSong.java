package jdash.common.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
  private final String title;
  private final String artist;
  private final String size;
  private final String downloadUrl;

  private ImmutableGDSong(
      long id,
      String title,
      String artist,
      String size,
      String downloadUrl) {
    this.id = id;
    this.title = title;
    this.artist = artist;
    this.size = size;
    this.downloadUrl = downloadUrl;
  }

  /**
   * The ID of the song. For a custom song, this is the Newgrounds ID. For an official song, this is its in-game ID.
   * @return a long
   */
  @Override
  public long id() {
    return id;
  }

  /**
   * The title of the song.
   * @return a string
   */
  @Override
  public String title() {
    return title;
  }

  /**
   * The display name of the artist of the song.
   * @return a string
   */
  @Override
  public String artist() {
    return artist;
  }

  /**
   * A string that indicates the size of the song. The structure of the string is not guaranteed. Only applicable for
   * custom songs.
   * @return an {@link Optional} containing a string if present
   */
  @Override
  public Optional<String> size() {
    return Optional.ofNullable(size);
  }

  /**
   * The download URL of the song. Only applicable for custom songs.
   * @return an {@link Optional} containing a string if present
   */
  @Override
  public Optional<String> downloadUrl() {
    return Optional.ofNullable(downloadUrl);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDSong#id() id} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for id
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDSong withId(long value) {
    if (this.id == value) return this;
    return new ImmutableGDSong(value, this.title, this.artist, this.size, this.downloadUrl);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDSong#title() title} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for title
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDSong withTitle(String value) {
    String newValue = Objects.requireNonNull(value, "title");
    if (this.title.equals(newValue)) return this;
    return new ImmutableGDSong(this.id, newValue, this.artist, this.size, this.downloadUrl);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link GDSong#artist() artist} attribute.
   * An equals check used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for artist
   * @return A modified copy of the {@code this} object
   */
  public final ImmutableGDSong withArtist(String value) {
    String newValue = Objects.requireNonNull(value, "artist");
    if (this.artist.equals(newValue)) return this;
    return new ImmutableGDSong(this.id, this.title, newValue, this.size, this.downloadUrl);
  }

  /**
   * Copy the current immutable object by setting a <i>present</i> value for the optional {@link GDSong#size() size} attribute.
   * @param value The value for size
   * @return A modified copy of {@code this} object
   */
  public final ImmutableGDSong withSize(String value) {
    String newValue = Objects.requireNonNull(value, "size");
    if (Objects.equals(this.size, newValue)) return this;
    return new ImmutableGDSong(this.id, this.title, this.artist, newValue, this.downloadUrl);
  }

  /**
   * Copy the current immutable object by setting an optional value for the {@link GDSong#size() size} attribute.
   * An equality check is used on inner nullable value to prevent copying of the same value by returning {@code this}.
   * @param optional A value for size
   * @return A modified copy of {@code this} object
   */
  public final ImmutableGDSong withSize(Optional<String> optional) {
    String value = optional.orElse(null);
    if (Objects.equals(this.size, value)) return this;
    return new ImmutableGDSong(this.id, this.title, this.artist, value, this.downloadUrl);
  }

  /**
   * Copy the current immutable object by setting a <i>present</i> value for the optional {@link GDSong#downloadUrl() downloadUrl} attribute.
   * @param value The value for downloadUrl
   * @return A modified copy of {@code this} object
   */
  public final ImmutableGDSong withDownloadUrl(String value) {
    String newValue = Objects.requireNonNull(value, "downloadUrl");
    if (Objects.equals(this.downloadUrl, newValue)) return this;
    return new ImmutableGDSong(this.id, this.title, this.artist, this.size, newValue);
  }

  /**
   * Copy the current immutable object by setting an optional value for the {@link GDSong#downloadUrl() downloadUrl} attribute.
   * An equality check is used on inner nullable value to prevent copying of the same value by returning {@code this}.
   * @param optional A value for downloadUrl
   * @return A modified copy of {@code this} object
   */
  public final ImmutableGDSong withDownloadUrl(Optional<String> optional) {
    String value = optional.orElse(null);
    if (Objects.equals(this.downloadUrl, value)) return this;
    return new ImmutableGDSong(this.id, this.title, this.artist, this.size, value);
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
        && title.equals(another.title)
        && artist.equals(another.artist)
        && Objects.equals(size, another.size)
        && Objects.equals(downloadUrl, another.downloadUrl);
  }

  /**
   * Computes a hash code from attributes: {@code id}, {@code title}, {@code artist}, {@code size}, {@code downloadUrl}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Long.hashCode(id);
    h += (h << 5) + title.hashCode();
    h += (h << 5) + artist.hashCode();
    h += (h << 5) + Objects.hashCode(size);
    h += (h << 5) + Objects.hashCode(downloadUrl);
    return h;
  }

  /**
   * Prints the immutable value {@code GDSong} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("GDSong{");
    builder.append("id=").append(id);
    builder.append(", ");
    builder.append("title=").append(title);
    builder.append(", ");
    builder.append("artist=").append(artist);
    if (size != null) {
      builder.append(", ");
      builder.append("size=").append(size);
    }
    if (downloadUrl != null) {
      builder.append(", ");
      builder.append("downloadUrl=").append(downloadUrl);
    }
    return builder.append("}").toString();
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
   *    .title(String) // required {@link GDSong#title() title}
   *    .artist(String) // required {@link GDSong#artist() artist}
   *    .size(String) // optional {@link GDSong#size() size}
   *    .downloadUrl(String) // optional {@link GDSong#downloadUrl() downloadUrl}
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
    private static final long INIT_BIT_TITLE = 0x2L;
    private static final long INIT_BIT_ARTIST = 0x4L;
    private long initBits = 0x7L;

    private long id;
    private String title;
    private String artist;
    private String size;
    private String downloadUrl;

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
      title(instance.title());
      artist(instance.artist());
      Optional<String> sizeOptional = instance.size();
      if (sizeOptional.isPresent()) {
        size(sizeOptional);
      }
      Optional<String> downloadUrlOptional = instance.downloadUrl();
      if (downloadUrlOptional.isPresent()) {
        downloadUrl(downloadUrlOptional);
      }
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
     * Initializes the value for the {@link GDSong#title() title} attribute.
     * @param title The value for title 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder title(String title) {
      this.title = Objects.requireNonNull(title, "title");
      initBits &= ~INIT_BIT_TITLE;
      return this;
    }

    /**
     * Initializes the value for the {@link GDSong#artist() artist} attribute.
     * @param artist The value for artist 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder artist(String artist) {
      this.artist = Objects.requireNonNull(artist, "artist");
      initBits &= ~INIT_BIT_ARTIST;
      return this;
    }

    /**
     * Initializes the optional value {@link GDSong#size() size} to size.
     * @param size The value for size
     * @return {@code this} builder for chained invocation
     */
    public final Builder size(String size) {
      this.size = Objects.requireNonNull(size, "size");
      return this;
    }

    /**
     * Initializes the optional value {@link GDSong#size() size} to size.
     * @param size The value for size
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder size(Optional<String> size) {
      this.size = size.orElse(null);
      return this;
    }

    /**
     * Initializes the optional value {@link GDSong#downloadUrl() downloadUrl} to downloadUrl.
     * @param downloadUrl The value for downloadUrl
     * @return {@code this} builder for chained invocation
     */
    public final Builder downloadUrl(String downloadUrl) {
      this.downloadUrl = Objects.requireNonNull(downloadUrl, "downloadUrl");
      return this;
    }

    /**
     * Initializes the optional value {@link GDSong#downloadUrl() downloadUrl} to downloadUrl.
     * @param downloadUrl The value for downloadUrl
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder downloadUrl(Optional<String> downloadUrl) {
      this.downloadUrl = downloadUrl.orElse(null);
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
      return new ImmutableGDSong(id, title, artist, size, downloadUrl);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<>();
      if ((initBits & INIT_BIT_ID) != 0) attributes.add("id");
      if ((initBits & INIT_BIT_TITLE) != 0) attributes.add("title");
      if ((initBits & INIT_BIT_ARTIST) != 0) attributes.add("artist");
      return "Cannot build GDSong, some of required attributes are not set " + attributes;
    }
  }
}
