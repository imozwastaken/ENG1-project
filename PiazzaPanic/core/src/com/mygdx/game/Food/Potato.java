package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Stack;

public class Potato implements Recipe{
    ArrayList<Ingredient> recipe;
    Texture potatoTex;
    Texture speechBubble;
    public Potato() {
        this.recipe = new ArrayList<Ingredient>();
        Ingredient potato = new Ingredient("potato", new Texture("potato.png"), new Texture("potatoCooked.png"));
        potato.prepare();
        recipe.add(potato);
        this.potatoTex = new Texture("potatoCooked.png");
        this.speechBubble = new Texture("orderPotatoBubble.png");
    }
    @Override
    public ArrayList<Ingredient> getRecipe() {
        return recipe;
    }

    @Override
    public Texture getTexture() {
        return potatoTex;
    }

    @Override
    public Texture getSpeechBubbleTexture() {
        return speechBubble;
    }

    @Override
    public Boolean has(Stack<Ingredient> ingredients) {
        boolean foundPotato = false;
        Ingredient potato = new Ingredient("potato", null, null);
        potato.prepare();
        for (Ingredient ingredient: ingredients) {
            if (ingredient.equals(potato)) {
                foundPotato = true;
            }
        }
        return foundPotato;
    }
}
