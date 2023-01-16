package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.PiazzaPanic;

public class MainMenuNew implements Screen{
    PiazzaPanic game;
    Texture logo;
    Texture playBtnTex;
    Texture playBtnTexHover;
    Texture exitBtnTex;
    Texture exitBtnTexHover;
    Texture infoBtnTex;
    Texture infoBtnTexHover;
    
    FitViewport view;
    Stage gameStage;

    //Image buttons for when not hovering on the button
    TextureRegion playBtnRegion;
    TextureRegionDrawable playBtnDrawable;
    ImageButton playBtn;

    TextureRegion exitBtnRegion;
    TextureRegionDrawable exitBtnDrawable;
    ImageButton exitBtn;

    TextureRegion infoBtnRegion;
    TextureRegionDrawable infoBtnDrawable;
    ImageButton infoBtn;

    //Image buttons for when hovering on the button
    TextureRegion playBtnRegionHover;
    TextureRegionDrawable playBtnDrawableHover;
    ImageButton playBtnHover;

    TextureRegion exitBtnRegionHover;
    TextureRegionDrawable exitBtnDrawableHover;
    ImageButton exitBtnHover;

    TextureRegion infoBtnRegionHover;
    TextureRegionDrawable infoBtnDrawableHover;
    ImageButton infoBtnHover;

    public MainMenuNew(PiazzaPanic game){
        this.game = game;
        
    }

    @Override
    public void show() {
        logo = new Texture("Piazza_Panic_Logo.png");

        //Textures for normal buttons
        playBtnTex = new Texture("playBtn.png");
        exitBtnTex = new Texture("exitBtn.png");
        infoBtnTex = new Texture("infoBtn.png");

        //Textures for hovered buttons
        infoBtnTexHover = new Texture("infoBtn2.png");
        playBtnTexHover = new Texture("playBtn2.png");
        exitBtnTexHover = new Texture("exitBtn2.png");

        view = new FitViewport(game.GAME_WIDTH, game.GAME_HEIGHT);
        view.getCamera().position.set(game.GAME_WIDTH / 2, game.GAME_HEIGHT / 2, 1f);
        gameStage = new Stage(view, game.batch);

        //normal buttons
        playBtnRegion = new TextureRegion(playBtnTex);
        playBtnDrawable = new TextureRegionDrawable(playBtnRegion);
        playBtn = new ImageButton(playBtnDrawable);

        exitBtnRegion = new TextureRegion(exitBtnTex);
        exitBtnDrawable = new TextureRegionDrawable(exitBtnRegion);
        exitBtn = new ImageButton(exitBtnDrawable);

        infoBtnRegion = new TextureRegion(infoBtnTex);
        infoBtnDrawable = new TextureRegionDrawable(infoBtnRegion);
        infoBtn = new ImageButton(infoBtnDrawable);
        
        //hovered buttons
        playBtnRegionHover = new TextureRegion(playBtnTexHover);
        playBtnDrawableHover = new TextureRegionDrawable(playBtnRegionHover);

        exitBtnRegionHover = new TextureRegion(exitBtnTexHover);
        exitBtnDrawableHover = new TextureRegionDrawable(exitBtnRegionHover);

        infoBtnRegionHover = new TextureRegion(infoBtnTexHover);
        infoBtnDrawableHover = new TextureRegionDrawable(infoBtnRegionHover);

        //Listerners for hovering on the buttons
        playBtn.addListener(new ClickListener(){
            ImageButton playNormal = new ImageButton(playBtnDrawable);
            ImageButton playHover = new ImageButton(playBtnDrawableHover);
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playBtn.setStyle(playHover.getStyle());
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                playBtn.setStyle(playNormal.getStyle());
            }
        });

        exitBtn.addListener(new ClickListener(){
            ImageButton exitNormal = new ImageButton(exitBtnDrawable);
            ImageButton exitHover = new ImageButton(exitBtnDrawableHover);
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitBtn.setStyle(exitHover.getStyle());
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                exitBtn.setStyle(exitNormal.getStyle());
            }
        });

        infoBtn.addListener(new ClickListener(){
            ImageButton infoNormal = new ImageButton(infoBtnDrawable);
            ImageButton infoHover = new ImageButton(infoBtnDrawableHover);
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                infoBtn.setStyle(infoHover.getStyle());
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                infoBtn.setStyle(infoNormal.getStyle());
            }
        });
    }

    @Override
    public void render(float delta) {
        gameStage.act();

        ScreenUtils.clear(1, 1, 1, 1);
        view.apply();

        game.batch.setProjectionMatrix(view.getCamera().combined);
		game.batch.begin();
		game.batch.draw(logo, ((game.GAME_WIDTH/2) - (logo.getWidth()/2)), 250);
		game.batch.end();

        gameStage.getViewport().apply();
        
        gameStage.addActor(playBtn);
        playBtn.setPosition((game.GAME_WIDTH/2) - (playBtn.getWidth()/2),125);

        gameStage.addActor(exitBtn);
        exitBtn.setPosition((game.GAME_WIDTH/2) - (exitBtn.getWidth()/2),35);

        gameStage.addActor(infoBtn);
        infoBtn.setPosition(game.GAME_WIDTH-infoBtn.getWidth(),game.GAME_HEIGHT-infoBtn.getHeight());

        if (playBtn.isPressed()){
            game.setScreen(new GameScreenNew(game,view));
        }

        if(exitBtn.isPressed()){
            dispose();
            Gdx.app.exit();
        }

        if(infoBtn.isPressed()){
            game.setScreen(new CreditsScreenNew(game));
        }

        Gdx.input.setInputProcessor(gameStage);
        gameStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameStage.getViewport().update(width, height);
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
        gameStage.dispose();
    }
    
}
