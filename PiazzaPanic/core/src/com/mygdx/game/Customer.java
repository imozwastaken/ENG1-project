package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Food.Burger;
import com.mygdx.game.Food.Order;
import com.mygdx.game.Food.Salad;


public class Customer {
    public boolean orderComplete = false;
    public boolean atCounter = false;
    public Actor body;
    public String name;
    public Order customerOrder;
    private ArrayList<Order> orderOptions = new ArrayList<Order>();
    private final float targetY = MathUtils.random(16, 48);

    public Customer(Actor skin){
        String[] names = {"Blue", "Red", "White", "Yellow"};
        this.name = names[MathUtils.random(0,3)];
        this.body = skin;
        this.body.setWidth(16);
        this.body.setHeight(23);
        this.body.setX(144);
        this.body.setY(80);

        orderOptions.add(new Order(new Texture("orderBurger.png"), new Burger()));
        orderOptions.add(new Order(new Texture("orderSalad.png"), new Salad()));

        this.customerOrder = generateOrder();
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
    private Order generateOrder(){
        return orderOptions.get(MathUtils.random(0,1));
    }
}
