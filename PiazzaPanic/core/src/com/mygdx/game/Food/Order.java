package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

public class Order {
    Texture orderTex;
    Recipe orderRecipe;
    String name;
    
    public Order(String name,Texture orderTexture, Recipe orderRecipe){
        this.name = name;
        this.orderTex = orderTexture;
        this.orderRecipe = orderRecipe;
    }

    public Texture getOrderTexture(){
        return this.orderTex;
    }

    public Recipe getRecipe(){
        return this.orderRecipe;
    }
    
    public String getName(){
        return this.name;
    }
}
