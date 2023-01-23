package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.mygdx.game.Food.Ingredient;


public class Customer {
    public boolean orderComplete = false;
    public boolean atCounter = false;
    public Actor body;
    public String name;
    private final float targetY = MathUtils.random(16, 48);

    public Customer(Actor skin){
        String[] names = {"Blue", "Red", "White", "Yellow"};
        this.name = names[MathUtils.random(0,3)];
        this.body = skin;
        this.body.setWidth(16);
        this.body.setHeight(23);
        this.body.setX(144);
        this.body.setY(80);
    }

    public void move() {
        // method to move a cook from their current position to a station
        //System.out.println(locations[index][0]);
        if (!atCounter) {
            if (body.getY() != targetY) {
                body.setY(body.getY() - 1);
            } else{
                if (body.getX() != 128){
                    body.setX(body.getX()-1);
                } else {
                    atCounter = true;
                }
            }
        } else if(orderComplete){
            body.setX(body.getX()+1);
        }
    }
}
