package com.mygdx.game.Food;

import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.graphics.Texture;

//Burger recipe made up of Ingredients
public class Burger implements Recipe{
    ArrayList<Ingredient> recipe;
    Texture bugerTex;
    Texture speechBubble;

    public Burger(){
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
    @Override
    public Boolean has(Stack<Ingredient> ingredients) {
        boolean foundBuns = false;
        boolean foundPatty = false;
        boolean foundLettuce = false;
        Ingredient buns = new Ingredient("buns", null,null);
        buns.prepare();
        Ingredient patty = new Ingredient("patty", null,null);
        patty.prepare();
        Ingredient lettuce = new Ingredient("lettuce", null,null);
        lettuce.prepare();
        for(Ingredient ingredient : ingredients){
            if(ingredient.equals(buns)){
                foundBuns = true;
            }
            if(ingredient.equals(patty)){
                foundPatty = true;
            }
            if(ingredient.equals(lettuce)){
                foundLettuce = true;
            }
        }
        if(foundBuns&&foundLettuce&&foundPatty){
            return true;
        }
        return false;
    }
}
