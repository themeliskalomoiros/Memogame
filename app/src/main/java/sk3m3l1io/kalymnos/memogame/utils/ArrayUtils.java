package sk3m3l1io.kalymnos.memogame.utils;

import java.util.Random;

public final class ArrayUtils {
    public static void shuffle(String[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String s = array[index];
            array[index] = array[i];
            array[i] = s;
        }
    }
}
