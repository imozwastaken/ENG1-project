package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

public class Order {
    Texture orderTex;
    Recipe orderRecipe;
    String name;
    //default order time, 40 seconds
    public Integer orderTime = 40;
    
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

    public Integer getOrderTime(){
        return this.orderTime;
    }

    public void setOrderTime(Integer orderTime){
        this.orderTime = orderTime;
    }
}
