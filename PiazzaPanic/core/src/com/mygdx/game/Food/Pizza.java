package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Stack;

public class Pizza implements Recipe {
    ArrayList<Ingredient> recipe;
    Texture pizzaTex;
    Texture speechBubble;

    public Pizza() {
        this.recipe = new ArrayList<Ingredient>();
        Ingredient pizzaBase = new Ingredient("pizza", new Texture("rawPizza.png"), new Texture("prepdPizza.png"));
        pizzaBase.prepare();
        recipe.add(pizzaBase);
        Ingredient lettuce = new Ingredient("lettuce", new Texture("lettuce.png"), new Texture("prepdLettuce.png"));
        lettuce.prepare();
        recipe.add(lettuce);

        this.pizzaTex = new Texture("pizza.png");
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
        Ingredient pizzaBase = new Ingredient("pizza", null, null);
        pizzaBase.prepare();
        Ingredient lettuce = new Ingredient("lettuce", null, null);
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
}
