package com.github.alex1304.jdash.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.github.alex1304.jdash.entity.DemonDifficulty;
import com.github.alex1304.jdash.entity.Difficulty;
import com.github.alex1304.jdash.entity.GDSong;
import com.github.alex1304.jdash.util.robtopsweakcrypto.XORCipher;

/**
 * Contains utility static methods 
 *
 * @author Alex1304
 *
 */
public final class Utils {
	private Utils() {
	}

	private static final String CHAR_TABLE= "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	private static final Map<Integer, GDSong> AUDIO_TRACKS;
	
	static {
		AUDIO_TRACKS = new HashMap<>();
		AUDIO_TRACKS.put(0, new GDSong("ForeverBound", "Stereo Madness"));
		AUDIO_TRACKS.put(1, new GDSong("DJVI", "Back On Track"));
		AUDIO_TRACKS.put(2, new GDSong("Step", "Polargeist"));
		AUDIO_TRACKS.put(3, new GDSong("DJVI", "Dry Out"));
		AUDIO_TRACKS.put(4, new GDSong("DJVI", "Base After Base"));
		AUDIO_TRACKS.put(5, new GDSong("DJVI", "Cant Let Go"));
		AUDIO_TRACKS.put(6, new GDSong("Waterflame", "Jumper"));
		AUDIO_TRACKS.put(7, new GDSong("Waterflame", "Time Machine"));
		AUDIO_TRACKS.put(8, new GDSong("DJVI", "Cycles"));
		AUDIO_TRACKS.put(9, new GDSong("DJVI", "xStep"));
		AUDIO_TRACKS.put(10, new GDSong("Waterflame", "Clutterfunk"));
		AUDIO_TRACKS.put(11, new GDSong("DJ-Nate", "Theory of Everything"));
		AUDIO_TRACKS.put(12, new GDSong("Waterflame", "Electroman Adventures"));
		AUDIO_TRACKS.put(13, new GDSong("DJ-Nate", "Clubstep"));
		AUDIO_TRACKS.put(14, new GDSong("DJ-Nate", "Electrodynamix"));
		AUDIO_TRACKS.put(15, new GDSong("Waterflame", "Hexagon Force"));
		AUDIO_TRACKS.put(16, new GDSong("Waterflame", "Blast Processing"));
		AUDIO_TRACKS.put(17, new GDSong("DJ-Nate", "Theory of Everything 2"));
		AUDIO_TRACKS.put(18, new GDSong("Waterflame", "Geometrical Dominator"));
		AUDIO_TRACKS.put(19, new GDSong("F-777", "Deadlocked"));
		AUDIO_TRACKS.put(20, new GDSong("MDK", "Fingerdash"));
	}
	
	/**
	 * Gets an audio track by its ID
	 * 
	 * @param id - the audio track id
	 * @return GDSong
	 */
	public static GDSong getAudioTrack(int id) {
		return AUDIO_TRACKS.containsKey(id) ?  AUDIO_TRACKS.get(id) : new GDSong("-", "Unknown");
	}

	public static Difficulty valueToDifficulty(int value) {
		switch (value) {
			case 10:
				return Difficulty.EASY; 
			case 20:
				return Difficulty.NORMAL; 
			case 30:
				return Difficulty.HARD; 
			case 40:
				return Difficulty.HARDER;
			case 50:
				return Difficulty.INSANE;  
			default:
				return Difficulty.NA;
		}
	}
	
	public static DemonDifficulty valueToDemonDifficulty(int value) {
		switch (value) {
			case 3:
				return DemonDifficulty.EASY; 
			case 4:
				return DemonDifficulty.MEDIUM; 
			case 5:
				return DemonDifficulty.INSANE;
			case 6:
				return DemonDifficulty.EXTREME;  
			default:
				return DemonDifficulty.HARD;
		}
	}
	
	public static String defaultStringIfEmptyOrNull(String str, String fallback) {
		return str == null || str.isEmpty() ? fallback : str;
	}
	
	public static String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}
	
	public static String urlDecode(String str) {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}
	
	public static byte[] b64DecodeToBytes(String str) {
		byte[] result = null;
		StringBuilder buf = new StringBuilder(str);
		while (result == null && buf.length() > 0) {
			try {
				result = Base64.getUrlDecoder().decode(buf.toString());
			} catch (IllegalArgumentException e) {
				buf.deleteCharAt(buf.length() - 1);
			}
		}
		return result == null ? new byte[0] : result;
	}
	
	public static String b64Decode(String str) {
		return new String(b64DecodeToBytes(str));
	}
	
	public static String b64Encode(String str) {
		return Base64.getUrlEncoder().encodeToString(str.getBytes());
	}

	public static String sha1Encrypt(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes(StandardCharsets.UTF_8));
			byte[] digest = md.digest();
			StringBuilder sb = new StringBuilder();
			for(byte b : digest) {
				sb.append(String.format("%02x", 0xff & b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError("Encrypting failed");
		}
	}

	public static String randomString(int size) {
		StringBuilder sb = new StringBuilder(size);
		for(int i = 0; i < size; i++) {
			int ran = (int) Math.floor(Math.random()*CHAR_TABLE.length());
			sb.append(CHAR_TABLE.charAt(ran));
		}
		return sb.toString();
	}

	public static String buildChk(String key, String... args) {
		String sha1 = sha1Encrypt(String.join("", args));
		XORCipher xor = new XORCipher(key);
		return b64Encode(xor.cipher(sha1));
	}
	
	public static int partialParseInt(String str) {
		for (int i = 0 ; i < str.length() ; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return Integer.parseInt(str.substring(0, i));
			}
		}
		return Integer.parseInt(str);
	}
}
