package com.mygdx.game.Food;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

//Burger recipe made up of Ingredients
public class BurgerRecipe {
    ArrayList<Ingredient> recipe;
    public BurgerRecipe(){
        this.recipe = new ArrayList<Ingredient>();
        Ingredient patty = new Ingredient("patty", new Texture("rawPatty.png"), new Texture("prepdPatty.png"));
        patty.prepare();
        recipe.add(patty);
        Ingredient buns = new Ingredient("buns", new Texture("buns.png"), new Texture("buns.png"));
        buns.prepare();
        recipe.add(buns);
        Ingredient lettuce = new Ingredient("lettuce", new Texture("lettuce.png"), new Texture("prepdLettuce.png"));
        lettuce.prepare();
        recipe.add(lettuce);
    }
}
