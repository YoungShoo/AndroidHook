package com.shoo.hook.util;

import java.security.MessageDigest;

public class MD5Utils {

    private static final String[] HEX_DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private MD5Utils() {
    }

    public static byte[] MD5Encode(byte[] origin) {
        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            return e.digest(origin);
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();

        for (int i = 0; i < b.length; ++i) {
            resultSb.append(byteToHexString(b[i], true));
        }

        return resultSb.toString();
    }

    private static String byteToHexString(byte b, boolean bigEnding) {
        int n = b;
        if (b < 0) {
            n = 256 + b;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return bigEnding ? HEX_DIGITS[d1] + HEX_DIGITS[d2] : HEX_DIGITS[d2] + HEX_DIGITS[d1];
    }
}