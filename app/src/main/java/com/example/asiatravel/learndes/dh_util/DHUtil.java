package com.example.asiatravel.learndes.dh_util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

/**
 * Created by kuangxiaoguo on 16/9/14.
 *
 * DH加密工具类
 */
public class DHUtil {

    public static final String PUBLIC_KEY = "DHPublicKey";
    public static final String PRIVATE_KEY = "DHPrivateKey";

    /**
     * 甲方初始化并返回密钥
     *
     * @return 甲方的公钥和私钥
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        //初始化密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(1024);// 默认是1024, 516-1024且是64的倍数
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //得到公钥和私钥
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 乙方根据甲方公钥初始化并返回密钥对
     *
     * @param key 甲方的公钥
     * @return 乙方的公钥和私钥
     * @throws Exception
     */
    public static Map<String, Object> initKey(byte[] key) throws Exception {
        //将甲方公钥从字节数组转化为publicKey
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance("DH");
        //产出甲方公钥
        DHPublicKey dhPublicKey = (DHPublicKey) keyFactory.generatePublic(keySpec);
        //剖析甲方公钥获取其参数
        DHParameterSpec dhParameterSpec = dhPublicKey.getParams();
        //实例化密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
        //用甲方公钥初始化密钥生成器
        keyPairGenerator.initialize(dhParameterSpec);
        //获取密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //获取乙方公钥和私钥
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
        //将乙方的公钥和私钥存入map集合中
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 根据对方的公钥和自己的私钥生成本地密钥
     *
     * @param publicKey  对方公钥
     * @param privateKey 自己私钥
     * @return 本地密钥
     * @throws Exception
     */
    public static byte[] getSecretKey(byte[] publicKey, byte[] privateKey) throws Exception {
        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance("DH");
        //将公钥从字节数组转化为publicKey
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKey);
        PublicKey pubKey = keyFactory.generatePublic(publicKeySpec);
        //将私钥从字节数组转化为privateKey
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKey);
        PrivateKey priKey = keyFactory.generatePrivate(privateKeySpec);
        //根据以上公钥和私钥生成本地的密钥secretKey
        //实例化keyAgreement
        KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
        //用自己的私钥初始化keyAgreement
        keyAgreement.init(priKey);
        //结合对方的公钥进行运算
        keyAgreement.doPhase(pubKey, true);
        //开始生成本地密钥secretKey,密钥算法为对称加密算法
        SecretKey secretKey = keyAgreement.generateSecret("AES");//DES 3DES AES
        return secretKey.getEncoded();
    }

    /**
     * 从Map中获取公钥
     *
     * @param keyMap 存放公钥和密钥的Map
     * @return 公钥
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) {
        DHPublicKey key = (DHPublicKey) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

    /**
     * 从Map中获取私钥
     *
     * @param keyMap 存放公钥和密钥的Map
     * @return 私钥
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        DHPrivateKey key = (DHPrivateKey) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }
}
