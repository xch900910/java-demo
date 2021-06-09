package com.example.workNote.util;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author: xiongchaohua
 * @Des :
 * @create: 2020-07-17 09:20
 **/
public class SignUtil {
    private static final String ENCODING = "UTF-8";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;


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
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }

    /**
     * BASE64解码
     *
     * @param bytes
     * @return
     */
    public static byte[] decodeBase64(byte[] bytes) {
        byte[] result = null;
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            result = decoder.decode(bytes);
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
        Base64.Decoder decoder = Base64.getDecoder();
        keyBytes = decoder.decode(key);
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
        Base64.Decoder decoder = Base64.getDecoder();
        keyBytes = decoder.decode(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;

    }

    /**
     * 公钥加密
     *
     * @param data      需要加密的数据
     * @param publicKey 公钥字符串
     * @return 加密后的数据
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 公钥加密方法
     *
     * @param data      需加密的字符串
     * @param PUBLICKEY 公钥字符串
     * @return 加密后的字符串
     */
    public static String encryptedDataByPublic(String data, String PUBLICKEY) {
        try {
            data = encodeBase64(encryptByPublicKey(data.getBytes(), PUBLICKEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 私钥解密方法
     *
     * @param data       公钥加密的字符串
     * @param PRIVATEKEY 私钥字符串
     * @return 私钥解密的字符串
     */
    public static String decryptDataByPrivate(String data, String PRIVATEKEY) {
        String temp = "";
        try {
            byte[] rs = decodeBase64(data.getBytes());
            //以utf-8的方式生成字符串
            temp = new String(decryptByPrivateKey(rs, PRIVATEKEY), "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 私钥解密
     *
     * @param encryptedData 公钥加密的数据
     * @param privateKey    私钥字符串
     * @return 私钥解密的数据
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        PrivateKey prikey = getPrivateKey(privateKey);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);
        InputStream ins = new ByteArrayInputStream(encryptedData);
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[256];
        int bufl;
        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }
            writer.write(cipher.doFinal(block));
        }

        return writer.toByteArray();
    }
}
