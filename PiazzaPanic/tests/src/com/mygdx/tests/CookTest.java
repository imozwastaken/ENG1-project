package com.mygdx.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Cook;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(GdxTestRunner.class)
public class CookTest {

    @Test
    /**
     * This test is here to make sure the testing environment is not broken.
     */
    public void alwaysTrueTest() {
        assertTrue("This test should pass every time", true);
    }

    @Test
    public void testInitialState() {
        Cook cook = new Cook(new Actor());
        cook.CookBody.setWidth(16);
        cook.CookBody.setHeight(23);

        assertFalse(cook.moveable); // check that Cook is not initially moveable
        assertNotNull(cook.CookStack); // check that CookStack is not null
    }

    @Test
    public void testDoUserInputWithNoInput() {
        // save initial position of Cook's Actor
        Cook cook = new Cook(new Actor());
        float initialX = cook.CookBody.getX();
        float initialY = cook.CookBody.getY();

        // call doUserInput with no input
        cook.doUserInput(cook);

        // check that position of Cook's Actor did not change
        assertEquals(initialX, cook.CookBody.getX(), 0);
        assertEquals(initialY, cook.CookBody.getY(), 0);
    }
    @Test
    public void testDoUserInputWithMoveLeft() {
        // create a Cook
        Cook cook = new Cook(new Actor());
        // save initial position of Cook's Actor
        float initialX = cook.CookBody.getX();
        float initialY = cook.CookBody.getY();

        // simulate pressing the left arrow key
        Gdx.input = mock(Input.class);
        when(Gdx.input.isKeyPressed(Input.Keys.A)).thenReturn(true);

        // call doUserInput
        cook.doUserInput(cook);

        // check that position of Cook's Actor moved left
        assertTrue(cook.CookBody.getX() < initialX);
        assertEquals(initialY, cook.CookBody.getY(), 0);
    }
    @Test
    public void testDoUserInputWithMoveRight() {
        // create a Cook
        Cook cook = new Cook(new Actor());
        // save initial position of Cook's Actor
        float initialX = cook.CookBody.getX();
        float initialY = cook.CookBody.getY();

        // simulate pressing the right arrow key
        Gdx.input = mock(Input.class);
        when(Gdx.input.isKeyPressed(Input.Keys.D)).thenReturn(true);

        // call doUserInput
        cook.doUserInput(cook);

        // check that position of Cook's Actor moved right
        assertTrue(cook.CookBody.getX() > initialX);
        assertEquals(initialY, cook.CookBody.getY(), 0);
    }
    @Test
    public void testDoUserInputWithMoveUp() {
        // create a Cook
        Cook cook = new Cook(new Actor());
        // save initial position of Cook's Actor
        float initialX = cook.CookBody.getX();
        float initialY = cook.CookBody.getY();

        // simulate pressing the up arrow key
        Gdx.input = mock(Input.class);
        when(Gdx.input.isKeyPressed(Input.Keys.W)).thenReturn(true);

        // call doUserInput
        cook.doUserInput(cook);

        // check that position of Cook's Actor moved up
        assertEquals(initialX, cook.CookBody.getX(), 0);
        assertTrue(cook.CookBody.getY() > initialY);
    }
    @Test
    public void testDoUserInputWithMoveDown() {
        // create a Cook
        Cook cook = new Cook(new Actor());
        // save initial position of Cook's Actor
        float initialX = cook.CookBody.getX();
        float initialY = cook.CookBody.getY();

        // simulate pressing the down arrow key
        Gdx.input = mock(Input.class);
        when(Gdx.input.isKeyPressed(Input.Keys.S)).thenReturn(true);

        // call doUserInput
        cook.doUserInput(cook);

        // check that position of Cook's Actor moved down
        assertEquals(initialX, cook.CookBody.getX(), 0);
        assertTrue(cook.CookBody.getY() < initialY);
    }
}