package com.mygdx.game.Food;

import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.graphics.Texture;

public interface Recipe {
    public ArrayList<Ingredient> getRecipe();
    public Texture getTexture();
    public Texture getSpeechBubbleTexture();
    public Boolean has(Stack<Ingredient> ingredients);
}
