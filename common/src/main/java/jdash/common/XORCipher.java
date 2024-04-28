package jdash.common;

/**
 * Implementation of the XOR Cipher algorithm.
 * <p>
 * It's done by performing cyclically a XOR operation on the ASCII code of each character between the message and a
 * key.
 * <p>
 * See <a href="https://en.wikipedia.org/wiki/XOR_cipher">https://en.wikipedia.org/wiki/XOR_cipher</a>
 */
final class XORCipher {

    private final byte[] key;

    /**
     * Constructs a new XORCipher with a provided key
     *
     * @param key the key
     * @throws IllegalArgumentException if the key is an empty string or null
     */
    XORCipher(String key) {
        if (key == null || key.isEmpty())
            throw new IllegalArgumentException("Key must not be empty !");

        this.key = key.getBytes();
    }

    /**
     * Ciphers the message using the key provided on the object instantiation.
     *
     * @param message the message to cipher
     * @return the ciphered message as String
     */
    String cipher(String message) {
        byte[] messageBytes = message.getBytes();
        byte[] result = new byte[messageBytes.length];

        for (int i = 0; i < messageBytes.length; i++)
            result[i] = (byte) (messageBytes[i] ^ key[i % key.length]);

        return new String(result);
    }
}