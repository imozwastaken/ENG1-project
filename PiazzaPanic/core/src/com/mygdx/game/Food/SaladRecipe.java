package com.mygdx.game.Food;

import java.util.ArrayList;

//Salad recipe made up of Ingredients
public class SaladRecipe {
    ArrayList<Ingredient> recipe;
    public SaladRecipe(){
        Ingredient lettuce = new Ingredient("lettuce", null, null);
        lettuce.prepare();
        recipe.add(lettuce);
        Ingredient tomato = new Ingredient("tomato", null, null);
        tomato.prepare();
        recipe.add(tomato);
    }
}
