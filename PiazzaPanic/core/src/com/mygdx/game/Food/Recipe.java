package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Stack;

public interface Recipe {
    ArrayList<Ingredient> getRecipe();

    Texture getTexture();

    Texture getSpeechBubbleTexture();

    Boolean has(Stack<Ingredient> ingredients);
}
