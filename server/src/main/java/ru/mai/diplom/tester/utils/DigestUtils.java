package ru.mai.diplom.tester.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class DigestUtils {

    public static String getMd5(String str) {
        if (str == null) {
            return null;
        }

        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            digest = messageDigest.digest();
        } catch (Exception e) {
            throw new RuntimeException("Error md5", e);
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}