package com.mygdx.game.Food;

import java.util.ArrayList;

//Burger recipe made up of Ingredients
public class BurgerRecipe {
    ArrayList<Ingredient> recipe;
    public BurgerRecipe(){
        this.recipe = new ArrayList<Ingredient>();
        Ingredient patty = new Ingredient("patty", null, null);
        patty.prepare();
        recipe.add(patty);
        Ingredient buns = new Ingredient("buns", null, null);
        buns.prepare();
        recipe.add(buns);
        Ingredient lettuce = new Ingredient("lettuce", null, null);
        lettuce.prepare();
        recipe.add(lettuce);
    }
}
