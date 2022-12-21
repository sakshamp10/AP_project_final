package com.saksham.game;

public class HealthGenerator {
    float low;
    float high;
    private static HealthGenerator instance = null;
    public static HealthGenerator getInstance() {
        if (instance == null) {
            instance = new HealthGenerator();
        }
        return instance;
    }
    private HealthGenerator(){
        low = 0;
        high = 1;

    }
    public float getMaxHealth(){
        return high;
    }
}