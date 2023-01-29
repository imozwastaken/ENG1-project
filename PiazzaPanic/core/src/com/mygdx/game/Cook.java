package com.mygdx.game;

import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Food.Ingredient;

public class Cook {
    public Stack<Ingredient> CookStack;
    public Actor CookBody;
    public float[][] locations = {{0, 64},{32,64},{64,64},{0,32},{48,28},{80,48}};
    public boolean isBusy = false;

    public Cook(Actor skin){
        this.CookBody = skin;
        this.CookStack = new Stack<>();
    }

    public void move(int index, Actor cook, ArrayList<Integer> stations) {
        for (int station : stations){
            if (index != station){
                // method to move a cook from their current position to a station
                if (cook.getX() != locations[index][0] || cook.getY() != locations[index][1]) {
                    // calculate the difference between 2 points to move the sprite towards
                    float pathX = locations[index][0] - cook.getX();
                    float pathY = locations[index][1] - cook.getY();
                    // use Pythagoras to find the distance between current position and final position
                    float distance = (float) Math.sqrt(pathX * pathX + pathY * pathY);
                    float directionX = pathX / distance;
                    float directionY = pathY / distance;
                    // snap cook to final coordinates once close enough
                    float speed;
                    if (distance < 1) {
                        speed = 0f;
                    } else {
                        speed = 1f;
                    }

                    cook.setX(cook.getX() + directionX * speed);
                    cook.setY(cook.getY() + directionY * speed);
                }
            }
        }
    }

    public boolean pickUp(Ingredient ingredient){
        if(this.CookStack.size() >= 5){
            return false;
        } else {
            CookStack.push(ingredient);
            return true;
        }
        
    }

    public boolean drop(){
        if(this.CookStack.isEmpty()){
            return false;
        }
        CookStack.pop();
        return true;
    }

    public float [] getPosition(){
       float []  output = {CookBody.getX(),CookBody.getY()};
        return output;
    }

}
