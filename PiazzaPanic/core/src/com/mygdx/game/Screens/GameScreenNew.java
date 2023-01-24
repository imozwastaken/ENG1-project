package com.mygdx.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.math.MathUtils;
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
import com.mygdx.game.Customer;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.Food.Ingredient;

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
    Skin custSkins;
    ArrayList<Sprite> idles = new ArrayList<Sprite>();
    SpriteBatch batch;

    // movement stuff
    Vector3 touchPos = new Vector3();
    Float speed = 3f;
    int selected = 0;
    ArrayList<Integer> stationSelected = new ArrayList<Integer>();
    // control the number of cooks
    int cookCount = 2; // control how many cooks spawn -> update to allow for the value to increase
    private Array<Cook> cooks;
    private Array<Customer> customers;
    private int customerCount = 0;

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

    //pantryScreen clickables
    TextureRegion XbtnRegion;
    ImageButton XbtnClickable;

    TextureRegion pantryScreenFrameRegion;
    ImageButton pantryScreenFrame;   
    
    TextureRegion lettuceRegion;
    ImageButton lettuceClickable;
    
    TextureRegion tomatoRegion;
    ImageButton tomatoClickable;

    TextureRegion bunsRegion;
    ImageButton bunsClickable;

    TextureRegion pattyRegion;
    ImageButton pattyClickable;

    //servingScreen clickables
    TextureRegion servingScreenFrameRegion;
    ImageButton servingScreenFrame;

    TextureRegion burgerRegion;
    ImageButton burgerClickable;
    
    TextureRegion saladRegion;
    ImageButton saladClickable;
    //plate texture used to show the cooks current inventory

    Texture plateTex = new Texture("plate.png");

    Boolean showPantryScreen = false;
    Boolean showServingScreen = false;

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
        TextureAtlas customersLeft = new TextureAtlas(Gdx.files.internal("customersLeft.txt"));
        skin = new Skin();
        skin.addRegions(atlasIdle);
        custSkins = new Skin();
        custSkins.addRegions(customersLeft);
        charIdles = Gdx.files.internal("charIdle.txt");
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
                stationSelected.set(selected,0);
                showPantryScreen = true;
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
                stationSelected.set(selected,1);
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
                stationSelected.set(selected,2);
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
                stationSelected.set(selected,3);
                if((Math.abs(cooks.get(selected).CookBody.getY()-32f) < 2) && (Math.abs(cooks.get(selected).CookBody.getX()-0f) < 2)){
                    if(cooks.get(selected).CookStack.size() > 0){
                        cooks.get(selected).CookStack.pop();
                    }
                }
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
                stationSelected.set(selected, 4);
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
                stationSelected.set(selected, 5);
                showServingScreen = true;
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


        customers = new Array<Customer>();
        customers.add(new Customer(new Actor()));

        //pantry screen pop up clickables

        //Xbtn for station pop ups
        XbtnRegion = new TextureRegion(new Texture("Xbtn.png"));

        XbtnClickable = new ImageButton(new TextureRegionDrawable(XbtnRegion));
        XbtnClickable.setSize(16, 16);
        XbtnClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //stationSelected.set(selected,1);
                showPantryScreen = false;
                hidePantryScreen();
                hideServingScreen();
            }
        });

        //pantry screen frame
        pantryScreenFrameRegion = new TextureRegion(new Texture("pantryFrame.png"));

        pantryScreenFrame = new ImageButton(new TextureRegionDrawable(pantryScreenFrameRegion));
        pantryScreenFrame.setSize(140, 92);

        //unprepd lettuce button
        lettuceRegion = new TextureRegion(new Texture("lettuce.png"));

        lettuceClickable = new ImageButton(new TextureRegionDrawable(lettuceRegion));
        lettuceClickable.setSize(24, 24);
        lettuceClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(cooks.get(selected).CookStack.size() < 5){
                    cooks.get(selected).CookStack.push(new Ingredient("lettuce", new Texture("lettuce.png"), new Texture("prepdLettuce.png")));
                }
            }
        });

        //unprepd tomato button
        tomatoRegion = new TextureRegion(new Texture("tomato.png"));

        tomatoClickable = new ImageButton(new TextureRegionDrawable(tomatoRegion));
        tomatoClickable.setSize(24, 24);
        tomatoClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(cooks.get(selected).CookStack.size() < 5){
                    cooks.get(selected).CookStack.push(new Ingredient("tomato", new Texture("tomato.png"), new Texture("prepdTomato.png")));
                }
            }
        });

        //unprepd buns button
        bunsRegion = new TextureRegion(new Texture("buns.png"));

        bunsClickable = new ImageButton(new TextureRegionDrawable(bunsRegion));
        bunsClickable.setSize(24, 24);
        bunsClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(cooks.get(selected).CookStack.size() < 5){
                    cooks.get(selected).CookStack.push(new Ingredient("buns", new Texture("buns.png"), new Texture("buns.png")));
                }
            }
        });

        pattyRegion = new TextureRegion(new Texture("rawPatty.png"));

        pattyClickable = new ImageButton(new TextureRegionDrawable(pattyRegion));
        pattyClickable.setSize(24, 24);
        pattyClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(cooks.get(selected).CookStack.size() < 5){
                    cooks.get(selected).CookStack.push(new Ingredient("patty", new Texture("rawPatty.png"), new Texture("prepdPatty.png")));
                }
            }
        });

        //serving screen clickables
        //serving screen frame
        servingScreenFrameRegion = new TextureRegion(new Texture("servingFrame.png"));

        servingScreenFrame = new ImageButton(new TextureRegionDrawable(servingScreenFrameRegion));
        servingScreenFrame.setSize(140, 92);

        //burger button
        burgerRegion = new TextureRegion(new Texture("burger.png"));

        burgerClickable = new ImageButton(new TextureRegionDrawable(burgerRegion));
        burgerClickable.setSize(24, 24);
        burgerClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("burger pressed");
            }
        });

        //salad button
        saladRegion = new TextureRegion(new Texture("salad.png"));

        saladClickable = new ImageButton(new TextureRegionDrawable(saladRegion));
        saladClickable.setSize(24, 24);
        saladClickable.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("salad pressed");
            }
        });
    }

    private void customerOperations(){
        // TODO make an else statement which ends the game once all 5 customers have been served
        // move the customers to the counter
        if (!customers.get(customerCount).atCounter){
            customers.get(customerCount).move();
        } else if (customers.get(customerCount).orderComplete){
            // make the customer leave
            customers.get(customerCount).move();
            if (customers.get(customerCount).body.getX()>148){
                customers.get(customerCount).body.remove();
                customers.add(new Customer(new Actor()));
                customerCount += 1;
            }
        }
    }


    @Override
    public void show() {

    }

    // generate the cooks
    private void spawnCooks(){
        for (int i = 0; i < cookCount; i++){
            System.out.println(stationSelected);
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
            stationSelected.add(MathUtils.random(0,5));
        }
    }
    //process user input
    private void processInput() {
        int index = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            selected = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            selected = 1;
        }
        if (cookCount > 2);{
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
                selected = 2;
            }
        }
        for (Cook cook : cooks) {
            if (cooks.get(index).CookBody.isTouchFocusTarget()) {
                selected = index;
            }
            index++;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            // debug option to mark the current customers order as complete, moving them on
            customers.get(customerCount).orderComplete = true;
        }
    }

    //update the cooks on the screen
    private void updateBatch(){
        // this section assigns each cook a sprite from the list idles
        // you could potentially update this to allow for animations for the cooks when they move
        game.batch.begin();
        int index = 0;
        for (Cook cook : cooks) {
            game.batch.draw(idles.get(index), cook.CookBody.getX(), cook.CookBody.getY());
            index ++;
        }
        game.batch.draw(custSkins.getSprite(customers.get(customerCount).name),customers.get(customerCount).body.getX(),customers.get(customerCount).body.getY());
        game.batch.end();
    }

    @Override
    public void render(float delta) {
        gameCam.update();
        renderer.setView(gameCam);

        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        //plate
        game.batch.begin();
        game.batch.draw(plateTex,164,23);
        game.batch.end();

        gameStage.act();
        updateProgressBars();
        
        showCookStack();

        showStationScreens();

        updateBatch();

        customerOperations();
        
        gameStage.draw();

        processInput();
        for (int i = 0; i < cookCount; i++) {
            cooks.get(i).move(stationSelected.get(i), cooks.get(i).CookBody);
        }

        

        Gdx.input.setInputProcessor(gameStage);

        
    }

    private void showCookStack() {
        float x = 164;
        float y = 30;
        game.batch.begin();
        //game.batch.draw(cooks.get(selected).CookBody.getTouchable(),176,0);
            for(Ingredient ingredient : cooks.get(selected).CookStack){
                game.batch.draw(ingredient.currentTex,x,y);
                //patty is a smaller texture hence decreasing the distance between the coming ingredient
                if(ingredient.name == "patty"){
                    y += 10;
                } else {
                    y += 18;
                }
                
            }
        game.batch.end();
    }

    private void showStationScreens() {
        for (Cook cook : cooks){
            if((Math.abs(cook.CookBody.getY()-64f) < 2) && (Math.abs(cook.CookBody.getX()-0f) < 2)){
                showPantryScreen();
            }
            if((Math.abs(cook.CookBody.getY()-28f) < 2) && (Math.abs(cook.CookBody.getX()-48f) < 2)){
                createProgressBar(40, 50);
            }
            if((Math.abs(cook.CookBody.getY()-48f) < 2) && (Math.abs(cook.CookBody.getX()-80f) < 2)){
                showServingScreen();
            }
        }
    }

    private void showServingScreen() {
        if(showServingScreen){
            gameStage.addActor(servingScreenFrame);
            gameStage.addActor(XbtnClickable);
            XbtnClickable.toFront();
            gameStage.addActor(burgerClickable);
            gameStage.addActor(saladClickable);
            
            servingScreenFrame.setPosition(10, 10);
            XbtnClickable.setPosition(7, 88);
            burgerClickable.setPosition(25,66);
            saladClickable.setPosition(53,66);

            showServingScreen = false;
        }
    }

    private void showPantryScreen() {
        if(showPantryScreen){
            gameStage.addActor(pantryScreenFrame);
            gameStage.addActor(XbtnClickable);
            XbtnClickable.toFront();
            gameStage.addActor(lettuceClickable);
            gameStage.addActor(tomatoClickable);
            gameStage.addActor(bunsClickable);
            gameStage.addActor(pattyClickable);
            
            pantryScreenFrame.setPosition(10, 10);
            XbtnClickable.setPosition(7, 88);
            lettuceClickable.setPosition(25,66);
            tomatoClickable.setPosition(53,66);
            bunsClickable.setPosition(81, 66);
            pattyClickable.setPosition(110, 72);
            showPantryScreen = false;
        }
        
    }

    private void hidePantryScreen() {
        //moves pantry screen offscreen
        
        pantryScreenFrame.setPosition(10000, -1);
        XbtnClickable.setPosition(10000, -1);
        lettuceClickable.setPosition(10000,-1);
        tomatoClickable.setPosition(10000,-1);
        bunsClickable.setPosition(10000,-1);
        pattyClickable.setPosition(10000,-1);
    }
    private void hideServingScreen() {
        //moves serving screen offscreen
        
        servingScreenFrame.setPosition(10000, -1);
        XbtnClickable.setPosition(10000, -1);
        burgerClickable.setPosition(10000,-1);
        saladClickable.setPosition(10000,-1);
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

    public void createProgressBar(float x, float y){
        Boolean createTheBar = true;
        for (ProgressBar bar : bars){
            if(bar.getValue() > 0){
                createTheBar = false;
                break;
            }
        }
        if(createTheBar){
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

    private void updateProgressBars(){
        for (ProgressBar bar : bars){
            bar.setValue(bar.getValue()-0.05f);
            if(bar.getValue() == 0){
                gameStage.getActors().removeValue(bar,false);
                //bars.remove(bar);
                //Gdx.input.setInputProcessor(gameStage);
            }
        }
    }
    
}
