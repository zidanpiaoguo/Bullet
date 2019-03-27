package com.lzy.commonbase.network.rsa;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by 闫文锐 on 2019/1/8.
 */

public class RSASupport {

    public static final String RSA = "RSA";// 非对称加密密钥算法
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式
    public static final int DEFAULT_KEY_SIZE = 1024;//秘钥默认长度
    public static final int ENCRYPT_MAX_SIZE = 117;//加密不能超过117
    public static final int DECRYPT_MAX_SIZE = 128;//解密不能超过128
    private PrivateKey mPrivateKey = null;
    private PublicKey mPublicKey = null;
    private static RSASupport RSAInstance = null;

    private RSASupport() {
        mPrivateKey = getRSAPriKey(PRIVATEKEY);
        mPublicKey = getRSAPubKey(PUBLICKEY);
    }

    public static RSASupport getRSAInstance() {
        if (RSAInstance == null) {
            RSAInstance = new RSASupport();
        }
        return RSAInstance;
    }

    /**
     * 随机生成RSA密钥对
     *
     * @param keyLength 密钥长度，范围：512～2048
     *                  一般1024
     * @return
     * @author 闫文锐
     * @created 2019年1月0日 下午8:42:59
     */
    public KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(keyLength);
            return kpg.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 描述：获取RSA公钥
     *
     * @return PublicKey
     * @throws Exception
     * @author 闫文锐
     * @created 2019年1月7日 下午8:42:59
     */
    public PublicKey getRSAPubKey(String pubKey) {
        try {
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodeBase64(pubKey));
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回当前默认的生成的PublicKey
     * @return
     */
    public PublicKey getRSAPubKey() {
        if (mPublicKey == null)
        {
            mPublicKey = getRSAPubKey(PUBLICKEY);
        }
        return mPublicKey;
    }

    /**
     * 描述：获取RSA私钥
     *
     * @return PrivateKey
     * @throws Exception
     * @author 闫文锐
     * @created 2019年1月0日 下午8:42:59
     */
    public PrivateKey getRSAPriKey(String priKey) {
        try {
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodeBase64(priKey));
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回当前默认的PrivateKey
     * @return
     */
    public PrivateKey getRSAPriKey() {
        if (mPrivateKey == null){
            mPrivateKey = getRSAPriKey(PRIVATEKEY);
        }
        return mPrivateKey;
    }

    /**
     * 描述：String转byte数组
     *
     * @param target
     * @return
     * @throws Exception
     * @author 闫文锐
     * @created 2019年1月0日 下午8:42:59
     */
    public byte[] decodeBase64(String target) throws UnsupportedEncodingException {
        return Base64.decode(target, Base64.DEFAULT);
    }

    public String encodeToString(byte[] data) throws UnsupportedEncodingException {
        return new String(Base64.encode(data, Base64.DEFAULT));
    }


    /**
     * 私钥加密
     *
     * @param data       待加密数据
     * @param keyPrivate 密钥
     * @return byte[] 加密数据
     * @author 闫文锐
     * @created 2019年1月0日 下午8:42:59
     */
    public byte[] encryptByPrivateKey(byte[] data, PrivateKey keyPrivate) throws Exception {
        // 数据加密
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, keyPrivate);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     *
     * @param data      待解密数据
     * @param keyPublic 密钥
     * @return byte[] 解密数据
     */
    public byte[] decryptByPublicKey(byte[] data, PublicKey keyPublic) throws Exception {
        // 数据解密
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, keyPublic);
        return cipher.doFinal(data);
    }


    /**
     * 私钥分段加密
     *
     * @param data       要加密的原始数据的字节数组
     * @param privateKey 秘钥
     * @return 加密后的字节数组
     */
    public byte[] encryptByPrivateKeyForSpilt(byte[] data, PrivateKey privateKey) throws Exception {
        // 数据加密
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > ENCRYPT_MAX_SIZE) {
                cache = cipher.doFinal(data, offSet, ENCRYPT_MAX_SIZE);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            i++;
            offSet = i * ENCRYPT_MAX_SIZE;
            out.write(cache, 0, cache.length);
        }
        out.close();
        return out.toByteArray();
    }

    /**
     * 私钥分段加密
     *
     * @param data       要加密的原始数据不是Base64解码后的数据
     * @param privateKey 秘钥 返回Base64 编码后的数据
     */
    public String encryptByPrivateKeyForSpilt(String data, PrivateKey privateKey) throws Exception {
        byte[] input = data.getBytes();
        return encodeToString(encryptByPrivateKeyForSpilt(input, privateKey));
    }


    /**
     * 公钥分段解密
     *
     * @param encrypted 待解密数据
     * @param publicKey 密钥
     */
    public byte[] decryptByPublicKeyForSpilt(byte[] encrypted, PublicKey publicKey) throws Exception {
        int inputLen = encrypted.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 数据解密
        Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > DECRYPT_MAX_SIZE) {
                cache = cipher.doFinal(encrypted, offSet, DECRYPT_MAX_SIZE);
            } else {
                cache = cipher.doFinal(encrypted, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * DECRYPT_MAX_SIZE;
        }
        out.close();
        return out.toByteArray();
    }

    /**
     * 公钥分段解密
     *
     * @param data      Base64编码后的数据
     * @param publicKey 密钥
     * @Retrun 返回原始数据
     */
    public String decryptByPublicKeyForSpilt(String data, PublicKey publicKey) throws Exception {
        byte[] input = decodeBase64(data);
        return new String(decryptByPublicKeyForSpilt(input, publicKey));
    }

    public static final String PUBLICKEY =
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5tMdEALMenfOhEDLp1BMdoa97\n" +
            "NQdlOUImSXcE38nTCCxAyTDm3KnmABO0WPwCBH+lcZKEVD5sGSa9sXxkMtmjjWDx\n" +
            "JBo5ADo3vtrHbwNhZdYdN/M2Fq0LEoaJQ5eKCW3QeGXJOPtDWyuO1qS+YOF7XXL2\n" +
            "ZWAvKlhWopzp8uTq+wIDAQAB";


    public static final String PRIVATEKEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMJNkTLM/1p5e+u4\n" +
            "eT/en+KqSB1Klat/9PDegcU0wDbp7dmXtxOKpgjrMbiSD5DbrCfRHRqhxn9f3nlx\n" +
            "tzUz+qp+kewTeqGMFIVXCaBVgl1KiAJYNwRAwYZgX8/E2YYcwTK6w2KJaCbp7H7R\n" +
            "pPlhI29tJ93ptf8M+UVuIileQ3svAgMBAAECgYA68V7vw0JfhuTx0CdwGa/1CeUQ\n" +
            "hbS7rvh2LXm+gk8hGXve+2g9VF0j7X1K2XxBXHJwLb6fep4Fu+Z4fueuCxOqJ3d+\n" +
            "emJ1de4qp2iP7ih2db/X2IbnkDn0KRy4p26YsLQI/wKiepU0S8WuEMZYhRqrB8w7\n" +
            "kQGEhpzAKKWJCQNkcQJBAOqwTGGk+5SMoBvOjKc/2lDWT50Y8+liE2JGryQxKuXX\n" +
            "GwVp3Fh2VawfFH/jyyDNCivR2YdDLWkmw5iMzid1v9cCQQDT8nD+JGdXydDxe82i\n" +
            "sQlaXiw6209vXkJyZrmWQAdEmxXns7f03cu3t/Bvs0hzDllggcRlyFAS4SnoXIkw\n" +
            "fRRpAkBooi97vVXZNhVQMHevPZ21OseGEBD3NZ3UzBTb+vvYO141vKvb2O57bePy\n" +
            "3Pm8Zw4o2Ue1sbK2Ve0Qv35OQ4ZDAkBe1yf0sTmFMQvMY0fbOzkiNO250JU1EorN\n" +
            "QmZdc31NF9Cvg94XNoCIT0jJ0wHialNYOfk2SnJ6YEMudOn2xqGxAkANZX4jDf3U\n" +
            "51UKkeBwCl8+UF/PgJ/i4ZlJgE/MIlE3dtSHo1z5R72HB9qd8lH71bM+VbmVkeOE\n" +
            "mQda/WhXZnoF";


}
