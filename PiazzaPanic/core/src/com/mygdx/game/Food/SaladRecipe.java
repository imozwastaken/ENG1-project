package com.mygdx.game.Food;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

//Salad recipe made up of Ingredients
public class SaladRecipe {
    ArrayList<Ingredient> recipe;
    public SaladRecipe(){
        Ingredient lettuce = new Ingredient("lettuce", new Texture("lettuce.png"), new Texture("prepdLettuce.png"));
        lettuce.prepare();
        recipe.add(lettuce);
        Ingredient tomato = new Ingredient("tomato", new Texture("tomato.png"), new Texture("prepdTomato.png"));
        tomato.prepare();
        recipe.add(tomato);
    }
}
