package com.mygdx.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.ConfigHandler;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.Screens.GameScreen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import org.json.JSONObject;
import org.mockito.Mockito;

@RunWith(GdxTestRunner.class)
public class GameScreenTests {
    private PiazzaPanic game;
    private FitViewport view;
    private GameScreen gameScreen;
    private ConfigHandler configHandler;

//    @Before
//    public void setUp() throws IOException {
//        configHandler = new ConfigHandler(Gdx.files.getLocalStoragePath() + "..\\core\\src\\com\\mygdx\\game\\config.json");
//        JSONObject config = configHandler.getConfig();
//        game = Mockito.mock(PiazzaPanic.class);
//        view = new FitViewport(game.GAME_WIDTH, game.GAME_HEIGHT);
//        view.getCamera().position.set(game.GAME_WIDTH /2, game.GAME_HEIGHT/2, 1f);
//        gameScreen = new GameScreen(game, view, false, false, "", config);
//    }

    @Test
    public void alwaysSuccess() {
        assertTrue(true);
    }

}
