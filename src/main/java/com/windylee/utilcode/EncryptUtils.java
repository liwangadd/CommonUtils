package com.windylee.utilcode;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class EncryptUtils {

    private EncryptUtils() {
        throw new UnsupportedOperationException("the EncryptUtils cannot be initialed");
    }

    /**
     * MD5加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptMD5toString(final String data) {
        return encryptMD5toString(data.getBytes());
    }

    /**
     * MD5加盐加密
     * @param data 明文字符串
     * @param salt 盐字符串
     * @return 16进制密文
     */
    public static String encryptMD5toString(final String data, final String salt) {
        return bytes2HexString(encryptMD5((data + salt).getBytes()));
    }

    /**
     * MD5加密
     * @param data 字节数组
     * @return 16进制密文
     */
    public static String encryptMD5toString(byte[] data) {
        return bytes2HexString(encryptMD5(data));
    }

    /**
     * MD5加密
     * @param data 明文字节数组
     * @param salt 盐字节数组
     * @return 16进制密文
     */
    public static String encryptMD5toString(final byte[] data, final byte[] salt) {
        if (data == null || salt == null) return null;
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(data, 0, dataSalt, data.length, salt.length);
        return bytes2HexString(encryptMD5(dataSalt));
    }

    /**
     * MD5加密
     * @param data 加盐后的字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptMD5(final byte[] data) {
        return hashTemplate(data, "MD5");
    }

    /**
     * SHA1加密
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA1toString(final String data) {
        return encryptSHA1toString(data.getBytes());
    }

    /**
     * SHA1加密
     * @param data 明文字节数组
     * @return 15进制密文
     */
    public static String encryptSHA1toString(final byte[] data) {
        return bytes2HexString(encryptSHA1(data));
    }

    /**
     * SHA1加密
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA1(final byte[] data) {
        return hashTemplate(data, "SHA1");
    }

    /**
     * SHA256加密
     * @param data 明文字符串
     * @return 16进制密文
     */
    public static String encryptSHA256toString(final String data) {
        return encryptSHA256toString(data.getBytes());
    }

    /**
     * SHA256加密
     * @param data 明文字节数组
     * @return 16进制密文
     */
    public static String encryptSHA256toString(final byte[] data) {
        return bytes2HexString(encryptSHA256(data));
    }

    /**
     * SHA256加密
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    public static byte[] encryptSHA256(final byte[] data) {
        return hashTemplate(data, "SHA256");
    }

    /**
     * 加密
     * @param data 明文字节数组
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (null == data || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // DES加密相关
    ///////////////////////////////////////////////////////////////////////////


    public static String DES_Transformation = "DES/ECB/NoPadding";
    private static final String DES_Algorithm = "DES";

    public static byte[] encryptDES2Base64(final byte[] data, final byte[] key) {
        return base64Encode(encryptDES(data, key));
    }

    public static String encryptDES2HexString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptDES(data, key));
    }

    public static byte[] encryptDES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, DES_Algorithm, DES_Transformation, true);
    }

    public static byte[] decryptBase64DES(final byte[] data, final byte[] key) {
        return decryptDES(base64Decode(data), key);
    }

    public static byte[] decryptHexStringDES(final String data, final byte[] key) {
        return decryptDES(hexString2Bytes(data), key);
    }

    public static byte[] decryptDES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, DES_Algorithm, DES_Transformation, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    // AES加密相关
    ///////////////////////////////////////////////////////////////////////////

    public static String AES_Transformation = "AES/ECB/NoPadding";
    private static final String AES_Algorithm = "AES";

    public static byte[] encryptAES2Base64(final byte[] data, final byte[] key) {
        return base64Encode(encryptAES(data, key));
    }

    public static String encryptAES2HexString(final byte[] data, final byte[] key) {
        return bytes2HexString(encryptAES(data, key));
    }

    public static byte[] encryptAES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, AES_Algorithm, AES_Transformation, true);
    }

    public static byte[] decryptBase64AES(final byte[] data, final byte[] key) {
        return decryptAES(base64Decode(data), key);
    }

    public static byte[] decryptHexStringAES(final String data, final byte[] key) {
        return decryptAES(hexString2Bytes(data), key);
    }


    public static byte[] decryptAES(final byte[] data, final byte[] key) {
        return desTemplate(data, key, AES_Algorithm, AES_Transformation, false);
    }

    private static byte[] desTemplate(final byte[] data, final byte[] key, final String algorithm, final String transformation, final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            SecureRandom random = new SecureRandom();
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, random);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    private static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 2];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return 10 + hexChar - 'A';
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static byte[] base64Encode(final byte[] input) {
        return Base64.getEncoder().encode(input);
    }

    private static byte[] base64Decode(final byte[] input) {
        return Base64.getDecoder().decode(input);
    }

    private static boolean isSpace(final String str) {
        return str == null || str.trim().length() == 0;
    }
}
