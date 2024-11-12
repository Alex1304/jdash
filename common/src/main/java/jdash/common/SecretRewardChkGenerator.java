package jdash.common;

import java.util.function.Supplier;

import static jdash.common.RobTopsWeakEncryption.CHK_REWARDS_XOR_CIPHER;
import static jdash.common.RobTopsWeakEncryption.encode;

/**
 * Function that generates a client CHK for secret rewards. Use {@link #random()} or {@link #fixed(String, String)} to
 * customize
 * CHK generation behavior.
 */
@FunctionalInterface
public interface SecretRewardChkGenerator extends Supplier<String> {

    /**
     * Function that generates a CHK at random, using a key representing a random number between 100_000 (inclusive) and
     * 900_000 (exclusive).
     *
     * @return a random {@link SecretRewardChkGenerator}
     */
    static SecretRewardChkGenerator random() {
        return RobTopsWeakEncryption::generateSecretRewardChk;
    }

    /**
     * Function that generates a CHK with a fixed input. Use this when you need deterministic behavior.
     *
     * @param prefix a string that will be prepended to the CHK. Only the first 5 characters are taken, and will be
     *               padded with 'X' if provided prefix is less than 5 characters.
     * @param key    the key encoded in the CHK. It is recommended to be 6 characters or highers for the server to
     *               accept it.
     * @return a {@link SecretRewardChkGenerator} with fixed input
     */
    static SecretRewardChkGenerator fixed(String prefix, String key) {
        return () -> String.format("%5s", prefix.substring(0, 5)).replace(' ', 'X') +
                encode(key, CHK_REWARDS_XOR_CIPHER);
    }
}
