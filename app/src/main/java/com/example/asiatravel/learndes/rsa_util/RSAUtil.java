package com.example.asiatravel.learndes.rsa_util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * Created by kuangxiaoguo on 16/9/14.
 *
 * RAS加密工具类
 */
public class RSAUtil {

    public static final String PUBLIC_KEY = "RSAPublicKey";
    public static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 生成RSA公钥和私钥
     *
     * @return RSA公钥和私钥的Map集合
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        //初始化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        //获取密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //获取公钥和私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //把公钥和私钥存入map集合
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 使用公钥加密
     *
     * @param data      需要加密的数据
     * @param publicKey 公钥
     * @return 加密后的字节数组
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, RSAPublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 使用私钥解密
     *
     * @param data       被公钥加密后的数据
     * @param privateKey 解密用的私钥
     * @return 解密后的数据的字节数组
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 从Map集合中获取公钥
     *
     * @param keyMap 存储公钥和私钥的map集合
     * @return 返回公钥
     */
    public static RSAPublicKey getPublicKey(Map<String, Object> keyMap) {
        return (RSAPublicKey) keyMap.get(PUBLIC_KEY);
    }

    /**
     * 从Map集合中获取私钥
     *
     * @param keyMap 存储公钥和私钥的map
     * @return 返回私钥
     */
    public static RSAPrivateKey getPrivateKey(Map<String, Object> keyMap) {
        return (RSAPrivateKey) keyMap.get(PRIVATE_KEY);
    }
}
