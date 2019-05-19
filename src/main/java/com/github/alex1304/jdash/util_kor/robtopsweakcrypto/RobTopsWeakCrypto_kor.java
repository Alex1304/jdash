 package com.github.alex1304.jdash.util.robtopsweakcrypto;
import com.github.alex1304.jdash.util.Utils;
/**
 * Provides methods to encrypt some data such as private (���� ������ ���� �Ϻ� ������ ��ȣȭ ��� ����)
* messages, level passwords and account passwords. (�޼���,���� ��ȣ �� ���� ��ȣ) */
public class RobTopsWeakCrypto
{
private static final XORCipher GD_MESSAGE_BODY_XOR_CIPHER = new XORCipher("14251");
private static final XORCipher LEVEL_PASS_XOR_CIPHER = new XORCipher("26364");
private static final XORCipher ACCOUNT_GJP_XOR_CIPHER = new XORCipher("37526");
private RobTopsWeakCrypto()
{
}
private static String decode0(String str, XORCipher algorithm)
{
return algorithm.cipher(Utils.b64Decode(str));
}
private static String encode0(String str, XORCipher algorithm)
{
return Utils.b64Encode(algorithm.cipher(str));
}
public static String decodeGDAccountPassword(String password)
{
return decode0(password, ACCOUNT_GJP_XOR_CIPHER);
}
public static String encodeGDAccountPassword(String password)
{
return encode0(password, ACCOUNT_GJP_XOR_CIPHER);
}
public static String decodeLevelPass(String lvlPass)
{
return decode0(lvlPass, LEVEL_PASS_XOR_CIPHER);
}
public static String encodeLevelPass(String lvlPass)
{
return encode0(lvlPass, LEVEL_PASS_XOR_CIPHER);
}
public static String decodeGDMessageBody(String msgBody)
{
return decode0(msgBody, GD_MESSAGE_BODY_XOR_CIPHER);
}
public static String encodeGDMessageBody(String msgBody)
{
return encode0(msgBody, GD_MESSAGE_BODY_XOR_CIPHER);
}

}
