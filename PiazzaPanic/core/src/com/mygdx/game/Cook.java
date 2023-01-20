package com.mygdx.game;

import java.util.Stack;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Food.Ingredient;

public class Cook {
    public Stack<Ingredient> CookStack;
    public Actor CookBody;

    public Cook(Actor skin){
        this.CookBody = skin;
        this.CookStack = new Stack<Ingredient>();
    }

    public boolean pickUp(Ingredient ingredient){
        if(this.CookStack.size() == 10){
            return false;
        }
        CookStack.push(ingredient);
        return true;
    }

    public boolean drop(){
        if(this.CookStack.isEmpty()){
            return false;
        }
        CookStack.pop();
        return true;
    }
}
