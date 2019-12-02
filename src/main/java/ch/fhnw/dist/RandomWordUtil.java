package ch.fhnw.dist;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class RandomWordUtil {

    private static Random random = new Random();

    public static String randomWord(int maxWordLength) {
        int length = random.nextInt(maxWordLength) + 1;
        return RandomStringUtils.random(length, true, false);
    }

}
