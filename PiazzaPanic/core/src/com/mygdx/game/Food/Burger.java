package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Stack;

// burger recipe made up of Ingredients
public class Burger implements Recipe {
    ArrayList<Ingredient> recipe;
    Texture bugerTex;
    Texture speechBubble;

    boolean isBurnt = false;

    public Burger() {
        this.recipe = new ArrayList<Ingredient>();
        Ingredient patty = new Ingredient("patty", new Texture("rawPatty.png"), new Texture("prepdPatty.png"), new Texture("burntPatty.png"));
        patty.prepare();
        recipe.add(patty);
        Ingredient buns = new Ingredient("buns", new Texture("buns.png"), new Texture("buns.png"), null);
        buns.prepare();
        recipe.add(buns);
        Ingredient lettuce = new Ingredient("lettuce", new Texture("lettuce.png"), new Texture("prepdLettuce.png"), null);
        lettuce.prepare();
        recipe.add(lettuce);
        this.bugerTex = new Texture("burger.png");
        this.speechBubble = new Texture("orderBurgerBubble.png");
    }

    @Override
    public ArrayList<Ingredient> getRecipe() {
        return recipe;
    }

    @Override
    public Texture getTexture() {
        return bugerTex;
    }

    @Override
    public Texture getSpeechBubbleTexture() {
        return speechBubble;
    }

    public Boolean getIsBurnt() { return isBurnt; }

    @Override
    public Boolean has(Stack<Ingredient> ingredients) {
        boolean foundBuns = false;
        boolean foundPatty = false;
        boolean foundLettuce = false;
        Ingredient buns = new Ingredient("buns", null, null, null);
        buns.prepare();
        Ingredient patty = new Ingredient("patty", null, null, null);
        patty.prepare();
        Ingredient lettuce = new Ingredient("lettuce", null, null, null);
        lettuce.prepare();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.equals(buns)) {
                foundBuns = true;
            }
            if (ingredient.equals(patty)) {
                foundPatty = true;
                if (patty.getBurnt()) {
                    isBurnt = true;
                }
            }
            if (ingredient.equals(lettuce)) {
                foundLettuce = true;
            }
        }
        return foundBuns && foundLettuce && foundPatty;
    }
}
