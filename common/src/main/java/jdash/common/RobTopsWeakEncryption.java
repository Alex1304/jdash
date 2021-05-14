package jdash.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static jdash.common.internal.InternalUtils.b64Decode;
import static jdash.common.internal.InternalUtils.b64Encode;

/**
 * Provides methods to encrypt some data such as private
 * messages, level passwords and account passwords.
 */
public final class RobTopsWeakEncryption {

	private static final XORCipher GD_MESSAGE_BODY_XOR_CIPHER = new XORCipher("14251");
	private static final XORCipher LEVEL_PASS_XOR_CIPHER = new XORCipher("26364");
	private static final XORCipher ACCOUNT_GJP_XOR_CIPHER = new XORCipher("37526");
	private static final XORCipher CHK_XOR_CIPHER = new XORCipher("58281");
	
	private RobTopsWeakEncryption() {
	}
	
	private static String decode0(String str, XORCipher algorithm) {
		return algorithm.cipher(b64Decode(str));
	}	
	
	private static String encode0(String str, XORCipher algorithm) {
		return b64Encode(algorithm.cipher(str));
	}
	
	public static String decodeAccountPassword(String password) {
		return decode0(password, ACCOUNT_GJP_XOR_CIPHER);
	}
	
	public static String encodeAccountPassword(String password) {
		return encode0(password, ACCOUNT_GJP_XOR_CIPHER);
	}
	
	public static String decodeLevelPass(String lvlPass) {
		return decode0(lvlPass, LEVEL_PASS_XOR_CIPHER);
	}
	
	public static String encodeLevelPass(String lvlPass) {
		return encode0(lvlPass, LEVEL_PASS_XOR_CIPHER);
	}
	
	public static String decodePrivateMessageBody(String msgBody) {
		return decode0(msgBody, GD_MESSAGE_BODY_XOR_CIPHER);
	}
	
	public static String encodePrivateMessageBody(String msgBody) {
		return encode0(msgBody, GD_MESSAGE_BODY_XOR_CIPHER);
	}

	public static String encodeChk(Object... params) {
	    return encode0(sha1(Arrays.stream(params)
                .map(String::valueOf)
                .collect(Collectors.joining())), CHK_XOR_CIPHER);
    }

    private static String sha1(String str) {
        try {
            var messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(StandardCharsets.UTF_8.encode(str));
            var digest = messageDigest.digest();
            return IntStream.range(0, digest.length)
                    .mapToObj(i -> String.format("%02x", 0xff & digest[i]))
                    .collect(Collectors.joining());
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }
}