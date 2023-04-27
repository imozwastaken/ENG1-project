package com.mygdx.tests;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Clickables.Baking;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.Screens.GameScreen;

import com.mygdx.game.Clickables.*;


import com.mygdx.game.Cook;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class BakingTest {
    private PiazzaPanic game;
    private Utils utils;
    private GameScreen gameScreen;
    private Baking baking;

    @Before
    public void setUp() {
        game = Mockito.mock(PiazzaPanic.class);
        utils = new Utils();
        gameScreen = Mockito.mock(GameScreen.class);
        baking = new Baking(game, utils, gameScreen);
    }

    @Test
    public void bakingClickableIsNotNull() {
        ImageButton bakingClickable = baking.getBakingClickable();
        assertNotNull(bakingClickable);
    }


    // Add more tests as needed for other aspects of the Baking class
}
