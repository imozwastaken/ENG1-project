package com.mygdx.game.Powerups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.PiazzaPanic;

import java.util.ArrayList;
import java.util.HashMap;

public class Powerups {
    HashMap<String, Powerup> allPowerups = new HashMap<String, Powerup>();

    BitmapFont font;
    private PiazzaPanic game;
    public Powerups(PiazzaPanic _game) {
        this.game = _game;
        Powerup speedPowerup = new Powerup("Speed", 5000, 1, 1);
        allPowerups.put("Speed", speedPowerup);
        this.font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(0.25f);
    }

    public float getSpeedMultiplier() {
        return allPowerups.get("Speed").getValue();
    }
    public void setSpeedMultiplier(int multiplier) {
        allPowerups.get("Speed").setInitialisedTime(System.currentTimeMillis());
        allPowerups.get("Speed").setValue(multiplier);
        System.out.println("Speed initialised");

    }

    public void checkPowerups() {
        for (String k: allPowerups.keySet()) {
            if (allPowerups.get(k).getInitialisedTime() != 0) {
                if (allPowerups.get(k).getExpiryTime() < System.currentTimeMillis()) {
                    allPowerups.get(k).setValue(1);
                    allPowerups.get(k).setInitialisedTime(0);
                    System.out.println(k + " has been stopped");
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
