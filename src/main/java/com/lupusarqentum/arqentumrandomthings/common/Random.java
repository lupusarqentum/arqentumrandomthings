package com.lupusarqentum.arqentumrandomthings.common;

public class Random {

    private static java.util.Random gen;
    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            throw new RuntimeException("random gen is already initialized");
        }
        gen = new java.util.Random();
        initialized = true;
    }

    public static float nextFloat() {
        return gen.nextFloat();
    }

    public static int nextInt(int min_inclusive, int max_inclusive) {
        return gen.nextInt() % (max_inclusive - min_inclusive + 1) + min_inclusive;
    }
}
