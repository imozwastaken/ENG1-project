package com.mygdx.game.Food;

import com.badlogic.gdx.graphics.Texture;

//Class for an ingredient
public class Ingredient {
    //patty, buns, lettuce, tomato
    public String name;
    public Texture prepdTex;
    public Texture notPrepdTex;
    public Texture currentTex;
    private Boolean prepared = false;

    public Ingredient(String name,Texture notPreparedTexture, Texture preparedTexture){
        this.name = name;
        this.prepdTex = preparedTexture;
        this.notPrepdTex = notPreparedTexture;
        currentTex = this.notPrepdTex;
    }

    public boolean getState(){
        return prepared;
    }

    public void prepare(){
        this.prepared = true;
    }

    public void updateCurrentTexture(){
        if (prepared){
            currentTex = this.prepdTex;
        } else {
            currentTex = this.notPrepdTex;
        }
    }
    @Override
    public boolean equals(Object ingredient){
        Ingredient compare = (Ingredient) ingredient;
        if(this.name == compare.name){
            if(this.prepared == compare.prepared){
                return true;
            }
        }
        return false;
    }

    public Texture getCurrentTexture(){
        return currentTex;
    }
}
