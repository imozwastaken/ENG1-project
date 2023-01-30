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

    public Ingredient(String name, Texture notPreparedTexture, Texture preparedTexture) {
        this.name = name;
        this.prepdTex = preparedTexture;
        this.notPrepdTex = notPreparedTexture;
        currentTex = this.notPrepdTex;
    }

    public boolean getState() {
        return prepared;
    }

    public void prepare() {
        this.prepared = true;
    }

    public void updateCurrentTexture() {
        if (prepared) {
            currentTex = this.prepdTex;
        } else {
            currentTex = this.notPrepdTex;
        }
    }

    @Override
    public boolean equals(Object ingredient) {
        Ingredient compare = (Ingredient) ingredient;
        if (this.name == compare.name) {
            return this.prepared == compare.prepared;
        }
        return false;
    }

    public Texture getCurrentTexture() {
        return currentTex;
    }
}
