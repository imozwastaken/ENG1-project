package com.mygdx.game.Food;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

//Salad recipe made up of Ingredients
public class Salad implements Recipe{
    ArrayList<Ingredient> recipe;
    Texture saladTex;
    Texture speechBubble;

    public Salad(){
        this.recipe = new ArrayList<Ingredient>();
        
        Ingredient lettuce = new Ingredient("lettuce", new Texture("lettuce.png"), new Texture("prepdLettuce.png"));
        lettuce.prepare();
        recipe.add(lettuce);
        Ingredient tomato = new Ingredient("tomato", new Texture("tomato.png"), new Texture("prepdTomato.png"));
        tomato.prepare();
        recipe.add(tomato);

        this.saladTex = new Texture("salad.png");
        this.speechBubble = new Texture("orderSaladBubble.png");
    }
    @Override
    public ArrayList<Ingredient> getRecipe() {
        return recipe;
    }
    @Override
    public Texture getTexture() {
        return saladTex;
    }
    @Override
    public Texture getSpeechBubbleTexture() {
        return speechBubble;
    }
}
