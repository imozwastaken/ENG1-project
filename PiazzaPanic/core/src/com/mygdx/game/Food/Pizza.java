package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Stack;

public class Pizza implements Recipe {
    ArrayList<Ingredient> recipe;
    Texture pizzaTex;
    Texture speechBubble;

    boolean isBurnt;

    public Pizza() {
        this.recipe = new ArrayList<Ingredient>();
        Ingredient pizzaBase = new Ingredient("pizza", new Texture("rawPizza.png"), new Texture("prepdPizza.png"), null);
        pizzaBase.prepare();
        recipe.add(pizzaBase);
        Ingredient lettuce = new Ingredient("lettuce", new Texture("lettuce.png"), new Texture("prepdLettuce.png"), null);
        lettuce.prepare();
        recipe.add(lettuce);

        this.pizzaTex = new Texture("prepdPizza.png");
        this.speechBubble = new Texture("orderPizzaBubble.png");
    }
    @Override
    public ArrayList<Ingredient> getRecipe() {
        return recipe;
    }

    @Override
    public Texture getTexture() {
        return pizzaTex;
    }

    @Override
    public Texture getSpeechBubbleTexture() {
        return speechBubble;
    }

    @Override
    public Boolean has(Stack<Ingredient> ingredients) {
        boolean foundPizzaBase = false;
        boolean foundLettuce = false;
        Ingredient pizzaBase = new Ingredient("pizza", null, null, null);
        pizzaBase.prepare();
        Ingredient lettuce = new Ingredient("lettuce", null, null, null);
        lettuce.prepare();
        for (Ingredient ingredient: ingredients) {
            if (ingredient.equals(pizzaBase)) {
                foundPizzaBase = true;
            }
            if (ingredient.equals(lettuce)) {
                foundLettuce = true;
            }
        }
        return foundPizzaBase && foundLettuce;
    }

    public void setBurnt() {
        isBurnt = true;
        pizzaTex = new Texture("burntPizza.png");
    }

    public Boolean getIsBurnt() {
        return isBurnt;
    }
}
