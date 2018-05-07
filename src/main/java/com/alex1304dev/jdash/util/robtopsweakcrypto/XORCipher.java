package com.alex1304dev.jdash.util.robtopsweakcrypto;

/**
 * Implementation of the XOR Cipher algorithm.
 * 
 * It's done by performing cyclically a XOR operation on the ASCII code of each character between the
 * message and a key.
 * 
 * @see {@link https://en.wikipedia.org/wiki/XOR_cipher}
 * 
 * @author Alex1304
 */
public class XORCipher {
	
	private byte[] key;

	/**
	 * Constructs a new XORCipher with a provided key
	 * @throws IllegalArgumentException if the key is an empty string or null
	 */
	public XORCipher(String key) {
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("Key must not be empty !");
		
		this.key = key.getBytes();
	}
	
	/**
	 * Ciphers the message using the key provided on the object instanciation.
	 * 
	 * @param message
	 *            - the message to cipher
	 * @return the ciphered message as String
	 */
	public String cipher(String message) {
		byte[] messageBytes = message.getBytes();
		byte[] result = new byte[messageBytes.length];
		
		for (int i = 0 ; i < messageBytes.length ; i++)
			result[i] = (byte) (messageBytes[i] ^ key[i % key.length]);
		
		return new String(result);
	}
	
	/**
	 * Retrieves the key by performing a XOR cipher between the plaintext
	 * message and the ciphered message. It's strongly recommended to ensure
	 * that both parameters have the same length, or else the key might not be
	 * the one expected.
	 * 
	 * @param messagePlain
	 *            - the plaintext message
	 * @param messageCipher
	 *            - the ciphered message
	 * @return the shortest possible key as String
	 * @throws IllegalArgumentException
	 *             if one of the parameters are empty strings or null
	 */
	public static String findKey(String messagePlain, String messageCipher) {
		if (messagePlain == null || messageCipher == null || messagePlain.isEmpty() || messageCipher.isEmpty())
			throw new IllegalArgumentException("At least one of the given parameters were empty strings or null");
		
		XORCipher xorBetweenMsgs = new XORCipher(messageCipher);
		String nonTrimmedKey = xorBetweenMsgs.cipher(messagePlain);
		
		String result = "";
		int repeatIndex = 0;
		int lastRepeatIndex = 0;
		
		for (int i = 0 ; i < nonTrimmedKey.length() ; i++) {
			if (result.isEmpty() || nonTrimmedKey.charAt(repeatIndex) != nonTrimmedKey.charAt(i)) {
				if (repeatIndex > 0 && lastRepeatIndex < i) {
					lastRepeatIndex++;
					i = lastRepeatIndex - 1;
				} else {
					lastRepeatIndex = i;
					repeatIndex = 0;
					result = nonTrimmedKey.substring(0, i + 1);
				}
			} else {
				repeatIndex++;
			}
		}
		
		return result;
	}
}
