package jdash.common;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static jdash.common.internal.InternalUtils.*;

/**
 * Provides methods to encode and decode some data such as private message body, level passcodes and account passwords.
 */
public final class RobTopsWeakEncryption {

    static final XORCipher GD_MESSAGE_BODY_XOR_CIPHER = new XORCipher("14251");
    static final XORCipher LEVEL_PASSCODE_XOR_CIPHER = new XORCipher("26364");
    static final XORCipher ACCOUNT_PASSWORD_XOR_CIPHER = new XORCipher("37526");
    static final XORCipher CHK_XOR_CIPHER = new XORCipher("58281");
    static final XORCipher CHK_REWARDS_XOR_CIPHER = new XORCipher("59182");

    private RobTopsWeakEncryption() {
    }

    static String decode(String str, XORCipher algorithm) {
        return algorithm.cipher(b64Decode(str));
    }

    static String encode(String str, XORCipher algorithm) {
        return b64Encode(algorithm.cipher(str));
    }

    /**
     * Decodes the given string using the algorithm used in-game to decode account passwords.
     *
     * @param encoded the encoded string
     * @return the decoded string
     */
    public static String decodeAccountPassword(String encoded) {
        return decode(encoded, ACCOUNT_PASSWORD_XOR_CIPHER);
    }

    /**
     * Encodes the given string using the algorithm used in-game to encode account passwords.
     *
     * @param plainText the decoded string
     * @return the encoded string
     */
    public static String encodeAccountPassword(String plainText) {
        return encode(plainText, ACCOUNT_PASSWORD_XOR_CIPHER);
    }

    /**
     * Decodes the given string using the algorithm used in-game to decode level passcodes.
     *
     * @param encoded the encoded string
     * @return the decoded string
     */
    public static String decodeLevelPasscode(String encoded) {
        return decode(encoded, LEVEL_PASSCODE_XOR_CIPHER);
    }

    /**
     * Encodes the given string using the algorithm used in-game to encode level passcodes.
     *
     * @param plainText the decoded string
     * @return the encoded string
     */
    public static String encodeLevelPasscode(String plainText) {
        return encode(plainText, LEVEL_PASSCODE_XOR_CIPHER);
    }

    /**
     * Decodes the given string using the algorithm used in-game to decode the body of private messages.
     *
     * @param encoded the encoded string
     * @return the decoded string
     */
    public static String decodePrivateMessageBody(String encoded) {
        return decode(encoded, GD_MESSAGE_BODY_XOR_CIPHER);
    }

    /**
     * Encodes the given string using the algorithm used in-game to encode the body of private messages.
     *
     * @param plainText the decoded string
     * @return the encoded string
     */
    public static String encodePrivateMessageBody(String plainText) {
        return encode(plainText, GD_MESSAGE_BODY_XOR_CIPHER);
    }

    /**
     * Encodes the given parameters using the algorithm used in-game to calculate the CHK of a sequence of parameters.
     *
     * @param params the parameters
     * @return the encoded string
     */
    public static String encodeChk(Object... params) {
        return encode(sha1(Arrays.stream(params)
                .map(String::valueOf)
                .collect(Collectors.joining())), CHK_XOR_CIPHER);
    }

    /**
     * Generates a valid CHK to get secret rewards (chests or Event level rewards).
     *
     * @return the encoded string
     */
    public static String generateSecretRewardChk() {
        return randomString(5) + encode("" + (new Random().nextInt(900000) + 100000), CHK_REWARDS_XOR_CIPHER);
    }

    /**
     * Encodes the given password into a string that can be passed as the gjp2 parameter for authenticated requests.
     *
     * @param password the password to encode
     * @return the encoded string
     */
    public static String encodeGjp2(String password) {
        return sha1(password + "mI29fmAnxgTs");
    }

    private static String sha1(String str) {
        try {
            final var messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(StandardCharsets.UTF_8.encode(str));
            final var digest = messageDigest.digest();
            return IntStream.range(0, digest.length)
                    .mapToObj(i -> String.format("%02x", 0xff & digest[i]))
                    .collect(Collectors.joining());
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }
}