package com.lupusarqentum.arqentumrandomthings.common;

public class Random {

    private static java.util.Random gen = new java.util.Random();

    public static boolean rollProbability(float probability) { return nextFloat() < probability; }

    public static float nextFloat() {
        return gen.nextFloat();
    }

    public static int nextInt(int min_inclusive, int max_inclusive) {
        if (min_inclusive > max_inclusive) {
            throw new RuntimeException("invalid arguments");
        }

        int generated = gen.nextInt();
        if (generated < 0) {
            generated *= -1;
        }

        return generated % (max_inclusive - min_inclusive + 1) + min_inclusive;
    }

    public static int nextInt(int max_inclusive) {
        return nextInt(0, max_inclusive);
    }
}
