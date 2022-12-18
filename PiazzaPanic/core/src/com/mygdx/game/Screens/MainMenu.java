package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.PiazzaPanic;

public class MainMenu implements Screen{
    PiazzaPanic game;
    Texture logo;
    Texture playBtn;
    Texture playBtnHover;
    Texture playBtnCurrent;
    Texture exitBtn;
    Texture exitBtnHover;
    Texture exitBtnCurrent;
    Texture infoBtn;
    Texture infoBtnHover;
    Texture infoBtnCurrent;

    public MainMenu(PiazzaPanic game){
        this.game = game;
		logo = new Texture("Piazza_Panic_Logo.png");
        playBtn = new Texture("playBtn.png");
        exitBtn = new Texture("exitBtn.png");
        infoBtn = new Texture("infoBtn.png");
        playBtnHover = new Texture("playBtn2.png");
        exitBtnHover = new Texture("exitBtn2.png");
        infoBtnHover = new Texture("infoBtn2.png");

        playBtnCurrent = playBtn;
        exitBtnCurrent = exitBtn;
        infoBtnCurrent = infoBtn;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);
		game.batch.begin();
		game.batch.draw(logo, ((game.GAME_WIDTH/2) - (logo.getWidth()/2)), 250);
        game.batch.draw(playBtnCurrent,((game.GAME_WIDTH/2) - (playBtn.getWidth()/2)),125);
        game.batch.draw(exitBtnCurrent,((game.GAME_WIDTH/2) - (exitBtn.getWidth()/2)),35);
        game.batch.draw(infoBtnCurrent,game.GAME_WIDTH-infoBtn.getWidth(),game.GAME_HEIGHT-infoBtn.getHeight());
		game.batch.end();

    //Play button
        if((Gdx.input.getX()<(((game.GAME_WIDTH/2) - (playBtn.getWidth()/2)) + playBtn.getWidth())) && ((Gdx.input.getX() > ((game.GAME_WIDTH/2) - (playBtn.getWidth()/2)))) && ((Gdx.input.getY() > (game.GAME_HEIGHT-125-playBtn.getHeight()))) && ((Gdx.input.getY() < game.GAME_HEIGHT-125))){
            playBtnCurrent = playBtnHover;
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new GameScreen(game));
            }
        } else {
            playBtnCurrent = playBtn;
        }

    //Exit button
        if((Gdx.input.getX()<(((game.GAME_WIDTH/2) - (exitBtn.getWidth()/2)) + exitBtn.getWidth())) && ((Gdx.input.getX() > ((game.GAME_WIDTH/2) - (exitBtn.getWidth()/2)))) && ((Gdx.input.getY() > (game.GAME_HEIGHT-35-exitBtn.getHeight()))) && ((Gdx.input.getY() < game.GAME_HEIGHT-35))){
            exitBtnCurrent = exitBtnHover;
            if(Gdx.input.isTouched()){
                this.dispose();
                Gdx.app.exit();
            }
        } else {
            exitBtnCurrent = exitBtn;
        }

    //Info button
        if((Gdx.input.getX() > (game.GAME_WIDTH-infoBtn.getWidth()-0)) && (Gdx.input.getX() < (game.GAME_WIDTH-0)) && (Gdx.input.getY() > 0) && (Gdx.input.getY() <0+infoBtn.getHeight())){
            infoBtnCurrent = infoBtnHover;
            if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                this.dispose();
                game.setScreen(new CreditsScreen(game));
            }
        } else {
            infoBtnCurrent = infoBtn;
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
        logo.dispose();
        
        playBtn.dispose();
        playBtnCurrent.dispose();
        playBtnHover.dispose();
        
        exitBtn.dispose();
        exitBtnCurrent.dispose();
        exitBtnHover.dispose();
        
        infoBtn.dispose();
        infoBtnCurrent.dispose();
        infoBtnHover.dispose();
    }
    
}
