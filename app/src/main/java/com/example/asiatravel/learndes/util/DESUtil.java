package com.example.asiatravel.learndes.util;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by kuangxiaoguo on 16/9/11.
 *
 * DES加密工具类
 */
public class DESUtil {

    private static final String TAG = "TAG";

    /**
     * 生成密钥
     */
    public static byte[] initKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (Exception e) {
            Log.e(TAG, "initKey: " + e.getMessage());
        }
        return null;
    }

    /**
     * DES加密
     *
     * @param data 需要加密的数据
     * @param key  加密使用的密钥
     * @return 加密后获取的字节数组
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        SecretKey secretKey = new SecretKeySpec(key, "DES");
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            Log.e(TAG, "encrypt: " + e.getMessage());
        }
        return null;
    }

    /**
     * DES解密
     */
    /**
     * @param data 密文对应的字节数组
     * @param key  算法名字
     * @return 解密后的字节数组
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        SecretKey secretKey = new SecretKeySpec(key, "DES");
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            Log.e(TAG, "decrypt: " + e.getMessage());
        }
        return null;
    }
}
