package com.mygdx.game.Food;

import java.util.ArrayList;
import java.util.Stack;

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
    public Boolean has(Stack<Ingredient> ingredients) {
        boolean foundTomato = false;
        boolean foundLettuce = false;
        Ingredient tomato = new Ingredient("tomato", null,null);
        tomato.prepare();
        Ingredient lettuce = new Ingredient("lettuce", null,null);
        lettuce.prepare();
        for(Ingredient ingredient : ingredients){
            if(ingredient.equals(tomato)){
                foundTomato = true;
            }
            if(ingredient.equals(lettuce)){
                foundLettuce = true;
            }
        }
        if(foundTomato&&foundLettuce){
            return true;
        }
        return false;
    }
}
