package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Cook;
import com.mygdx.game.Customer;
import com.mygdx.game.Food.Burger;
import com.mygdx.game.Food.Ingredient;
import com.mygdx.game.Food.Order;
import com.mygdx.game.Food.Salad;
import com.mygdx.game.PiazzaPanic;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The class GameScreen represents the screen and all displayed assets within the game, which inherits from the Screen interface
 */
public class GameScreen implements Screen {

    private final int customerNumber = 5;
    PiazzaPanic game;
    FitViewport view;
    Stage gameStage;
    /** map and camera stuff */
    TmxMapLoader mapLoader;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera gameCam;
    /** Start the game with 3 reputation points */
    int Rep = 3;
    Texture RepLabel = new Texture("REP.png");
    Texture RepPoint = new Texture("REPHeart.png");
    /** customer number determines how many customers will spawn over the course of the game */
    /** 0 means infinite */
    private final Array<Cook> cooks;
    private final Array<Customer> customers;
    /** sprite handling */
    Sprite alex;
    Sprite amelia;
    Sprite adam;
    FileHandle charIdles;
    Skin skin;
    Skin custSkins;
    ArrayList<Sprite> idles = new ArrayList<>();
    /** cook and customer control variables */
    int selected = 0;
    ArrayList<Integer> stationSelected = new ArrayList<>();
    /** control the number of cooks */
    int cookCount = 2; /** control how many cooks spawn -> update to allow for the value to increase */
    /** take the time at the start of the game to display the time taken to complete the round */
    Instant gameTime = Instant.now();
    /** list of active orders */
    ArrayList<Order> orders = new ArrayList<>();
    /** used to count how much time has passed after an order is placed */
    float timeCount = 0;
    /** order timer font */
    BitmapFont font = new BitmapFont();
    /** progress bars */
    HashMap<ProgressBar, Cook> bars;
    /** music */
    Music alienJazz = Gdx.audio.newMusic(Gdx.files.internal("Alien_Jazz_Ridley_Coyte.mp3"));
    /** stations */
    ImageButton pantryClickable;
    ImageButton fryingClickable;
    int fryingClicked = 0;
    boolean pattyAtFrying = false;
    Texture flipBtn = new Texture("flipBtn.png");
    ImageButton bakingClickable;
    ImageButton cuttingClickable;
    ImageButton binClickable;
    ImageButton servingClickable;
    /** pantry and serving screen frames */
    TextureRegion pantryScreenFrameRegion;
    ImageButton pantryScreenFrame;
    TextureRegion servingScreenFrameRegion;
    ImageButton servingScreenFrame;
    /** clickables */
    ImageButton XbtnClickable;
    ImageButton lettuceClickable;
    ImageButton tomatoClickable;
    ImageButton bunsClickable;
    ImageButton pattyClickable;
    ImageButton burgerClickable;
    ImageButton saladClickable;

    /**
     * Listener function to
     */
    ClickListener cursorHovering = new ClickListener() {
        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            Gdx.graphics.setSystemCursor(SystemCursor.Hand);
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
        }
    };
    /** UI elements */
    Texture plateTex = new Texture("plate.png");
    Texture cookStackTitle = new Texture("cookStackTitle.png");
    Texture selectedCook = new Texture("selected.png");
    /** Order Textures */
    Texture burgerOrderTexture = new Texture("orderBurger.png");
    Texture saladOrderTexture = new Texture("orderSalad.png");
    Boolean showPantryScreen = false;
    Boolean showServingScreen = false;
    private int customerCount = 0;


    public GameScreen(PiazzaPanic game, FitViewport port) {
        /** initialise the game */
        this.game = game;
        this.view = port;
        gameStage = new Stage(view, game.batch);

        /** load the map and camera */
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("KitchenMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam = new OrthographicCamera();
        view.setCamera(gameCam);
        view.setWorldSize(192, 144);
        gameCam.position.set(view.getWorldWidth() / 2, view.getWorldHeight() / 2, 0);
        /** set order timer font color */
        font.setColor(Color.BLACK);
        font.getData().setScale(0.5f);
        /** sprite information from the texture atlas */
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

        /** music control */
        /** music composed by Ridley Coyte */
        alienJazz.setLooping(true);
        alienJazz.play();

        /** create the instances of the cooks and first customer. */
        cooks = new Array<Cook>();
        spawnCooks();
        customers = new Array<Customer>();
        customers.add(new Customer(new Actor()));

        /** array of all progressbars created (used to update all of them in updateProgressBars function) */
        bars = new HashMap<ProgressBar, Cook>();

        /** pantry station */
        pantryClickable = createImageClickable(32, 32);
        /** function exectutes when you press on the pantry on screen */
        /** it sets the pantry as the currently selected station - this moves the cook to the pantry */
        /** when the cook arrives the pantry screen is shown */
        pantryClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stationSelected.set(selected, 0);
                if ((Math.abs(cooks.get(selected).CookBody.getY() - 64f) < 2) && (Math.abs(cooks.get(selected).CookBody.getX() - 0f) < 2)) {
                    showPantryScreen = true;
                    cooks.get(selected).isBusy = true;
                }
            }
        });
        gameStage.addActor(pantryClickable);

        /** frying station */
        fryingClickable = createImageClickable(32, 32);
        /** function exectutes when you press on the frying station on screen */
        /** it sets the frying station as the currently selected station - this moves the cook to the frying station */
        fryingClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stationSelected.set(selected, 1);
                boolean ingredientAtStation = false;
                Ingredient cookedPatty = new Ingredient("patty", new Texture("rawPatty.png"), new Texture("prepdPatty.png"));
                cookedPatty.prepare();
                cookedPatty.updateCurrentTexture();
                if ((Math.abs(cooks.get(selected).CookBody.getY() - 64f) < 2) && (Math.abs(cooks.get(selected).CookBody.getX() - 32f) < 2)) {
                    if (!(cooks.get(selected).isBusy)) {
                        /** used to limit to preping only one ingredient per press */
                        boolean ingredientDone = false;
                        Ingredient selectedIngredient = null;
                        /** preps the first vegetable in the current cook's stack after pressing the station again */
                        /** while busy creates a progress bar to indicate when the cook can move again */
                        for (Ingredient ingredient : cooks.get(selected).CookStack) {
                            if ((ingredient.name == "patty") && (!ingredient.getState()) && (!ingredientDone)) {
                                selectedIngredient = ingredient;
                            }
                        }
                        if (!(selectedIngredient == null)) {
                            cooks.get(selected).isBusy = true;
                            createProgressBar(24, 86, cooks.get(selected));
                            fryingClicked++;
                            /** used for the flipping mechanism (the station has to be pressed twice for the patty to be prepared) */
                            if ((fryingClicked) % 2 == 0) {
                                ingredientDone = true;
                                cooks.get(selected).CookStack.push(cookedPatty);
                                pattyAtFrying = false;
                            } else {
                                cooks.get(selected).CookStack.remove(selectedIngredient);
                                pattyAtFrying = true;
                            }
                        } else {
                            /** create message to indicate that there are no ingredients in the current cook's stack to be prepared */
                            if (pattyAtFrying) {
                                cooks.get(selected).isBusy = true;
                                createProgressBar(24, 86, cooks.get(selected));
                                fryingClicked++;
                                cooks.get(selected).CookStack.push(cookedPatty);
                                pattyAtFrying = false;
                            }
                        }
                    }
                }
            }
        });
        gameStage.addActor(fryingClickable);

        /** baking station */
        bakingClickable = createImageClickable(32, 32);
        /** function exectutes when you press on the baking station on screen */
        /** it sets the baking station as the currently selected station - this moves the cook to the baking station */
        bakingClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stationSelected.set(selected, 2);
            }
        });
        gameStage.addActor(bakingClickable);

        /** bin station */
        binClickable = createImageClickable(32, 32);
        /** function exectutes when you press on the bin station on screen */
        /** it sets the bin station as the currently selected station - this moves the cook to the bin station */
        /** if the cook is by the bin and presses on the bin it deletes the top ingredient on the current cook's stack */
        binClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                stationSelected.set(selected, 3);
                /** if statement that checks if the current cook is at the bin */
                if ((Math.abs(cooks.get(selected).CookBody.getY() - 32f) < 2) && (Math.abs(cooks.get(selected).CookBody.getX() - 0f) < 2)) {
                    if (cooks.get(selected).CookStack.size() > 0) {
                        cooks.get(selected).CookStack.pop();
                    }
                }
            }
        });
        gameStage.addActor(binClickable);

        /** cutting station */
        cuttingClickable = createImageClickable(64, 32);
        /** function exectutes when you press on the cutting station on screen */
        /** it sets the cutting station as the currently selected station - this moves the cook to the cutting station */
        cuttingClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stationSelected.set(selected, 4);
                if ((Math.abs(cooks.get(selected).CookBody.getY() - 28f) < 2) && (Math.abs(cooks.get(selected).CookBody.getX() - 48f) < 2)) {
                    /** Can only prep if the cook is not busy */
                    if (!(cooks.get(selected).isBusy)) {
                        /** used to limit to preping only one ingredient per press */
                        boolean ingredientDone = false;
                        /** preps the first vegetable in the current cook's stack after pressing the station again */
                        /** while busy creates a progress bar to indicate when the cook can move again */
                        Ingredient selectedIngredient = null;
                        for (Ingredient ingredient : cooks.get(selected).CookStack) {
                            if ((ingredient.name == "lettuce") && (!ingredient.getState()) && (!ingredientDone)) {
                                selectedIngredient = ingredient;
                            } else if ((ingredient.name == "tomato") && (!ingredient.getState()) && (!ingredientDone)) {
                                selectedIngredient = ingredient;
                            }
                        }
                        if (!(selectedIngredient == null)) {
                            cooks.get(selected).isBusy = true;
                            createProgressBar(40, 50, cooks.get(selected));
                            selectedIngredient.prepare();
                            selectedIngredient.updateCurrentTexture();
                            ingredientDone = true;
                        }
                    }
                }
            }
        });
        gameStage.addActor(cuttingClickable);

        /** serving station */
        servingClickable = createImageClickable(32, 56);
        /** function exectutes when you press on the serving station on screen */
        /** it sets the serving station as the currently selected station - this moves the cook to the serving station */
        /** when the cook arrives the serving screen is shown */
        servingClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stationSelected.set(selected, 5);
                if ((Math.abs(cooks.get(selected).CookBody.getY() - 48f) < 2) && (Math.abs(cooks.get(selected).CookBody.getX() - 80f) < 2)) {
                    showServingScreen = true;
                    cooks.get(selected).isBusy = true;
                }
            }
        });
        gameStage.addActor(servingClickable);

        /** adding the station clickables to the screen */
        pantryClickable.setPosition(0, 64);
        fryingClickable.setPosition(32, 64);
        bakingClickable.setPosition(64, 64);
        binClickable.setPosition(0, 0);
        cuttingClickable.setPosition(32, 0);
        servingClickable.setPosition(96, 16);

        /** close button for station pop ups */
        XbtnClickable = createImageClickable(new Texture("Xbtn.png"), 16, 16);
        /** function executes after clicking on the close button */
        /** hides any menus that pop up */
        XbtnClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hidePantryScreen();
                hideServingScreen();
                cooks.get(selected).isBusy = false;
            }
        });

        /** pantry screen frame */
        pantryScreenFrameRegion = new TextureRegion(new Texture("pantryFrame.png"));
        pantryScreenFrame = new ImageButton(new TextureRegionDrawable(pantryScreenFrameRegion));
        pantryScreenFrame.setSize(140, 92);

        /** Pantry screen buttons
         * The functions executes after clicking on any of the ingredient buttons on the pantry screen
         * Adds the ingredient to the current cook's stack (if it's less than 5 items) */

        /** unprepared lettuce button */
        lettuceClickable = createImageClickable(new Texture("lettuce.png"), 24, 24);
        lettuceClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (cooks.get(selected).CookStack.size() < 5) {
                    cooks.get(selected).CookStack.push(new Ingredient("lettuce", new Texture("lettuce.png"), new Texture("prepdLettuce.png")));
                }
            }
        });

        /** unprepared tomato button */
        tomatoClickable = createImageClickable(new Texture("tomato.png"), 24, 24);
        tomatoClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (cooks.get(selected).CookStack.size() < 5) {
                    cooks.get(selected).CookStack.push(new Ingredient("tomato", new Texture("tomato.png"), new Texture("prepdTomato.png")));
                }
            }
        });

        /** unprepared buns button */
        bunsClickable = createImageClickable(new Texture("buns.png"), 24, 24);
        bunsClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (cooks.get(selected).CookStack.size() < 5) {
                    Ingredient buns = new Ingredient("buns", new Texture("buns.png"), new Texture("buns.png"));
                    buns.prepare();
                    cooks.get(selected).CookStack.push(buns);
                }
            }
        });

        /** unprepared patty button */
        pattyClickable = createImageClickable(new Texture("rawPatty.png"), 24, 24);
        pattyClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (cooks.get(selected).CookStack.size() < 5) {
                    cooks.get(selected).CookStack.push(new Ingredient("patty", new Texture("rawPatty.png"), new Texture("prepdPatty.png")));
                }
            }
        });

        /** serving screen frame */
        servingScreenFrameRegion = new TextureRegion(new Texture("servingFrame.png"));
        servingScreenFrame = new ImageButton(new TextureRegionDrawable(servingScreenFrameRegion));
        servingScreenFrame.setSize(140, 92);

         /**
          * Serving screen buttons
          * The functions executes after clicking on any of the ingredient buttons on the serving screen
          * Serves the item if all the required prepared ingredients are in the current cook's stack
          */

        /** burger button */
        burgerClickable = createImageClickable(new Texture("burger.png"), 24, 24);
        burgerClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Burger recipe = new Burger();
                Ingredient buns = new Ingredient("buns", null, null);
                buns.prepare();
                Ingredient patty = new Ingredient("patty", null, null);
                patty.prepare();
                Ingredient lettuce = new Ingredient("lettuce", null, null);
                lettuce.prepare();
                if (recipe.has(cooks.get(selected).CookStack)) {
                    if (customers.get(customerCount).customerOrder.getName() == "burger") {
                        cooks.get(selected).CookStack.remove(buns);
                        cooks.get(selected).CookStack.remove(patty);
                        cooks.get(selected).CookStack.remove(lettuce);
                        customers.get(customerCount).orderComplete = true;
                        hideServingScreen();
                        cooks.get(selected).isBusy = false;
                    }
                } else {
                    /** some or all ingredients are not in the current cook's stack */
                }
            }
        });

        /** salad button */
        saladClickable = createImageClickable(new Texture("salad.png"), 24, 24);
        saladClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Salad recipe = new Salad();
                Ingredient tomato = new Ingredient("tomato", null, null);
                tomato.prepare();
                Ingredient lettuce = new Ingredient("lettuce", null, null);
                lettuce.prepare();
                if (recipe.has(cooks.get(selected).CookStack)) {
                    if (customers.get(customerCount).customerOrder.getName() == "salad") {
                        cooks.get(selected).CookStack.remove(tomato);
                        cooks.get(selected).CookStack.remove(lettuce);
                        customers.get(customerCount).orderComplete = true;
                        hideServingScreen();
                        cooks.get(selected).isBusy = false;
                    }
                } else {
                    /** some or all ingredients are not in the current cook's stack */
                }
            }
        });
    }

    private static TextureRegionDrawable getColoredDrawable(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        return drawable;
    }

    /** Used when the clickable region has a texture */
    private ImageButton createImageClickable(Texture texture, float width, float height) {
        TextureRegion region = new TextureRegion(texture);
        ImageButton clickable = new ImageButton(new TextureRegionDrawable(region));
        clickable.setSize(width, height);
        clickable.addListener(cursorHovering);
        return clickable;
    }

    /** Used to create an invisible clickable region */
    private ImageButton createImageClickable(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        TextureRegion region = new TextureRegion(new Texture(pixmap));
        ImageButton clickable = new ImageButton(new TextureRegionDrawable(region));
        clickable.setSize(width, height);
        clickable.addListener(cursorHovering);
        return clickable;
    }

    @Override
    public void render(float delta) {
        gameCam.update();
        renderer.setView(gameCam);

        ScreenUtils.clear(0, 0, 0, 0);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(gameStage);
        renderer.render();

        /** call functions which determine key gameplay elements */
        gameStage.act();
        updateProgressBars();
        updateBatch();
        showCookStack();
        showStationScreens();
        showOrders(delta);
        showRepPoints();
        customerOperations();
        processInput();
        gameStage.draw();

        if (pattyAtFrying) {
            game.batch.begin();
            game.batch.draw(flipBtn, 30, 80);
            game.batch.end();
        }

        for (int i = 0; i < cookCount; i++) {
            if (!cooks.get(i).isBusy) {
                cooks.get(i).move(stationSelected.get(i), cooks.get(i).CookBody, stationSelected);
            }
        }
    }
    private void showRepPoints(){
        game.batch.begin();
        int x = 146;
        game.batch.draw(RepLabel,130,134);
        for(int i = 0; i<Rep; i++){
            game.batch.draw(RepPoint,x,135);
            x += 6;
        }
        game.batch.end();
    }
    private void customerOperations() {
        /** move the customers to the counter */
        if (!customers.get(customerCount).atCounter) {
            customers.get(customerCount).move();
        } else if (customers.get(customerCount).orderComplete) {
            /** make the customer leave */
            customers.get(customerCount).move();
            if (customers.get(customerCount).body.getX() > 148) {
                customers.get(customerCount).body.remove();
                if (customerNumber != 0) {
                    /** check if the game is in endless mode or not */
                    if (customerCount != customerNumber - 1) {
                        /** spawn new customer */
                        customers.add(new Customer(new Actor()));
                        customerCount += 1;
                    } else {
                        /** end game by taking the time at the game end and going to the time screen */
                        Duration timeTaken = Duration.between(gameTime, Instant.now());
                        alienJazz.stop();
                        game.setScreen(new EndGameScreen(game, timeTaken,Rep));
                    }
                } else {
                    /** TODO endless mode */
                    customers.add(new Customer(new Actor()));
                    customerCount += 1;
                }
            }
        }
    }

    /** generate the cooks */
    private void spawnCooks() {
        for (int i = 0; i < cookCount; i++) {
            Cook cook = new Cook(new Actor());
            cook.CookBody.setWidth(16);
            cook.CookBody.setHeight(23);
            /** scale information */
            cook.CookBody.setScaleX(game.GAME_WIDTH / 16f);
            cook.CookBody.setScaleY(game.GAME_HEIGHT / 23f);
            /** cooks are stored in an array to make it easier to keep track of all things relating to them
            /** I love arrays so much */
            cooks.add(cook);
            gameStage.addActor(cook.CookBody);
            stationSelected.add(i);
        }
    }

    /**process user input */
    private void processInput() {
        /** number keys are used to select which cook is being controlled currently */
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            selected = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            selected = 1;
        }
        /** TODO add statements for adding more cooks here */
        if (cookCount > 2 && Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            selected = 2;
        }
        for (int i = 0; i < cooks.size; i++) {
            if (cooks.get(i).CookBody.isTouchFocusTarget()) {
                selected = i;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            /** debug option to mark the current customers order as complete, moving them on */
            customers.get(customerCount).orderComplete = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            /** return to main menu */
            game.setScreen(new MainMenuScreen(game));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            /** used for debugging */
            /** prepares all ingredients in current cook's stack */
            for (Ingredient ingredient : cooks.get(selected).CookStack) {
                ingredient.prepare();
                ingredient.updateCurrentTexture();
            }
        }
    }

    /**update the cooks on the screen */
    private void updateBatch() {
        /** this section assigns each cook a sprite from the list idles */
        /** you could potentially update this to allow for animations for the cooks when they move */
        game.batch.begin();
        int index = 0;
        for (Cook cook : cooks) {
            game.batch.draw(idles.get(index), cook.CookBody.getX(), cook.CookBody.getY());
            index++;
        }
        game.batch.draw(plateTex, 164, 25);
        game.batch.draw(cookStackTitle, 164, 120);
        game.batch.draw(idles.get(selected), 168, 1);
        game.batch.draw(custSkins.getSprite(customers.get(customerCount).name), customers.get(customerCount).body.getX(), customers.get(customerCount).body.getY());
        game.batch.draw(selectedCook, cooks.get(selected).CookBody.getX(), cooks.get(selected).CookBody.getY() + 26);
        game.batch.end();
    }

    private void showOrders(float dt) {
        /** displays the orders at the top of the screen */
        int x = 1;
        int y = 112;
        for (Customer customer : customers) {
            if ((customer.atCounter) && (!customer.orderComplete)) {
                timeCount += dt;
                /**one second has passed */
                if(timeCount >= 1){
                    /**update order timer */
                    if(customer.customerOrder.getOrderTime() >= 0){
                        customer.customerOrder.orderTime --;
                    }
                    timeCount = 0;
                    if(customer.customerOrder.getOrderTime()==0){
                        /**Uncomment line below if you want the customer to leave after the order timer is gone */
                        /**customer.orderComplete = true; */
                        Rep--;
                    }
                }
                game.batch.begin();
                game.batch.draw(customer.customerOrder.getOrderTexture(), x, y);
                game.batch.draw(customer.customerOrder.getRecipe().getSpeechBubbleTexture(), customer.body.getX() - 10, customer.body.getY() + 17);
                /**order timer sets to 0 when it reaches -1 */
                if(customer.customerOrder.getOrderTime()>-1){
                    font.draw(game.batch, Integer.toString(customer.customerOrder.getOrderTime()), x+30, y+10);
                } else {
                    font.draw(game.batch, "0", x+30, y+10);
                }
                
                game.batch.end();
                /** increase x value if there is more than one current order */
                x += 41;
            }
        }
    }

    private void showCookStack() {
        /** display the stack of ingredients being held by the current cook */
        float x = 164;
        float y = 32;
        game.batch.begin();
        for (Ingredient ingredient : cooks.get(selected).CookStack) {
            game.batch.draw(ingredient.getCurrentTexture(), x, y);
            y += 18;
        }
        game.batch.end();
    }

    private void showStationScreens() {
        for (Cook cook : cooks) {
            if ((Math.abs(cook.CookBody.getY() - 64f) < 2) && (Math.abs(cook.CookBody.getX() - 0f) < 2)) {
                showPantryScreen();
            }
            if ((Math.abs(cook.CookBody.getY() - 48f) < 2) && (Math.abs(cook.CookBody.getX() - 80f) < 2)) {
                showServingScreen();
            }
        }
    }

    private void showServingScreen() {
        if (showServingScreen) {
            gameStage.addActor(servingScreenFrame);
            gameStage.addActor(XbtnClickable);
            XbtnClickable.toFront();
            gameStage.addActor(burgerClickable);
            gameStage.addActor(saladClickable);
            servingScreenFrame.setPosition(10, 10);
            XbtnClickable.setPosition(7, 88);
            burgerClickable.setPosition(25, 66);
            saladClickable.setPosition(53, 66);
            showServingScreen = false;
        }
    }

    private void showPantryScreen() {
        if (showPantryScreen) {
            gameStage.addActor(pantryScreenFrame);
            gameStage.addActor(XbtnClickable);
            XbtnClickable.toFront();
            gameStage.addActor(lettuceClickable);
            gameStage.addActor(tomatoClickable);
            gameStage.addActor(bunsClickable);
            gameStage.addActor(pattyClickable);
            pantryScreenFrame.setPosition(10, 10);
            XbtnClickable.setPosition(7, 88);
            lettuceClickable.setPosition(25, 66);
            tomatoClickable.setPosition(53, 66);
            bunsClickable.setPosition(81, 66);
            pattyClickable.setPosition(110, 72);
            showPantryScreen = false;
        }
    }

    private void hidePantryScreen() {
        /** moves pantry screen offscreen */
        pantryScreenFrame.setPosition(10000, -1);
        XbtnClickable.setPosition(10000, -1);
        lettuceClickable.setPosition(10000, -1);
        tomatoClickable.setPosition(10000, -1);
        bunsClickable.setPosition(10000, -1);
        pattyClickable.setPosition(10000, -1);
    }

    private void hideServingScreen() {
        /** moves serving screen offscreen */
        servingScreenFrame.setPosition(10000, -1);
        XbtnClickable.setPosition(10000, -1);
        burgerClickable.setPosition(10000, -1);
        saladClickable.setPosition(10000, -1);
    }

    public void createProgressBar(float x, float y, Cook selectedCook) {
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = getColoredDrawable(20, 5, Color.GREEN);
        style.knob = getColoredDrawable(0, 5, Color.WHITE);
        style.knobAfter = getColoredDrawable(20, 5, Color.WHITE);
        ProgressBar bar = new ProgressBar(0, 7, 0.05f, false, style);
        bar.setWidth(30);
        bar.setHeight(5);
        bar.setValue(15f);
        bar.setX(x);
        bar.setY(y);
        gameStage.addActor(bar);
        bars.put(bar, selectedCook);
    }

    private void updateProgressBars() {
        if (!bars.isEmpty()) {
            for (ProgressBar bar : bars.keySet()) {
                bar.setValue(bar.getValue() - 0.05f);
                if (bar.getValue() == 0) {
                    gameStage.getActors().removeValue(bar, false);
                    /**unbusy the cook */
                    bars.get(bar).isBusy = false;
                    bars.remove(bar);
                }
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        view.update(width, height);
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        game.batch.dispose();
        gameStage.dispose();
        alienJazz.dispose();
    }
}