package com.mygdx.game.Powerups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.Money;
import com.mygdx.game.PiazzaPanic;

import java.util.ArrayList;
import java.util.HashMap;

public class Powerups {
    HashMap<String, Powerup> allPowerups = new HashMap<String, Powerup>();

    BitmapFont font;
    Money money;
    private PiazzaPanic game;
    public Powerups(PiazzaPanic _game, Money _money) {
        this.game = _game;
        Powerup speedPowerup = new Powerup("Speed", 5000, 0, 1, 100, 0);
        Powerup extraLife = new Powerup("ExtraLife", 1000000000, 0, 1, 300, 0);
        Powerup stationSpeed = new Powerup("FastStations", 5000, 0, 1, 300, 0);
        allPowerups.put("Speed", speedPowerup);
        allPowerups.put("ExtraLife", extraLife);
        allPowerups.put("FastStations", stationSpeed);
        this.font = new BitmapFont();
        this.money = _money;

        font.setColor(Color.BLACK);
        font.getData().setScale(0.25f);

    }

    public float getSpeedMultiplier() {
        return allPowerups.get("Speed").getValue();
    }
    public void setSpeedMultiplier(int multiplier) {
        boolean bought = buyPowerup("Speed");
        if (bought) {
            allPowerups.get("Speed").setInitialisedTime(System.currentTimeMillis());
            allPowerups.get("Speed").setValue(multiplier);
        } else {
            System.out.println("Not enough money for this.");
        }

    }

    public float getStationSpeed() {
        return allPowerups.get("FastStations").getValue();
    }

    public void setStationSpeed(float multiplier) {
        boolean bought = buyPowerup("FastStations");
        if (bought) {
            allPowerups.get("FastStations").setInitialisedTime(System.currentTimeMillis());
            allPowerups.get("FastStations").setValue(multiplier);
        } else {
            System.out.println("Not enough money for this.");
        }
    }

    public boolean buyRep() {
        if (allPowerups.get("ExtraLife").getTimesBought() > 0) {
            return false;
        }
        boolean bought = buyPowerup("ExtraLife");
        if (!bought) {
            System.out.println("invalid");
        }
        return bought;
    }

    public boolean buyPowerup(String powerup) {
        int price = allPowerups.get(powerup).getPrice();

        if (price <= money.getCurrentMoney() && (allPowerups.get(powerup).getInitialisedTime() == 0)) {
            money.removeMoney(price);
            allPowerups.get(powerup).incrementTimesBought();
            return true;
        } else {
            return false;
        }
    }

    public void resetPowerup(String k) {
        allPowerups.get(k).setInitialisedTime(0);
        allPowerups.get(k).setValue(1);
    }

    public void checkPowerups() {
        for (String k: allPowerups.keySet()) {
            if (allPowerups.get(k).getInitialisedTime() != 0) {
                if (allPowerups.get(k).getExpiryTime() < System.currentTimeMillis()) {
                    resetPowerup(k);
                }
            }
        }
    }

    public void render() {
        String message = "Powerups active : ";
        StringBuilder messageBuilder = new StringBuilder(message);
        for (String k: allPowerups.keySet()) {
            if (allPowerups.get(k).getInitialisedTime() != 0) {
                messageBuilder.append(" ").append(k);
            }
        }
        message = messageBuilder.toString();
        game.batch.begin();
        font.draw(game.batch, message, 127, 122);
        game.batch.end();
    }


}
