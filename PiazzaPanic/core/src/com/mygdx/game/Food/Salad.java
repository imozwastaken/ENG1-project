package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The class Salad represents the salad item in the game, which inherits from the recipe interface
 */
public class Salad implements Recipe {
    ArrayList<Ingredient> saladRecipe;
    Texture saladTexture;
    Texture speechBubble;

    /**
     * Constructor for the Salad class
     */
    public Salad() {
        this.saladRecipe = new ArrayList<Ingredient>();

        Ingredient lettuce = new Ingredient("lettuce", new Texture("lettuce.png"), new Texture("prepdLettuce.png"));
        lettuce.prepare();
        saladRecipe.add(lettuce);
        Ingredient tomato = new Ingredient("tomato", new Texture("tomato.png"), new Texture("prepdTomato.png"));
        tomato.prepare();
        saladRecipe.add(tomato);

        this.saladTexture = new Texture("salad.png");
        this.speechBubble = new Texture("orderSaladBubble.png");
    }

    /**
     * Getter function for the saladRecipe attribute
     * @return the recipe array for the recipe
     */
    @Override
    public ArrayList<Ingredient> getRecipe() {
        return saladRecipe;
    }

    /**
     * Getter function for the saladTexture attribute
     * @return the texture
     */
    @Override
    public Texture getTexture() {
        return saladTexture;
    }

    /**
     * Getter function for the speechBubble attribute
     * @return the speech bubble
     */
    @Override
    public Texture getSpeechBubbleTexture() {
        return speechBubble;
    }

    /**
     * Function for checking if a chef has all the ingredients to make a Salad
     * @return the boolean true or false for if the chef has all ingredients
     */
    public Boolean has(Stack<Ingredient> ingredients) {
        boolean foundTomato = false;
        boolean foundLettuce = false;
        Ingredient tomato = new Ingredient("tomato", null, null);
        tomato.prepare();
        Ingredient lettuce = new Ingredient("lettuce", null, null);
        lettuce.prepare();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.equals(tomato)) {
                foundTomato = true;
            }
            if (ingredient.equals(lettuce)) {
                foundLettuce = true;
            }
        }
        return foundTomato && foundLettuce;
    }
}
