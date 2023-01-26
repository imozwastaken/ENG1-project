package com.mygdx.game.Food;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

public interface Recipe {
    public ArrayList<Ingredient> getRecipe();
    public Texture getTexture();
    public Texture getSpeechBubbleTexture();
}
