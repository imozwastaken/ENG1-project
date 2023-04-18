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
import com.badlogic.gdx.math.MathUtils;
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
import com.mygdx.game.*;
import com.mygdx.game.Clickables.*;
import com.mygdx.game.Food.Burger;
import com.mygdx.game.Food.Ingredient;
import com.mygdx.game.Food.Order;
import com.mygdx.game.Food.Salad;

import com.mygdx.game.Powerups.Powerups;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameScreen implements Screen {

    private int customerNumber = 5;
    PiazzaPanic game;
    FitViewport view;
    Stage gameStage;
    Money money;
    Powerups powerups;
    // map and camera stuff
    TmxMapLoader mapLoader;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera gameCam;
    // Start the game with 3 reputation points
    int Rep = 3;
    Texture RepLabel = new Texture("REP.png");
    Texture RepPoint = new Texture("REPHeart.png");
    // customer number determines how many customers will spawn over the course of
    // the game
    // 0 means infinite
    private final Array<Cook> cooks;
    private final ArrayList<ArrayList<Customer>> customers;
    // sprite handling
    Sprite alex;
    Sprite amelia;
    Sprite adam;
    FileHandle charIdles;
    Skin skin;
    Skin custSkins;
    ArrayList<Sprite> idles = new ArrayList<>();
    // cook and customer control variables
    int selected = 0;
    ArrayList<Integer> stationSelected = new ArrayList<>();
    // control the number of cooks
    int cookCount = 2; // control how many cooks spawn -> update to allow for the value to increase
    // take the time at the start of the game to display the time taken to complete
    // the round

    long gameTime = System.currentTimeMillis();

    // list of active orders
    ArrayList<Order> orders = new ArrayList<>();
    // used to count how much time has passed after an order is placed
    float timeCount = 0;
    // order timer font
    BitmapFont font = new BitmapFont();
    // progress bars
    HashMap<ProgressBar, Cook> bars;
    // music
    Music alienJazz = Gdx.audio.newMusic(Gdx.files.internal("Alien_Jazz_Ridley_Coyte.mp3"));
    // stations
    ImageButton pantryClickable;
    ImageButton fryingClickable;
    int fryingClicked = 0;
    int bakingClicked = 0;
    boolean pattyAtFrying = false;
    boolean pizzaAtBaking = false;
    Texture flipBtn = new Texture("flipBtn.png");
    ImageButton bakingClickable;
    ImageButton cuttingClickable;
    ImageButton binClickable;
    ImageButton servingClickable;
    ImageButton extraTimeClickable;
    // pantry and serving screen frames
    TextureRegion pantryScreenFrameRegion;
    ImageButton pantryScreenFrame;
    TextureRegion servingScreenFrameRegion;
    ImageButton servingScreenFrame;
    // clickables
    ImageButton XbtnClickable;
    ImageButton lettuceClickable;
    ImageButton tomatoClickable;
    ImageButton bunsClickable;
    ImageButton pattyClickable;
    ImageButton burgerClickable;
    ImageButton saladClickable;
    ImageButton speedClickable;
    ImageButton repClickable;
    ImageButton saveClickable;
    ImageButton pizzaClickable;

    ImageButton stationSpeedClickable;
    int NumServed = 0;

    // when you hover over a clickable it changes the cursor to a hand
    // this listener is added to all clickables
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
    // UI elements
    Texture plateTex = new Texture("plate.png");
    Texture cookStackTitle = new Texture("cookStackTitle.png");
    Texture selectedCook = new Texture("selected.png");
    // Order Textures
    Texture burgerOrderTexture = new Texture("orderBurger.png");
    Texture saladOrderTexture = new Texture("orderSalad.png");
    Boolean showPantryScreen = false;
    Boolean showServingScreen = false;

    Utils utils;
    Frying frying;
    Bin bin;
    Pantry pantry;
    Baking baking;
    Cutting cutting;
    Serving serving;
    SaladClickable saladC;
    BurgerClickable burger;
    LettuceClickable lettuce;
    TomatoClickable tomato;
    BunClickable bun;
    PattyClickable patty;
    Savegame save;
    PizzaClickable pizza;

    SpeedPowerup speedPowerup;
    RepPowerup repPowerup;
    ExtratimePowerup extratimePowerup;
    Random rand = new Random();

    StationSpeedPowerup stationSpeedPowerup;

    private int customerCount = 0;

    private Boolean endless = false;
    JSONObject obj;
    JSONObject config;

    public GameScreen(PiazzaPanic game, FitViewport port, Boolean isEndless, Boolean isLoad, String Loadfile,
            JSONObject config) throws IOException {

        // initialise the game
        this.game = game;
        this.view = port;
        this.config = config;
        this.customerNumber = config.getInt("customersToServe");
        System.out.println("customerNumber: " + customerNumber);
        this.utils = new Utils();
        this.frying = new Frying(game, utils, this);
        this.bin = new Bin(game, utils, this);
        this.pantry = new Pantry(game, utils, this);
        this.baking = new Baking(game, utils, this);
        this.cutting = new Cutting(game, utils, this);
        this.serving = new Serving(game, utils, this);
        this.saladC = new SaladClickable(game, utils, this);
        this.burger = new BurgerClickable(game, utils, this);
        this.bun = new BunClickable(utils, this);
        this.lettuce = new LettuceClickable(utils, this);
        this.tomato = new TomatoClickable(utils, this);
        this.patty = new PattyClickable(utils, this);
        this.pizza = new PizzaClickable(utils, this);
        try {
            this.save = new Savegame(game, utils, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        money = new Money(game, config.getString("difficulty"));

        powerups = new Powerups(game, money, this, config.getString("difficulty"));
        this.speedPowerup = new SpeedPowerup(utils, this, powerups);
        this.repPowerup = new RepPowerup(utils, this, powerups);
        this.stationSpeedPowerup = new StationSpeedPowerup(utils, this, powerups);
        this.extratimePowerup = new ExtratimePowerup(utils, this, powerups);

        gameStage = new Stage(view, game.batch);

        // load the map and camera
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("KitchenMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam = new OrthographicCamera();
        view.setCamera(gameCam);
        view.setWorldSize(192, 144);
        gameCam.position.set(view.getWorldWidth() / 2, view.getWorldHeight() / 2, 0);
        // set order timer font color
        font.setColor(Color.BLACK);
        font.getData().setScale(0.5f);
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

        // music control
        // music composed by Ridley Coyte
        if (!config.getBoolean("muteMusic")) {
            alienJazz.setLooping(true);
            alienJazz.play();
        }

        // create the instances of the cooks and first customer.
        cooks = new Array<Cook>();
        spawnCooks();
        customers = new ArrayList<ArrayList<Customer>>();
        ArrayList<Customer> tmp = new ArrayList<Customer>();
        int tmpI = CustomersToServe();
        System.out.println("Customers to serve: " + tmpI);

        for (int i = 0; i < tmpI; i++) {
            tmp.add(new Customer(new Actor()));
        }
        System.out.print(tmp);
        customers.add(tmp);
        increaseNumServed(tmpI);
        // array of all progressbars created (used to update all of them in
        // updateProgressBars function)
        bars = new HashMap<ProgressBar, Cook>();
        // pantry station
        pantryClickable = pantry.getPantryClickable();
        gameStage.addActor(pantryClickable);
        // frying station
        fryingClickable = frying.createFryingClickable();
        gameStage.addActor(fryingClickable);
        // baking station
        bakingClickable = baking.getBakingClickable();
        gameStage.addActor(bakingClickable);
        // bin station
        binClickable = bin.getBinClickable();
        gameStage.addActor(binClickable);
        // cutting station
        cuttingClickable = cutting.getCuttingClickable();
        gameStage.addActor(cuttingClickable);
        // serving station
        servingClickable = serving.getServingClickable();
        gameStage.addActor(servingClickable);
        // save game
        saveClickable = save.getSaveClickable();
        gameStage.addActor(saveClickable);
        // pizza
        // adding the station clickables to the screen
        pantryClickable.setPosition(0, 64);
        fryingClickable.setPosition(32, 64);
        bakingClickable.setPosition(64, 64);
        binClickable.setPosition(0, 0);
        cuttingClickable.setPosition(32, 0);
        servingClickable.setPosition(96, 16);
        saveClickable.setPosition(170, 100);

        // close button for station pop ups
        XbtnClickable = createImageClickable(new Texture("Xbtn.png"), 16, 16);
        // function executes after clicking on the close button
        // hides any menus that pop up
        XbtnClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hidePantryScreen();
                hideServingScreen();
                cooks.get(selected).isBusy = false;
            }
        });

        // pantry screen frame
        pantryScreenFrameRegion = new TextureRegion(new Texture("pantryFrame.png"));
        pantryScreenFrame = new ImageButton(new TextureRegionDrawable(pantryScreenFrameRegion));
        pantryScreenFrame.setSize(140, 92);

        /*
         * Pantry screen buttons
         * The functions executes after clicking on any of the ingredient buttons on the
         * pantry screen
         * Addes the ingredient to the current cook's stack (if it's less than 5 items)
         */
        // unprepared lettuce button
        lettuceClickable = lettuce.getLettuceClickable();
        // unprepared tomato button
        tomatoClickable = tomato.getTomatoClickable();
        // unprepared buns button
        bunsClickable = bun.getBunClickable();
        // unprepared patty button
        pattyClickable = patty.getPattyClickable();
        speedClickable = speedPowerup.getSpeedClickable();
        repClickable = repPowerup.getRepButton();
        stationSpeedClickable = stationSpeedPowerup.getStationClickable();
        extraTimeClickable = extratimePowerup.getExtraTimeClickable();
        pizzaClickable = pizza.getPizzaClickable();
        // serving screen frame
        servingScreenFrameRegion = new TextureRegion(new Texture("servingFrame.png"));
        servingScreenFrame = new ImageButton(new TextureRegionDrawable(servingScreenFrameRegion));
        servingScreenFrame.setSize(140, 92);

        /*
         * Serving screen buttons
         * The functions executes after clicking on any of the ingredient buttons on the
         * serving screen
         * Serves the item if all the required prepared ingredients are in the current
         * cook's stack
         */
        // burger button
        burgerClickable = burger.getBurgerClickable();
        // salad button
        saladClickable = saladC.getSaladClickable();
        this.endless = isEndless;
        if (isLoad) {
            try {
                loadJSON(Loadfile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public long getGameTime() {
        return gameTime;
    }

    public void setSationSelected(int value) {
        stationSelected.set(selected, value);
    }

    public int getSelected() {
        return selected;
    }

    public Array<Cook> getCooks() {
        return cooks;
    }

    public void incrementFryingClicked() {
        fryingClicked++;
    }

    public void incrementBakingClicked() {
        bakingClicked++;
    }

    public int getFryingClicked() {
        return fryingClicked;
    }

    public int getBakingClicked() {
        return bakingClicked;
    }

    public void setPizzaAtBaking(Boolean isBaking) {
        pizzaAtBaking = isBaking;
    }

    public boolean getPizzaAtBaking() {
        return pizzaAtBaking;
    }

    public void setPattyAtFrying(Boolean isFrying) {
        pattyAtFrying = isFrying;
    }

    public boolean getPattyAtFrying() {
        return pattyAtFrying;
    }

    public void setShowPantryScreen(Boolean show) {
        showPantryScreen = show;
    }

    public ArrayList<Integer> getStationSelected() {
        return stationSelected;
    }

    public void setStationSelected(int value) {
        stationSelected.set(selected, value);
    }

    public void setShowServingScreen(Boolean value) {
        showServingScreen = value;
    }

    public ArrayList<ArrayList<Customer>> getCustomers() {
        return customers;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void increaseNumServed(int amnt) {
        NumServed += amnt;
    }

    public void addRep(int amount) {
        Rep += amount;
    }

    public Money getMoney() {
        return money;
    }

    public int getRep() {
        return Rep;
    }

    public void setRep(int rep) {
        Rep = rep;
    }

    public void initialiseLoad(JSONObject obj) {
        Rep = (int) obj.get("rep");
        gameTime = System.currentTimeMillis() + (int) obj.get("timetaken");
        // customerCount = (int) obj.get("customersLeft");
        money.addMoney((int) obj.get("Money"));

    }

    public void loadJSON(String Loadfile) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(Loadfile)));
        obj = new JSONObject(content);
        initialiseLoad(obj);
    }

    private static TextureRegionDrawable getColoredDrawable(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        return drawable;
    }

    // Used when the clickable region has a texture
    private ImageButton createImageClickable(Texture texture, float width, float height) {
        TextureRegion region = new TextureRegion(texture);
        ImageButton clickable = new ImageButton(new TextureRegionDrawable(region));
        clickable.setSize(width, height);
        clickable.addListener(cursorHovering);
        return clickable;
    }

    // Used to create an invisible clickable region
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

        // call functions which determine key gameplay elements
        gameStage.act();

        updateProgressBars();
        updateBatch();
        showCookStack();
        showStationScreens();
        showOrders(delta);
        showRepPoints();
        try {
            customerOperations();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            processInput();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        powerups.render();
        powerups.checkPowerups();
        gameStage.draw();

        if (pattyAtFrying) {
            game.batch.begin();
            game.batch.draw(flipBtn, 30, 80);
            game.batch.end();
        }

        if (pizzaAtBaking) {
            game.batch.begin();
            game.batch.draw(flipBtn, 85, 78);
            game.batch.end();
        }

        for (int i = 0; i < cookCount; i++) {
            if (!cooks.get(i).isBusy) {
                cooks.get(i).move(stationSelected.get(i), cooks.get(i).CookBody, stationSelected, powerups);
            }
        }
        money.render();

        // cooks.get(selected).doUserInput(cooks.get(selected));

    }

    private void showRepPoints() {
        game.batch.begin();
        int x = 146;
        game.batch.draw(RepLabel, 130, 134);
        for (int i = 0; i < Rep; i++) {
            game.batch.draw(RepPoint, x, 135);
            x += 6;
        }
        game.batch.end();
    }

    private void customerOperations() throws IOException {
        // Check if all customers have been served
        boolean allComplete = false;
        for (Customer c : customers.get(customerCount)) {
            if (!c.selfComplete) {
                allComplete = false;
                break;
            } else {
                allComplete = true;
            }
        }

        // move the customers to the counter
        for (int i = 0; i < customers.get(customerCount).size(); i++) {
            if (!customers.get(customerCount).get(i).atCounter) {
                customers.get(customerCount).get(i).move();
            } else if (allComplete) {
                // make the customer leave
                System.out.println("at counter kl" + customers.get(customerCount).get(i).body.getX());
                customers.get(customerCount).get(i).move();

                if (customers.get(customerCount).get(i).body.getX() > 148) {
                    System.out.println("X is past 148" + customers.get(customerCount).get(i).body.getX());
                    customers.get(customerCount).get(i).body.remove();
                    if (!endless) {
                        // check if the game is in endless mode or not
                        if (NumServed != customerNumber) {
                            // spawn new customer

                            ArrayList<Customer> tmp = new ArrayList<Customer>();
                            int tmpI = CustomersToServe();
                            System.out.println("Customers to serve: " + tmpI);
                            for (int j = 0; j < tmpI; j++) {
                                tmp.add(new Customer(new Actor()));
                            }
                            if (powerups.hasExtraTime()) {
                                System.out.println("Extra time powerup active");
                                for (Customer c : tmp) {
                                    c.customerOrder.setOrderTime(c.customerOrder.getOrderTime() + 10);
                                }
                            } else {
                                System.out.println("Extra time powerup not active");
                            }

                            customers.add(tmp);
                            customerCount += 1;
                            increaseNumServed(tmpI);
                            System.out.println("Currently served: " + NumServed);
                        } else {
                            // end game by taking the time at the game end and going to the time screen

                            long timeTaken = System.currentTimeMillis() - gameTime;
                            alienJazz.stop();
                            if (endless) {
                                game.setScreen(new EndGameScreen(game, timeTaken, 0, true, customerCount));
                            } else {
                                game.setScreen(new EndGameScreen(game, timeTaken, Rep, false, 0));
                            }

                        }
                    } else {
                        // TODO endless mode
                        ArrayList<Customer> tmp = new ArrayList<Customer>();
                        int tmpI = CustomersToServe();
                        System.out.println("Customers to serve: cc " + tmpI);

                        for (int j = 0; j < tmpI; j++) {
                            tmp.add(new Customer(new Actor()));
                        }
                        customers.add(tmp);
                        customerCount += 1;
                        increaseNumServed(tmpI);
                    }
                }
            }
        }

    }

    // generate the cook
    private void spawnCooks() {
        for (int i = 0; i < cookCount; i++) {
            Cook cook = new Cook(new Actor());
            cook.CookBody.setWidth(16);
            cook.CookBody.setHeight(23);
            // scale information
            cook.CookBody.setScaleX(game.GAME_WIDTH / 16f);
            cook.CookBody.setScaleY(game.GAME_HEIGHT / 23f);
            // cooks are stored in an array to make it easier to keep track of all things
            // relating to them
            // I love arrays so much
            cooks.add(cook);
            gameStage.addActor(cook.CookBody);
            stationSelected.add(i);
        }
    }

    public int CustomersToServe() {
        int num = MathUtils.random(0, 10);
        if (customerCount <= 1) {

            if (num <= 7) {
                return 1;
            } else if (num <= 9) {
                return 2;
            } else {
                return 3;
            }
        } else if (customerCount == 2) {
            if (num <= 5) {
                return 1;
            } else if (num <= 8) {
                return 2;
            } else {
                return 3;
            }
        } else if (customerCount == 3) {
            if (num <= 3) {
                return 1;
            } else if (num <= 7) {
                return 2;
            } else {
                return 3;
            }
        } else if (customerCount <= 5) {
            if (num <= 2) {
                return 1;
            } else if (num <= 5) {
                return 2;
            } else {
                return 3;
            }
        }
        return num % 3;
    }

    // process user input
    private void processInput() throws IOException {
        // number keys are used to select which cook is being controlled currently
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            selected = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            selected = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            powerups.setSpeedMultiplier(2);
        }
        // TODO add statements for adding more cooks here
        if (cookCount > 2 && Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            selected = 2;
        }
        for (int i = 0; i < cooks.size; i++) {
            if (cooks.get(i).CookBody.isTouchFocusTarget()) {
                selected = i;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            // debug option to mark the current customers order as complete, moving them on
            customers.get(customerCount).get(0).orderComplete = true;
            money.addMoney(100);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            // return to main menu
            game.setScreen(new MainMenuScreen(game));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.M)) {
            // used for debugging
            // prepares all ingredients in current cook's stack
            for (Ingredient ingredient : cooks.get(selected).CookStack) {
                ingredient.prepare();
                ingredient.updateCurrentTexture();
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.L)) {
            alienJazz.pause();
        }
    }

    // update the cooks on the screen
    private void updateBatch() {
        // this section assigns each cook a sprite from the list idles
        // you could potentially update this to allow for animations for the cooks when
        // they move
        game.batch.begin();
        int index = 0;
        for (Cook cook : cooks) {
            game.batch.draw(idles.get(index), cook.CookBody.getX(), cook.CookBody.getY());
            index++;
        }
        game.batch.draw(plateTex, 164, 25);
        game.batch.draw(cookStackTitle, 164, 120);
        game.batch.draw(idles.get(selected), 168, 1);
        for (int i = 0; i < customers.get(customerCount).size(); i++) {
            game.batch.draw(custSkins.getSprite(customers.get(customerCount).get(i).name),
                    customers.get(customerCount).get(i).body.getX(), customers.get(customerCount).get(i).body.getY());
        }
        game.batch.end();
    }

    private void showOrders(float dt) {
        // displays the orders at the top of the screen
        int x = 1;
        int y = 112;

        timeCount += dt;
        boolean shouldUpdateTimers = false; // Add a new flag to indicate whether timers should be updated

        // Check if one second has passed
        if (timeCount >= 1) {
            shouldUpdateTimers = true;
            timeCount = 0; // Reset timeCount outside the customer loop
        }

        for (ArrayList<Customer> customerArr : customers) {
            for (Customer customer : customerArr) {
                if ((customer.atCounter) && (!customer.selfComplete)) {
                    // Update order timer only if shouldUpdateTimers is true
                    if (shouldUpdateTimers) {
                        if (customer.customerOrder.getOrderTime() >= 0) {
                            customer.customerOrder.orderTime--;
                        }
                    }

                    if (customer.customerOrder.getOrderTime() == 0 && !customer.selfComplete
                            && !customer.orderExpired) {
                        // Uncomment line below if you want the customer to leave after the order timer
                        // is gone
                        // customer.orderComplete = true;
                        customer.orderExpired = true;
                        Rep--;
                        if (Rep == 0) {
                            long timeTaken = System.currentTimeMillis() - gameTime;
                            try {
                                game.setScreen(new EndGameScreen(game, timeTaken, 0, true, NumServed));
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                    game.batch.begin();
                    if (!customer.selfComplete) {
                        game.batch.draw(customer.customerOrder.getOrderTexture(), x, y);
                        game.batch.draw(customer.customerOrder.getRecipe().getSpeechBubbleTexture(),
                                customer.body.getX() - 10, customer.body.getY() + 17);
                        if (customer.customerOrder.getOrderTime() > -1) {
                            font.draw(game.batch, Integer.toString(customer.customerOrder.getOrderTime()), x + 30,
                                    y + 10);
                        } else {
                            font.draw(game.batch, "0", x + 30, y + 10);
                        }
                    }
                    // order timer sets to 0 when it reaches -1

                    game.batch.end();
                    // increase x value if there is more than one current order
                    x += 41;
                }
            }
        }
    }

    private void showCookStack() {
        // display the stack of ingredients being held by the current cook
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
            gameStage.addActor(speedClickable);
            gameStage.addActor(repClickable);
            gameStage.addActor(stationSpeedClickable);
            gameStage.addActor(extraTimeClickable);
            gameStage.addActor(pizzaClickable);
            pantryScreenFrame.setPosition(10, 10);
            XbtnClickable.setPosition(7, 88);
            lettuceClickable.setPosition(25, 66);
            tomatoClickable.setPosition(53, 66);
            bunsClickable.setPosition(81, 66);
            pattyClickable.setPosition(110, 72);
            speedClickable.setPosition(25, 40);
            repClickable.setPosition(53, 40);
            stationSpeedClickable.setPosition(75, 40);
            extraTimeClickable.setPosition(110, 40);
            pizzaClickable.setPosition(25, 17);

            showPantryScreen = false;
        }
    }

    private void hidePantryScreen() {
        // moves pantry screen offscreen
        pantryScreenFrame.setPosition(10000, -1);
        XbtnClickable.setPosition(10000, -1);
        lettuceClickable.setPosition(10000, -1);
        tomatoClickable.setPosition(10000, -1);
        bunsClickable.setPosition(10000, -1);
        pattyClickable.setPosition(10000, -1);
        speedClickable.setPosition(10000, -1);
        repClickable.setPosition(10000, -1);
        stationSpeedClickable.setPosition(10000, -1);
        extraTimeClickable.setPosition(100000, -1);
        pizzaClickable.setPosition(10000, -1);
    }

    public void hideServingScreen() {
        // moves serving screen offscreen
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
        float stepSize = powerups.getStationSpeed() * 0.05f;
        System.out.println(stepSize);
        ProgressBar bar = new ProgressBar(0, 7, stepSize, false, style);
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
                bar.setValue(bar.getValue() - bar.getStepSize());
                if (bar.getValue() == 0) {
                    gameStage.getActors().removeValue(bar, false);
                    // unbusy the cook
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