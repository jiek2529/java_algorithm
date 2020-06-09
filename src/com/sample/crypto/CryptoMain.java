package com.sample.crypto;

import com.sample.util.Util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * Created by jiek on 2020/6/9.
 */
public class CryptoMain {

    public static void main(String[] args) {
        testAESEncrypt("hello");
    }

    /**
     * 测试使用 AES 进行加解密
     */
    private static void testAESEncrypt(String originalString) {
        try {
            //当使用256字节密钥进行加解密可能会有问题，参见：https://blog.csdn.net/dling8/article/details/84061948
            byte[] key =
                    hexToByte("867ca046cf63a99632aa5e7a8572a3c5");
//                    generateDesKey(128);
            p("DesKey: ", key);

            String sks = "AES/CBC/PKCS5Padding";
            String cryptoMode = "AES";
            {//方式一
                byte[] rand = new byte[16];
                SecureRandom r = new SecureRandom(); r.nextBytes(rand);//ali android 规范v9中推荐使用此方式随机生成矢量参数
//                p(bytes1);
                IvParameterSpec algorithmParameterSpec = new IvParameterSpec(rand);

                SecretKeySpec skeySpec = new SecretKeySpec(key, cryptoMode);

                Cipher cipher = Cipher.getInstance(sks);
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, algorithmParameterSpec);

                byte[] bytes = cipher.doFinal(originalString.getBytes());
                p(bytes);

                System.out.println(originalString + " > encrypt : " + new String(bytes));

                Cipher cipher1 = Cipher.getInstance(sks);
                cipher1.init(Cipher.DECRYPT_MODE, skeySpec, algorithmParameterSpec);

                System.out.println("decrypt: " + new String(cipher1.doFinal(bytes)));
            }
            {//方式二
                Util.splitLine();
                IvParameterSpec algorithmParameterSpec = new IvParameterSpec(new byte[16]);

                SecretKeySpec skeySpec = new SecretKeySpec(key, cryptoMode);

                Cipher cipher = Cipher.getInstance(sks);
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, algorithmParameterSpec);

                byte[] bytes = cipher.doFinal(originalString.getBytes());
                p(bytes);

                System.out.println(originalString + " > encrypt : " + new String(bytes));

                Cipher cipher1 = Cipher.getInstance(sks);
                cipher1.init(Cipher.DECRYPT_MODE, skeySpec, algorithmParameterSpec);

                System.out.println("decrypt: " + new String(cipher1.doFinal(bytes)));
            }
            {//方式三
                Util.splitLine();
                SecretKeySpec skeySpec = new SecretKeySpec(key, cryptoMode);

                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

                byte[] bytes = cipher.doFinal(originalString.getBytes());
                p(bytes);

                System.out.println(originalString + " > encrypt : " + new String(bytes));

                Cipher cipher1 = Cipher.getInstance("AES");
                cipher1.init(Cipher.DECRYPT_MODE, skeySpec);

                System.out.println("decrypt: " + new String(cipher1.doFinal(bytes)));
            }
//            方式一与方式二的差别在IvParameterSpec的参数值不同
//            方式二与方式三结果相同
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] generateDesKey(int length) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(length);
        SecretKey skey = kgen.generateKey();
        //返回密钥的二进制编码
        return skey.getEncoded();
    }

    private static void p(String msg, byte[] b) {
        if (b != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < b.length; i++) {
                sb.append(Integer.toHexString(b[i] & 0xff));
            }
            System.out.println(msg+" : "+sb);
        }
    }
    private static void p(byte[] b) {
        if (b != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < b.length; i++) {
                sb.append(" "+Integer.toHexString(b[i] & 0xff));
            }
            System.out.println(sb);
        }
    }

    /**
     * hex转byte数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex) {
        int m = 0, n = 0;
        int byteLen = hex.length() / 2;
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte) intVal);
        }
        return ret;
    }
}
