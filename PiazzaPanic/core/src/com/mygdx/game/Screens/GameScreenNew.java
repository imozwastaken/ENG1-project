package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.PiazzaPanic;

import java.io.*;

public class GameScreenNew implements Screen{
    PiazzaPanic game;
    
    FitViewport view;
    Stage gameStage;

    TmxMapLoader mapLoader;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera gameCam;

    // graphics stuff
    TextureAtlas atlas;
    TextureAtlas atlasIdle;
    Sprite bob;
    String[] lines;
    FileHandle charIdles;
    Skin skin;
    private Array<Sprite> idles;
    SpriteBatch batch;
    // movement stuff
    Vector3 touchPos = new Vector3();
    Float speed = 3f;
    int selected = 0;
    private Array<Actor> cooks;

    public GameScreenNew(PiazzaPanic game, FitViewport port){
        this.game = game;
        this.view = port;

        gameStage = new Stage(view, game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("KitchenMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam = new OrthographicCamera();
        
        view.setCamera(gameCam);
        view.setWorldSize(192,144);

        gameCam.position.set(view.getWorldWidth()/2, view.getWorldHeight()/2,0);

        atlas = new TextureAtlas("charAnimations.atlas");

        TextureAtlas atlasIdle = new TextureAtlas(Gdx.files.internal("charIdle.txt"));
        skin = new Skin();
        skin.addRegions(atlasIdle);
        bob = skin.getSprite("Bob");
        charIdles = Gdx.files.internal("charIdle.txt");
        lines = charIdles.readString().split("\n");

        batch = new SpriteBatch();
        cooks = new Array<Actor>();
        spawnCooks();

        batch = new SpriteBatch();
        cooks = new Array<Actor>();
        spawnCooks();
    }


    @Override
    public void show() {

    }

    private void spawnCooks(){
        for (int i = 0; i < 3; i++){
            Sprite current = skin.getSprite(lines[i*7+6]);
            System.out.println(current);
            //idles.add(current);
            Actor cook = new Actor();
            cook.setX(110 * i);
            cook.setY(240);
            cook.setWidth(16);
            cook.setHeight(23);
            cook.addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            });
            cooks.add(cook);
            gameStage.addActor(cook);
        }
    }

    @Override
    public void render(float delta) {
        gameCam.update();
        renderer.setView(gameCam);

        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //game.batch.setProjectionMatrix(view.getCamera().combined);

        renderer.render();

        // draw the cooks
        int index = 0;
        batch.begin();
        for (Actor cook : cooks) {
            batch.draw(bob, cook.getX(), cook.getY(), 115, 160);
        }
        batch.end();

        // process user input
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            selected = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            selected = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)){
            selected = 2;
        }
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), game.GAME_HEIGHT - Gdx.input.getY(), 0);
            System.out.println(touchPos);
        }
        for (Actor cook : cooks) {
            if (cooks.get(index).isTouchFocusTarget()) {
                selected = index;
            }
            index ++;
        }


        // system for moving to user input
        if (touchPos.x - 8 != cooks.get(selected).getX() || touchPos.y - 8 != cooks.get(selected).getY()) {
            // calculate the difference between 2 points to move the sprite towards
            float pathX = touchPos.x - 8 - cooks.get(selected).getX();
            float pathY = touchPos.y - 8 - cooks.get(selected).getY();

            float distance = (float) Math.sqrt(pathX * pathX + pathY * pathY);
            float directionX = pathX / distance;
            float directionY = pathY / distance;

            if (distance < 3) {
                speed = 1f;
                if (distance < 1) {
                    speed = 0f;
                }
            } else {
                speed = 3f;
            }

            cooks.get(selected).setX(cooks.get(selected).getX() + directionX * speed);
            cooks.get(selected).setY(cooks.get(selected).getY() + directionY * speed);
        }


        // make sure the chef stays within the screen bounds (limit to kitchen area for main game)
        if (cooks.get(selected).getX() < 8){
            cooks.get(selected).setX(8);
        }
        if (cooks.get(selected).getX() > 565){
            cooks.get(selected).setX(565);
        }
        if (cooks.get(selected).getY() < 100) {
            cooks.get(selected).setY(100);
        }
        if (cooks.get(selected).getY() > 320) {
            cooks.get(selected).setY(320);
        }
    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
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
        gameStage.dispose();
    }
    
}
