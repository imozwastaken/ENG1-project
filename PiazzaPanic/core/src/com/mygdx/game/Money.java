package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.Screens.GameScreen;

public class Money {

    private int currentMoney;
    private BitmapFont font;
    private PiazzaPanic game;
    private String difficulty;

    public Money(PiazzaPanic _game, String difficulty) {
        this.font = new BitmapFont();
        font.setColor(new Color(0.52156866f, 0.73333335f, 0.39607844f, 1.0f));
        font.getData().setScale(0.45f);
        this.currentMoney = 0;
        this.game = _game;
        this.difficulty = difficulty;
    }

    public Money(PiazzaPanic _game, BitmapFont font) {
        this.font = font;
        font.setColor(new Color(0.52156866f, 0.73333335f, 0.39607844f, 1.0f));
        font.getData().setScale(0.45f);
        this.currentMoney = 0;
        this.game = _game;
    }

    public void addMoney(int amount) {
        System.out.println("money being added");
        System.out.println("difficulty: " + difficulty);
        if (difficulty.equals("Easy")) {
            System.out.println("easy money");
            currentMoney += amount;
        } else if (difficulty.equals("Medium")) {
            System.out.println("medium money");
            currentMoney += amount * 0.9;
        } else if (difficulty.equals("Hard")) {
            System.out.println("hard money");
            currentMoney += amount * 0.7;
        }
    }

    public int getCurrentMoney() {
        return currentMoney;
    }

    public void removeMoney(int amount) {
        currentMoney -= amount;
    }

    public void render() {
        game.batch.begin();
        font.draw(game.batch, "$" + currentMoney, 127, 132);
        game.batch.end();
    }

}
