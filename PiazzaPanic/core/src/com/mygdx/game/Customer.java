package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Food.FoodMenu;
import com.mygdx.game.Food.Order;

import java.io.IOException;

public class Customer {
    // god we love using arrays for dealing with this stuff

    private final float targetY = MathUtils.random(16, 48);
    public boolean orderComplete = false;
    public boolean selfComplete = false;
    public boolean atCounter = false;
    public boolean orderExpired = false;
    public Actor body;
    public String name;
    public Order customerOrder;
    public boolean bakingUnlocked = false;

    public Customer(Actor skin, boolean _bakingUnlocked) throws IOException {
        String[] names = { "Blue", "Red", "White", "Yellow" };
        this.name = names[MathUtils.random(0, 3)];
        this.body = skin;
        this.body.setWidth(16);
        this.body.setHeight(23);
        this.body.setX(144);
        this.body.setY(80);
        bakingUnlocked = _bakingUnlocked;

        // TODO add all possible orders here

        this.customerOrder = generateOrder();
    }

    public void move() {
        // method to move a cook from their current position to a station
        if (!atCounter) {
            if (body.getY() != targetY) {
                body.setY(body.getY() - 50 * Gdx.graphics.getDeltaTime());
                if (Math.abs(body.getY() - targetY) < 1) {
                    body.setY(targetY);
                }
            } else {
                // once close enough to the target positions snap the customer to it
                if (body.getX() != 128) {
                    body.setX(body.getX() - 1);
                    if (Math.abs(body.getX() - 128) < 1) {
                        body.setX(128);
                    }
                } else {
                    atCounter = true;
                }
            }
        } else if (selfComplete) {
            // if an order is complete, move the customer offscreen to the right
            body.setX(body.getX() + 50 * Gdx.graphics.getDeltaTime());
        } else {
            System.out.println("ORder isnt complete...");
        }
    }

    private Order generateOrder() throws IOException {
        FoodMenu menu = new FoodMenu(bakingUnlocked);
        return menu.getRandomOrder();
    }
}
