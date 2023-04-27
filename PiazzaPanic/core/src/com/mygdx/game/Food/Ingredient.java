package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

// class for an ingredient
public class Ingredient {
    private final Texture prepdTex;
    private final Texture notPrepdTex;
    private final Texture burntTex;
    // patty, buns, lettuce, tomato
    public String name;
    private Texture currentTex;
    private Boolean prepared = false;

    private Boolean burnt = false;

    public Ingredient(String name, Texture notPreparedTexture, Texture preparedTexture, Texture burntTexture) {
        this.name = name;
        this.prepdTex = preparedTexture;
        this.notPrepdTex = notPreparedTexture;
        this.burntTex = burntTexture;
        currentTex = this.notPrepdTex;
    }

    public boolean getState() {
        return prepared;
    }

    public void prepare() {
        this.prepared = true;
    }

    public void setBurnt() { this.burnt = true; }

    public void updateCurrentTexture() {
        if (prepared && !burnt) {
            currentTex = this.prepdTex;
        } else if (prepared && burnt) {
            currentTex = this.burntTex;
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

    public Boolean getBurnt() {
        return burnt;
    }
}
