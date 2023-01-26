package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

public class Order {
    Texture orderTex;
    Recipe orderRecipe;
    
    public Order(Texture orderTexture, Recipe orderRecipe){
        this.orderTex = orderTexture;
        this.orderRecipe = orderRecipe;
    }

    public Texture getOrderTexture(){
        return this.orderTex;
    }

    public Recipe getRecipe(){
        return this.orderRecipe;
    }
}
