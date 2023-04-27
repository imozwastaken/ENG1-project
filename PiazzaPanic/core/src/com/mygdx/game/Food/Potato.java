package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Stack;

public class Potato implements Recipe{
    ArrayList<Ingredient> recipe;
    Texture potatoTex;
    Texture speechBubble;
    boolean isBurnt = false;
    public Potato() {
        this.recipe = new ArrayList<Ingredient>();
        Ingredient potato = new Ingredient("potato", new Texture("potato.png"), new Texture("potatoCooked.png"), new Texture("burntPotato.png"));
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
        Ingredient potato = new Ingredient("potato", null, null, null);
        potato.prepare();
        for (Ingredient ingredient: ingredients) {

            if (ingredient.equals(potato)) {
                System.out.println("Found potato");
                foundPotato = true;
            } else {
                System.out.println(ingredient.name);
                System.out.println(potato.name);
            }
        }
        return foundPotato;
    }

    public void setBurnt() {
        isBurnt = true;
        potatoTex = new Texture("burntPotato.png");
    }

    public Boolean getIsBurnt() {
        return isBurnt;
    }
}
