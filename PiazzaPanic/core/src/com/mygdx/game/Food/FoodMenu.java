package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import java.io.IOException;
import java.util.ArrayList;

public class FoodMenu {

    private final ArrayList<Order> orderOptions;
    public boolean bakingUnlocked = false;
    public FoodMenu(boolean _bakingUnlocked) throws IOException {
        this.orderOptions = new ArrayList<>();

        // Add order options here
        this.orderOptions.add(new Order("burger", new Texture("orderBurger.png"), new Burger()));
        this.orderOptions.add(new Order("salad", new Texture("orderSalad.png"), new Salad()));
        this.orderOptions.add(new Order("potato", new Texture("orderPotato.png"), new Potato()));
        this.orderOptions.add(new Order("pizza", new Texture("orderPizza.png"), new Pizza()));
        bakingUnlocked = _bakingUnlocked;
    }

    public ArrayList<Order> getOrderOptions() {
        return orderOptions;
    }

    public Order getRandomOrder() {
//        return orderOptions.get(2);
        if (bakingUnlocked) {
            return orderOptions.get(MathUtils.random(0, 3));
        } else {
            return  orderOptions.get(MathUtils.random(0, 1));
        }

    }

    // Uncomment when logic added for adding a baking station
    public void addNewMenuItems() {
        // orderOptions.add(new Order("pizza", new Texture("orderPizza.png"), new
        // Pizza()));
    }
}
