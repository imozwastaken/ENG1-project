package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class GameScreenNew implements Screen{
    PiazzaPanic game;
    BitmapFont font;
    Texture backBtnTex;
    Texture backBtnTexHover;
    
    FitViewport view;
    Stage gameStage;
    
    TextureRegion backBtnRegion;
    TextureRegionDrawable backBtnDrawable;
    ImageButton backBtn;

    TextureRegion backBtnRegionHover;
    TextureRegionDrawable backBtnDrawableHover;

    public GameScreenNew(PiazzaPanic game){
        this.game = game;
    }

    @Override
    public void show() {
        font = new BitmapFont();
        backBtnTex = new Texture("backBtn.png");
        backBtnTexHover = new Texture("backBtn2.png");

        view = new FitViewport(game.GAME_WIDTH, game.GAME_HEIGHT);
        view.getCamera().position.set(game.GAME_WIDTH / 2, game.GAME_HEIGHT / 2, 1f);
        gameStage = new Stage(view, game.batch);

        //Buttons
        backBtnRegion = new TextureRegion(backBtnTex);
        backBtnDrawable = new TextureRegionDrawable(backBtnRegion);
        backBtn = new ImageButton(backBtnDrawable);

        //hovered button
        backBtnRegionHover = new TextureRegion(backBtnTexHover);
        backBtnDrawableHover = new TextureRegionDrawable(backBtnRegionHover);

        //listen for hover
        backBtn.addListener(new ClickListener(){
            ImageButton backNormal = new ImageButton(backBtnDrawable);
            ImageButton backHover = new ImageButton(backBtnDrawableHover);
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                backBtn.setStyle(backHover.getStyle());
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                backBtn.setStyle(backNormal.getStyle());
            }
        });
    }

    @Override
    public void render(float delta) {
        gameStage.act();

        ScreenUtils.clear(0, 0, 0, 0);
        view.apply();

        game.batch.setProjectionMatrix(view.getCamera().combined);
		game.batch.begin();
        font.draw(game.batch, "This is an option screen", game.GAME_WIDTH/2-100, game.GAME_HEIGHT/2);
        game.batch.end();

        gameStage.getViewport().apply();
        
        gameStage.addActor(backBtn);
        backBtn.setPosition(0,game.GAME_HEIGHT-backBtn.getHeight());

        if (backBtn.isPressed()){
            game.setScreen(new MainMenuNew(game));
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
        font.dispose();
        gameStage.dispose();
    }
    
}
