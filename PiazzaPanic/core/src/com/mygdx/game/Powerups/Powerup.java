package com.mygdx.game.Powerups;

public class Powerup {
    private String name;
    private long duration;
    private long initialisedTime;
    private float value;
    private int price;
    private int timesBought;

    public Powerup(String name, long duration, long initialisedTime, float value, int price, int timesBought,
            int _priceIncrease) {
        this.name = name;
        this.duration = duration;
        this.initialisedTime = initialisedTime;
        this.value = value;
        this.price = price + _priceIncrease;
        this.timesBought = timesBought;

    }

    public void incrementTimesBought() {
        timesBought++;
    }

    public int getTimesBought() {
        return timesBought;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float _value) {
        value = _value;
    }

    public void setInitialisedTime(long initialisedTime) {
        this.initialisedTime = initialisedTime;
    }

    public int getPrice() {
        return price;
    }

    public long getDuration() {
        return duration;
    }

    public long getInitialisedTime() {
        return initialisedTime;
    }

    public long getExpiryTime() {
        return initialisedTime + duration;
    }

}
