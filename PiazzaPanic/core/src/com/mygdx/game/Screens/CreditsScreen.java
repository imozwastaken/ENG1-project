package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.PiazzaPanic;

public class CreditsScreen implements Screen{
    PiazzaPanic game;
    Texture credits;
    Texture backBtn;
    Texture backBtnHover;
    Texture backBtnCurrent;
    
    public CreditsScreen(PiazzaPanic game){
        this.game = game;
        credits = new Texture("credits2.png");
        backBtn = new Texture("backBtn.png");
        backBtnHover = new Texture("backBtn2.png");

        backBtnCurrent = backBtn;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
        game.batch.begin();
        game.batch.draw(credits,(game.GAME_WIDTH/2 - credits.getWidth()/2),30);
        game.batch.draw(backBtnCurrent,0,game.GAME_HEIGHT-backBtn.getHeight());
        game.batch.end();
        
        if((Gdx.input.getY() > 0) && (Gdx.input.getY() < (0+backBtn.getHeight())) && (Gdx.input.getX() > 0) && (Gdx.input.getX() < (backBtn.getWidth()+0))){
            backBtnCurrent = backBtnHover;
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new MainMenu(game));
            }
        } else {
            backBtnCurrent = backBtn;
        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void dispose() {
        credits.dispose();
        
        backBtn.dispose();
        backBtnCurrent.dispose();
        backBtnHover.dispose();
    }
    
}
