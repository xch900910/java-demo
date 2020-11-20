package com.example.day.util;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-07-17 09:20
 **/
public class SignUtil {
    private static final String ENCODING = "UTF-8";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * SHA256WithRSA签名
     *
     * @param data
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws UnsupportedEncodingException
     */
    public static byte[] sign256(String data, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException,
            SignatureException, UnsupportedEncodingException {

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);

        signature.initSign(privateKey);

        signature.update(data.getBytes(ENCODING));

        return signature.sign();
    }

    public static boolean verify256(String data, byte[] sign, PublicKey publicKey) {
        if (data == null || sign == null || publicKey == null) {
            return false;
        }

        try {
            Signature signetcheck = Signature.getInstance(SIGNATURE_ALGORITHM);
            signetcheck.initVerify(publicKey);
            signetcheck.update(data.getBytes("UTF-8"));
            return signetcheck.verify(sign);
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     */
    public static String encodeBase64(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

    /**
     * BASE64解码
     *
     * @param bytes
     * @return
     */
    public static byte[] decodeBase64(byte[] bytes) {
        byte[] result = null;
        try {
            result = Base64.decodeBase64(bytes);
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /**
     * String转私钥PrivateKey
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * String转公钥PublicKey
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;

    }

}
