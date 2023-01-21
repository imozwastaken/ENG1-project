package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Cook;
import com.mygdx.game.PiazzaPanic;

import java.io.*;
import java.util.ArrayList;

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
    Sprite alex;
    Sprite amelia;
    Sprite adam;
    String[] lines;
    FileHandle charIdles;
    Skin skin;
    ArrayList<Sprite> idles = new ArrayList<Sprite>();
    SpriteBatch batch;

    // movement stuff
    Vector3 touchPos = new Vector3();
    Float speed = 3f;
    int selected = 0;
    // control the number of cooks
    int cookCount = 2;
    private Array<Cook> cooks;

    //progress bars
    ArrayList<ProgressBar> bars;

    //stations
    TextureRegion pantryRegion;
    ImageButton pantryClickable;

    TextureRegion fryingRegion;
    ImageButton fryingClickable;

    TextureRegion bakingRegion;
    ImageButton bakingClickable;

    TextureRegion cuttingRegion;
    ImageButton cuttingClickable;

    TextureRegion binRegion;
    ImageButton binClickable;

    TextureRegion servingRegion;
    ImageButton servingClickable;

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

        // sprite information from the texture atlas
        TextureAtlas atlasIdle = new TextureAtlas(Gdx.files.internal("charIdle.txt"));
        skin = new Skin();
        skin.addRegions(atlasIdle);
        charIdles = Gdx.files.internal("charIdle.txt");
        lines = charIdles.readString().split("\n");
        adam = skin.getSprite("Adam");
        alex = skin.getSprite("Alex");
        amelia = skin.getSprite("Amelia");
        idles.add(adam);
        idles.add(alex);
        idles.add(amelia);

        //batch = new SpriteBatch();
        cooks = new Array<Cook>();
        spawnCooks();

        //array of all progressbars created (used to update all of them in updateProgressBars function)
        bars = new ArrayList<ProgressBar>();

        //regions for all the stations on the map and adding clicklistners to them.
        //pixmap is used to create the region used for the stations (using pixel size from actual map)
        Pixmap pixmap = new Pixmap(32, 32, Format.RGBA8888);

        //pantry station
        pantryRegion = new TextureRegion(new Texture(pixmap));

        pantryClickable = new ImageButton(new TextureRegionDrawable(pantryRegion));
        pantryClickable.setSize(32, 32);
        pantryClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("pantry clicked!");
            }
        });
        gameStage.addActor(pantryClickable);

        //frying station
        fryingRegion = new TextureRegion(new Texture(pixmap));

        fryingClickable = new ImageButton(new TextureRegionDrawable(fryingRegion));
        fryingClickable.setSize(32, 32);
        fryingClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("frying clicked!");
            }
        });
        gameStage.addActor(fryingClickable);
        
        //baking station
        bakingRegion = new TextureRegion(new Texture(pixmap));

        bakingClickable = new ImageButton(new TextureRegionDrawable(bakingRegion));
        bakingClickable.setSize(32, 32);
        bakingClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("baking clicked!");
            }
        });
        gameStage.addActor(bakingClickable);
        
        //bin station
        binRegion = new TextureRegion(new Texture(pixmap));

        binClickable = new ImageButton(new TextureRegionDrawable(binRegion));
        binClickable.setSize(32, 32);
        binClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("bin clicked!");
            }
        });
        gameStage.addActor(binClickable);

        //cutting station
        pixmap = new Pixmap(64, 32, Format.RGBA8888); 
        cuttingRegion = new TextureRegion(new Texture(pixmap));

        cuttingClickable = new ImageButton(new TextureRegionDrawable(cuttingRegion));
        cuttingClickable.setSize(64, 32);
        cuttingClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("cutting clicked!");
            }
        });
        gameStage.addActor(cuttingClickable);

        //serving station
        pixmap = new Pixmap(32, 56, Format.RGBA8888); 
        servingRegion = new TextureRegion(new Texture(pixmap));

        servingClickable = new ImageButton(new TextureRegionDrawable(servingRegion));
        servingClickable.setSize(32, 56);
        servingClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("serving clicked!");
            }
        });
        gameStage.addActor(servingClickable);

        //adding regions to the screen
        pantryClickable.setPosition(0, 64);
        fryingClickable.setPosition(32, 64);
        bakingClickable.setPosition(64, 64);
        binClickable.setPosition(0, 0);
        cuttingClickable.setPosition(32, 0);
        servingClickable.setPosition(96, 16);
    }


    @Override
    public void show() {

    }

    // generate the cooks
    private void spawnCooks(){
        for (int i = 0; i < 2; i++){
            Cook cook = new Cook(new Actor());
            cook.CookBody.setX(1 * i);
            cook.CookBody.setY(10);
            cook.CookBody.setWidth(16);
            cook.CookBody.setHeight(23);
            // scale information
            cook.CookBody.setScaleX(game.GAME_WIDTH/16);
            cook.CookBody.setScaleY(game.GAME_HEIGHT/23);
            // click detection for cooks
            cook.CookBody.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("cook clicked!!");
                }
            });
            cooks.add(cook);
            gameStage.addActor(cook.CookBody);
        }
    }
    //process user input
    // TODO fix this pls k thanks
    private void processInput(){
        int index = 0;
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
        for (Cook cook : cooks) {
            if (cooks.get(index).CookBody.isTouchFocusTarget()) {
                selected = index;
            }
            index ++;
        }
        // system for moving to user input

            if (touchPos.x - 8 != cooks.get(selected).CookBody.getX() || touchPos.y - 8 != cooks.get(selected).CookBody.getY()) {
            // calculate the difference between 2 points to move the sprite towards
            float pathX = touchPos.x - cooks.get(selected).CookBody.getX();
            float pathY = touchPos.y - cooks.get(selected).CookBody.getY();
            // use Pythagoras to find the distance between current position and final position
            float distance = (float) Math.sqrt(pathX * pathX + pathY * pathY);
            float directionX = pathX / distance;
            float directionY = pathY / distance;
            // very rough speed easing to ensure the sprites don't jitter around their coordinates
            if (distance < 3) {
                speed = 1f;
                if (distance < 1) {
                    speed = 0f;
                }
            } else {
                speed = 3f;
            }

            cooks.get(selected).CookBody.setX(cooks.get(selected).CookBody.getX() + directionX * speed);
            cooks.get(selected).CookBody.setY(cooks.get(selected).CookBody.getY() + directionY * speed);
        }
    }
    //update the cooks on the screen
    private void updateCooks(){
        // this section assigns each cook a sprite from the list idles
        game.batch.begin();
        int index = 0;
        for (Cook cook : cooks) {
            game.batch.draw(idles.get(index), cook.CookBody.getX(), cook.CookBody.getY());
            index ++;
        }
        game.batch.end();
        // make sure the chef stays within the screen bounds (limit to kitchen area for main game)
        if (cooks.get(selected).CookBody.getX() < 8){
            cooks.get(selected).CookBody.setX(8);
        }
        if (cooks.get(selected).CookBody.getX() > 565){
            cooks.get(selected).CookBody.setX(565);
        }
        if (cooks.get(selected).CookBody.getY() < 100) {
            cooks.get(selected).CookBody.setY(100);
        }
        if (cooks.get(selected).CookBody.getY() > 320) {
            cooks.get(selected).CookBody.setY(320);
        }
    }

    @Override
    public void render(float delta) {
        gameCam.update();
        renderer.setView(gameCam);

        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        updateCooks();

        processInput();

        updateProgressBars();

        gameStage.act();
        //adding station regions
        Gdx.input.setInputProcessor(gameStage);
        gameStage.draw();

    }

    private void updateProgressBars(){
        for (ProgressBar bar : bars){
            bar.setValue(bar.getValue()-0.05f);
            if(bar.getValue() == 0){
                gameStage.getActors().removeValue(bar,false);
            }
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

    private static TextureRegionDrawable getColoredDrawable(int width, int height, Color color) {
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color);
		pixmap.fill();
		
		TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
		
		pixmap.dispose();
		
		return drawable;
	}

    private void createProgressBar(float x, float y){
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = getColoredDrawable(20, 5, Color.GREEN);
        style.knob = getColoredDrawable(0, 5, Color.WHITE);
        style.knobAfter = getColoredDrawable(20, 5, Color.WHITE);

        ProgressBar bar = new ProgressBar(0, 15, 0.05f, false, style);
        bar.setWidth(30);
        bar.setHeight(5);
        
        bar.setValue(15f);
        bar.setX(x);
        bar.setY(y);

        gameStage.addActor(bar);
        bars.add(bar);
    }
    
}
