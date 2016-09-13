package com.example.asiatravel.learndes.util;

/**
 * Created by kuangxiaoguo on 16/9/6.
 * <p/>
 * 将字节数组转化为16进制的工具类
 */
public class ByteToHexUtil {

    public static String fromByteToHex(byte[] resultBytes) {
        StringBuilder builder = new StringBuilder();
        for (byte resultByte : resultBytes) {
            if (Integer.toHexString(0xFF & resultByte).length() == 1) {
                builder.append(0).append(Integer.toHexString(0xFF & resultByte));
            } else {
                builder.append(Integer.toHexString(0xFF & resultByte));
            }
        }
        return builder.toString();
    }

}
