package com.mygdx.game;

public class Tank {
    private float health;
    private float fuel;
    private int powerups;
    public Tank(float health, int fuel, int powerups){
        this.health = health;
        this.fuel = fuel;
        this.powerups = powerups;
    }

    float max(float a, float b){
        return a>b?a:b;
    }
    public float getHealth(){
        return health;
    }
    public float getFuel(){
        return fuel;
    }
    public void setFuel(float f){
        this.fuel = max(this.fuel-f,0f);
    }

    public void fuelRefill(){
        this.fuel=1f;
    }
    public int getPowerups(){
        return powerups;
    }

    public void setHealth(float h){
        this.health-=h;
    }
}
