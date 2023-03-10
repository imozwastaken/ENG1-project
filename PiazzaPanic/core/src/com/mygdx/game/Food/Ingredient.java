package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

// class for an ingredient
public class Ingredient {
    private final Texture prepdTex;
    private final Texture notPrepdTex;
    // patty, buns, lettuce, tomato
    public String name;
    private Texture currentTex;
    private Boolean prepared = false;

    /**
     * Constructor for the Ingredient class
     */
    public Ingredient(String name, Texture notPreparedTexture, Texture preparedTexture) {
        this.name = name;
        this.prepdTex = preparedTexture;
        this.notPrepdTex = notPreparedTexture;
        currentTex = this.notPrepdTex;
    }

    /**
     * Getter function to return if ingredient is prepared
     * @return the boolean value for whether the ingredient is prepared or not
     */
    public boolean getState() {
        return prepared;
    }

    /**
     * Prepares an ingredient
     * @return the true boolean value for the ingredient being prepared
     */
    public void prepare() {
        this.prepared = true;
    }

    /**
     * Function to update the texture to either look prepared or not depending on whether the ingredient is prepared or not
     */
    public void updateCurrentTexture() {
        if (prepared) {
            currentTex = this.prepdTex;
        } else {
            currentTex = this.notPrepdTex;
        }
    }

    /**
     * Check function to check if ingredient matches required ingredient
     * @return the boolean value for whether the ingredient matches the required ingredient
     */
    @Override
    public boolean equals(Object ingredient) {
        Ingredient compare = (Ingredient) ingredient;
        if (this.name == compare.name) {
            return this.prepared == compare.prepared;
        }
        return false;
    }

    /**
     * Getter function to return the current texture of the ingredient
     * @return the texture for ingredient
     */
    public Texture getCurrentTexture() {
        return currentTex;
    }
}
