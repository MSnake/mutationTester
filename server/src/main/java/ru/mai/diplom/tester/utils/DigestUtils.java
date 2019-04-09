package ru.mai.diplom.tester.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class DigestUtils {

    public static String getMd5(String str) {
        if (str == null) {
            return null;
        } else {
            MessageDigest messageDigest = null;
            byte[] digest = new byte[0];

            try {
                messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.reset();
                messageDigest.update(str.getBytes("UTF-8"));
                digest = messageDigest.digest();
            } catch (Exception var5) {
                throw new RuntimeException("Error md5", var5);
            }

            BigInteger bigInt = new BigInteger(1, digest);

            String md5Hex;
            for (md5Hex = bigInt.toString(16); md5Hex.length() < 32; md5Hex = "0" + md5Hex) {
                ;
            }

            return md5Hex;
        }
    }
}