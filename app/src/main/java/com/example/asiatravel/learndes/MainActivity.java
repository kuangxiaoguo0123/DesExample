package com.example.asiatravel.learndes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.asiatravel.learndes.dh_util.DHUtil;
import com.example.asiatravel.learndes.rsa_util.RSAUtil;
import com.example.asiatravel.learndes.util.AESUtil;
import com.example.asiatravel.learndes.util.ByteToHexUtil;
import com.example.asiatravel.learndes.util.DESUtil;
import com.example.asiatravel.learndes.util.TripleDESUtil;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import javax.crypto.interfaces.DHPublicKey;

public class MainActivity extends AppCompatActivity {

    private static final String DATA = "asiatravel";
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testDES();
//        test3DES();
//        testAES();
        try {
//            testDH();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
        try {
            testRSA();
        } catch (Exception e) {
            Log.e(TAG, "onCreate: " + e.getMessage());
        }
    }

    /**
     * 测试RSA加密-->非对称加密
     */
    private void testRSA() throws Exception {
        Map<String, Object> keyMap = RSAUtil.initKey();
        RSAPublicKey publicKey = RSAUtil.getPublicKey(keyMap);
        RSAPrivateKey privateKey = RSAUtil.getPrivateKey(keyMap);
        System.out.println("RSA publicKey: " + publicKey);
        System.out.println("RSA privateKey: " + privateKey);

        //加密后的数据
        byte[] encryptResult = RSAUtil.encrypt(DATA.getBytes(), publicKey);
        System.out.println(DATA + " RSA 加密: " + ByteToHexUtil.fromByteToHex(encryptResult));

        //解密后的数据
        byte[] decryptResult = RSAUtil.decrypt(encryptResult, privateKey);
        System.out.println(DATA + " RSA 解密: " + new String(decryptResult));
    }

    /**
     * 测试DH加密-->非对称加密
     */
    private void testDH() throws Exception {
        //甲方公钥
        byte[] publicKeyA;
        //甲方私钥
        byte[] privateKeyA;
        //甲方本地密钥
        byte[] secretKeyA;
        //乙方公钥
        byte[] publicKeyB;
        //乙方私钥
        byte[] privateKeyB;
        //乙方本地密钥
        byte[] secretKeyB;

        //初始化密钥并生成甲方密钥对
        Map<String, Object> keyMapA = DHUtil.initKey();
        publicKeyA = DHUtil.getPublicKey(keyMapA);
        privateKeyA = DHUtil.getPrivateKey(keyMapA);
        System.out.println("DH 甲方公钥: " + ByteToHexUtil.fromByteToHex(publicKeyA));
        System.out.println("DH 甲方私钥: " + ByteToHexUtil.fromByteToHex(privateKeyA));

        //乙方根据甲方公钥生成乙方密钥对
        Map<String, Object> keyMapB = DHUtil.initKey(publicKeyA);
        publicKeyB = DHUtil.getPublicKey(keyMapB);
        privateKeyB = DHUtil.getPrivateKey(keyMapB);
        System.out.println("DH 乙方公钥: " + ByteToHexUtil.fromByteToHex(publicKeyB));
        System.out.println("DH 乙方私钥: " + ByteToHexUtil.fromByteToHex(privateKeyB));

        //对于甲方,根据乙方公钥和自己的私钥生成本地密钥 secretKeyA
        secretKeyA = DHUtil.getSecretKey(publicKeyB, privateKeyA);
        //对于乙方,根据其甲公钥和自己的私钥生成本地密钥 secretKeyB
        secretKeyB = DHUtil.getSecretKey(publicKeyA, privateKeyB);

        System.out.println("DH 甲方本地密钥: " + ByteToHexUtil.fromByteToHex(secretKeyA));
        System.out.println("DH 乙方本地密钥: " + ByteToHexUtil.fromByteToHex(secretKeyB));
    }

    /**
     * 测试AES加密-->对称加密
     */
    private void testAES() {
        try {
            /**
             * 密钥
             */
            byte[] aesKey = AESUtil.initKey();
            System.out.println(DATA + "AES key: " + ByteToHexUtil.fromByteToHex(aesKey));
            /**
             * 加密后的数据
             */
            byte[] encryptResult = AESUtil.encrypt(DATA.getBytes(), aesKey);
            System.out.println(DATA + "AES 加密: " + ByteToHexUtil.fromByteToHex(encryptResult));
            /**
             * 解密后的数据
             */
            byte[] decryptResult = AESUtil.decrypt(encryptResult, aesKey);
            System.out.println(DATA + "AES 解密: " + new String(decryptResult));
        } catch (Exception e) {
            Log.e(TAG, "testAES: " + e.getMessage());
        }
    }

    /**
     * 测试3DES加密-->对称加密
     */
    private void test3DES() {
        try {
            /**
             * 密钥
             */
            byte[] tripleKey = TripleDESUtil.initKey();
            System.out.println(DATA + "3DES key: " + ByteToHexUtil.fromByteToHex(tripleKey));
            /**
             * 加密后的数据
             */
            byte[] encryptResult = TripleDESUtil.encrypt(DATA.getBytes(), tripleKey);
            System.out.println(DATA + "3DES 加密: " + ByteToHexUtil.fromByteToHex(encryptResult));
            /**
             * 解密后的数据
             */
            byte[] decryptResult = TripleDESUtil.decrypt(encryptResult, tripleKey);
            System.out.println(DATA + "3DES 解密: " + new String(decryptResult));
        } catch (Exception e) {
            Log.e(TAG, "test3DES: " + e.getMessage());
        }
    }

    /**
     * 测试DES加密-->对称加密
     */
    private void testDES() {
        /**
         * DES 加密
         */
        byte[] desKey = DESUtil.initKey();
        System.out.println("DES key: " + ByteToHexUtil.fromByteToHex(desKey));
        /**
         * 加密后的数据
         */
        byte[] desResult = DESUtil.encrypt(DATA.getBytes(), desKey);
        System.out.println(DATA + "DES 加密>>>" + ByteToHexUtil.fromByteToHex(desResult));

        /**
         * DES 解密
         */
        byte[] decryptResult = DESUtil.decrypt(desResult, desKey);
        System.out.println(DATA + "DES 解密" + new String(decryptResult));
    }
}
