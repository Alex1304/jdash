package com.github.alex1304.jdash.util.robtopsweakcrypto;
/**
 * Implementation of the XOR Cipher algorithm.
(XOR Cipher 알고리즘 구현) *
* It's done by performing cyclically a XOR operation on the ASCII code of each character between the (그것은 각 문자의 ASCII 코드에 대해 주기적으로 XOR 연산을 수행함으로써 이루어진다.)
* message and a key.
(메세지와 키)* 
 
* See https://en.wikipedia.org/wiki/XOR_cipher
(https://en.wikipedia.org/wiki/XOR_cipher을 참조하십시오.) */
public class XORCipher
{
private byte[] key;
/**
* Constructs a new XORCipher with a provided key (제공된 키를 사용하여 새 XORCipher 구성)
*
* @param key
*- the key
 * 
* @throws IllegalArgumentException
 *if the key is an empty string or null (키가 비어 있거나 null인 경우)
*/
public XORCipher(String key)
{
if (key == null || key.isEmpty())

throw new IllegalArgumentException("Key must not be empty !");
this.key = key.getBytes();

}
/**
 * Ciphers the message using the key provided on the object instanciation. (개체 삽입에 제공된 키를 사용하여 메시지 암호화)* 
	 
* @param message *- the message to cipher* @return the ciphered message as String (암호 메세지를 문자열로 반환한다) */
public String cipher(String message)
{
byte[] messageBytes = message.getBytes();
byte[] result = new byte[messageBytes.length];
for (int i = 0 ; i < messageBytes.length ; i++)
result[i] = (byte) (messageBytes[i] ^ key[i % key.length]);
return new String(result);
}
/**
 * Retrieves the key by performing a XOR cipher between the plaintext (일반 텍스트 간에 XOR 암호를 수행하여 키 검색)
* message and the ciphered message. It's strongly recommended to ensure (메시지 및 암호화된 메시지. 확실히 하는 것이 좋다.)
* that both parameters have the same length, or else the key might not be (두 파라미터의 길이가 같거나 키가 같지 않은지 여부)

* the one expected. (기대했던 것) * * @param messagePlain
*- the plaintext message * @param messageCipher *- the ciphered message * @return the shortest possible key as String
(가능한 최단 키를 문자열로 반환하다) * @throws IllegalArgumentException
	 *if one of the parameters are empty strings or null (매개 변수 중 하나가 비어 있거나 null인 경우)*/
public static String findKey(String messagePlain, String messageCipher)
{
if (messagePlain == null || messageCipher == null || messagePlain.isEmpty() || messageCipher.isEmpty())
throw new IllegalArgumentException("At least one of the given parameters were empty strings or null");
XORCipher xorBetweenMsgs = new XORCipher(messageCipher);
String nonTrimmedKey = xorBetweenMsgs.cipher(messagePlain);
String result = "";
int repeatIndex = 0;
int lastRepeatIndex = 0;
for (int i = 0 ; i < nonTrimmedKey.length() ; i++)
{
if (result.isEmpty() || nonTrimmedKey.charAt(repeatIndex) != nonTrimmedKey.charAt(i))
{
if (repeatIndex > 0 && lastRepeatIndex < i)
{
lastRepeatIndex++;
i = lastRepeatIndex - 1;
}
else
{
lastRepeatIndex = i;
repeatIndex = 0;

result = nonTrimmedKey.substring(0, i + 1);
}
}
else
{
repeatIndex++;
}
}
return result;
}
}
