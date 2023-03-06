package com.mygdx.tests;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Food.Burger;
import com.mygdx.game.Food.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.*;

public class BurgerTest {

    private Burger burger;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void testGetRecipe() {
        assertEquals(3, burger.getRecipe().size());
    }

    @Test
    public void testGetTexture() {
        assertNotNull(burger.getTexture());
        assertTrue(burger.getTexture() != null);
    }

    @Test
    public void testGetSpeechBubbleTexture() {
        assertNotNull(burger.getSpeechBubbleTexture());
        assertTrue(burger.getSpeechBubbleTexture() != null);
    }

    @Test
    public void testHasTrue() {
        Stack<Ingredient> ingredients = new Stack<>();
        ingredients.push(new Ingredient("patty", null, null));
        ingredients.push(new Ingredient("buns", null, null));
        ingredients.push(new Ingredient("lettuce", null, null));
        assertTrue(burger.has(ingredients));
    }

    @Test
    public void testHasFalse() {
        Stack<Ingredient> ingredients = new Stack<>();
        ingredients.push(new Ingredient("patty", null, null));
        ingredients.push(new Ingredient("lettuce", null, null));
        assertFalse(burger.has(ingredients));
    }
}
