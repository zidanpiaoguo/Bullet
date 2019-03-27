package com.lzy.commonbase.network.rsa;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAUtils {

    /**
     * 私钥 （测试）
     * 正常是两对钥匙，后台拿着一对钥匙的公钥和一对钥匙的私钥
     * 你拿着是一对钥匙的私钥和一对钥匙的公钥， 每人拿着的都不是一对的。
     * 目前为了测试，我的私钥和公钥是一对钥匙
     */
    public static final String PRIVATE_KEY_DATA =
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKCJ9d6HYzI8d2cP\n" +
                    "2Jb/s/NmUMZ5IyqdlA/P9g9LnoL8xWR4yJc1p/N/ENHe9LMgXQazWZn6diJyByz3\n" +
                    "4WqsC513bzP218NXJ1P4aZdTSvUZt1GGq2P+IUrZ7eHL/UDKCp/hUUI4Eb5p7Xbc\n" +
                    "+TniaEZGYASMd6Q4La41hGBKZvqxAgMBAAECgYAYTCkhT/5seQhdWeGfIP5cZWzu\n" +
                    "TSFMbuMc7Y3BTOGl05oQldNOR3mu+dlS2lrQzaImxHhYPLcqiXWakb3VRkrPggiZ\n" +
                    "AnPuMJOcXgFISLCu0Lab8OoTqtNYd3TV/0NGlN8wb+WHap/1skR2jp3pRkl7vdYp\n" +
                    "WalGS5vDqXvmjPHhDQJBAMzJyTJC3vnmmGCqETscJ5ve8GKokZlg9Je6vafmf1BF\n" +
                    "KApzegsCIFbeEygw/kxhgZwWpFfjBdF9muShhYGFU9MCQQDIr2dv7fFIEZtd/xwf\n" +
                    "igzrhdNBOfX2ARwxflZJEPPnGru3BNKtJEGleIOn3ZRjzuqsdQObadqxr/cZqoM3\n" +
                    "89jrAkEAhjJM9/S+LUCg5edMN1Hx2sCzAdO45auVXMBwe9Ad6boHzSFy/je8fXA1\n" +
                    "WYcRfXf/+QsVUspgC7GliOEXnZKDJQJBAJQ4mNkekJp+BpaCGS9iMvCxQrpmKLet\n" +
                    "Ujlr6ibFEp+aKxKdyUx//sxMGlEdYr/kz4bYVfWvVQrvwD2AprHotEkCQBcm+7ND\n" +
                    "CNCG68YHg48LY3AbC07tQlcgZorOuO5b9K8Pgse3lbdGW5LCZyEL4REKtWN3RRjt\n" +
                    "SWE8xCO4DRnP88Y=";
    /**
     * 公钥 （测试）
     * 注意这个私钥和公钥是对称关系，只要对应都可以。
     * 可以公钥加密， 私钥解密
     * 也可以私钥加密 ，公钥解密
     */
    public static final String PUBLIC_KEY_DATA =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgifXeh2MyPHdnD9iW/7PzZlDG\n" +
            "eSMqnZQPz/YPS56C/MVkeMiXNafzfxDR3vSzIF0Gs1mZ+nYicgcs9+FqrAudd28z\n" +
            "9tfDVydT+GmXU0r1GbdRhqtj/iFK2e3hy/1Aygqf4VFCOBG+ae123Pk54mhGRmAE\n" +
            "jHekOC2uNYRgSmb6sQIDAQAB";

    /**
     * 加密填充方式
     **/
    private static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    /**
     * 加密算法RSA
     **/
    private static final String KEY_ALGORITHM = "RSA";
    /**
     * RSA最大加密明文大小
     **/
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     **/
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 签名算法 （正常是不用我们自己生产公钥对和私钥对的，如果需要，可以在这里直接生产）
     * 生产网站 http://web.chacuo.net/netrsakeypair
     **/
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 获取公钥的key  （测试）
     **/
    private static final String PUBLIC_KEY = "RSAPublicKey";
    /**
     * 获取私钥的key  （测试）
     **/
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    /**
     * 秘钥默认长度
     **/
    private static final int DEFAULT_KEY_SIZE = 2048;

    /**
     * 生成密钥对(公钥和私钥)
     **/
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }


    /**
     * 公钥加密
     *
     * @param data      源数据        string 要转换成字节
     * @param publicKey 公钥(BASE64编码)
     */
    public static byte[] encryptByPublicKey(String data, String publicKey) throws Exception {
        byte[] encryptedData = data.getBytes();
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        return getData(encryptedData, cipher,MAX_ENCRYPT_BLOCK);
    }


    /**
     * 私钥解密
     *
     * @param data      已加密数据
     * @param privateKey    私钥(BASE64编码)
     */
    public static byte[] decryptByPrivateKey(String data, String privateKey) throws Exception {
        byte[] encryptedData = data.getBytes();
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        return getData(encryptedData, cipher,MAX_DECRYPT_BLOCK);
    }


    /**
     * 私钥加密
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     */
    public static byte[] encryptByPrivateKey(String data, String privateKey ) throws Exception {
        byte[] encryptedData = data.getBytes();
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        //   Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        return getData(encryptedData, cipher,MAX_ENCRYPT_BLOCK);
    }


    /**
     * 公钥解密
     *
     * @param data 已加密数据
     * @param publicKey     公钥(BASE64编码)
     */
    public static byte[] decryptByPublicKey(String data, String publicKey) throws Exception {
        byte[] encryptedData = Base64Utils.decode(data) ;
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);


        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
    //    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        return getData(encryptedData, cipher,MAX_DECRYPT_BLOCK);
    }


    private static byte[] getData(byte[] encryptedData, Cipher cipher , int border) throws Exception {
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > border) {
                cache = cipher.doFinal(encryptedData, offSet, border);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * border;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 获取私钥
     *
     * @param keyMap 密钥对
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /**
     * 获取公钥
     *
     * @param keyMap 密钥对
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }
}
