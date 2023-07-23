package com.lupusarqentum.arqentumrandomthings.common;

public class Random {

    private static java.util.Random gen = new java.util.Random();

    public static boolean rollProbability(double probability) { return nextDouble() < probability; }

    public static double nextDouble() {
        return gen.nextDouble();
    }

    public static int nextInt(int min_inclusive, int max_inclusive) {
        if (min_inclusive > max_inclusive) {
            Logger.error("Arqentum Random.nextInt received invalid arguments min & max:", min_inclusive, max_inclusive);
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
