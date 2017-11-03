package ovh.kocproz.markpages;

import java.util.Random;

public class Util {
    public static String randomString(int length) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        Random random = new Random();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < length; i++) {
            out.append(chars[random.nextInt(chars.length)]);
        }
        return out.toString();
    }

}
