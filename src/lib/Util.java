package lib;

import java.util.Random;

public class Util {
    public static int randomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
