package com.github.alex1304.jdash.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author {@link https://myadventuresincoding.wordpress.com/2016/01/02/java-simple-gzip-utility-to-compress-and-decompress-a-string/}
 */
public class Gzip {

	public static byte[] zip(final String str) {
		if ((str == null) || (str.length() == 0)) {
			throw new IllegalArgumentException("Cannot zip null or empty string");
		}

		try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
			try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
				gzipOutputStream.write(str.getBytes(StandardCharsets.UTF_8));
			}
			return byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException("Failed to zip content", e);
		}
	}

	public static String unzip(final byte[] compressed) {
		if ((compressed == null) || (compressed.length == 0)) {
			throw new IllegalArgumentException("Cannot unzip null or empty bytes");
		}
		if (!isZipped(compressed)) {
			return new String(compressed);
		}

		try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressed)) {
			try (GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream)) {
				try (InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream,
						StandardCharsets.UTF_8)) {
					try (BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
						StringBuilder output = new StringBuilder();
						String line;
						while ((line = bufferedReader.readLine()) != null) {
							output.append(line);
						}
						return output.toString();
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to unzip content", e);
		}
	}

	public static boolean isZipped(final byte[] compressed) {
		return (compressed[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
				&& (compressed[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8));
	}
}