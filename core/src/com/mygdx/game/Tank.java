package com.mygdx.game;

public class Tank {
    int health;
    int fuel;
    int powerups;
    public Tank(int health, int fuel, int powerups){
        this.health = health;
        this.fuel = fuel;
        this.powerups = powerups;
    }
    public int getHealth(){
        return health;
    }
    public int getFuel(){
        return fuel;
    }
    public int getPowerups(){
        return powerups;
    }
}
