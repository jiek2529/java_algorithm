package com.sample.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
            byte[] key = generateDesKey(128);

            byte[] bytes1 = "abcdefghijklmnop".getBytes();//new byte[16];
            IvParameterSpec algorithmParameterSpec = new IvParameterSpec(bytes1);

            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, algorithmParameterSpec);

            byte[] bytes = cipher.doFinal(originalString.getBytes());
            String ens = new String(bytes);

            System.out.println(originalString + " > encrypt : "+ens);

            Cipher cipher1 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher1.init(Cipher.DECRYPT_MODE, skeySpec, algorithmParameterSpec);

            System.out.println("decrypt: "+new String(cipher1.doFinal(bytes)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] generateDesKey(int length) throws Exception {
        //实例化
        KeyGenerator kgen = null;
        kgen = KeyGenerator.getInstance("AES");
        //设置密钥长度
        kgen.init(length);
        //生成密钥
        SecretKey skey = kgen.generateKey();
        //返回密钥的二进制编码
        return skey.getEncoded();
    }
}
