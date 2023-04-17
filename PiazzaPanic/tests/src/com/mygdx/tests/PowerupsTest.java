package com.mygdx.tests;

import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Customer;
import com.mygdx.game.Food.Burger;
import com.mygdx.game.Food.Order;
import com.mygdx.game.Money;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.Powerups.Powerup;
import com.mygdx.game.Powerups.Powerups;
import com.mygdx.game.Screens.GameScreen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(GdxTestRunner.class)
public class PowerupsTest {
    private PiazzaPanic game = Mockito.mock(PiazzaPanic.class);
    private Money money;

    @Before
    public void setUp() {
        System.out.println("Setting up class");
        game = Mockito.mock(PiazzaPanic.class);
        money = new Money(game);

    }
    @Test
    public void testBuyPowerup() {
        Powerups powerups = new Powerups(game, money, null);

        // set current money to 200
        money.addMoney(200);

        // buy a powerup with a price of 100
        boolean bought = powerups.buyPowerup("Speed");

        assertTrue(bought);
        assertEquals(100,  money.getCurrentMoney());
    }
    @Test
    public void testBuyPowerupInsufficientFunds() {
        Powerups powerups = new Powerups(game, money, null);

        // set current money to 50
        money.addMoney(50);

        // buy a powerup with a price of 100
        boolean bought = powerups.buyPowerup("Speed");

        assertFalse(bought);
        assertEquals(50, money.getCurrentMoney());
    }

    @Test
    public void testGetSpeedMultiplier() {
        money.addMoney(1000);
        Powerups powerups = new Powerups(game, money, null);

        // check default speed multiplier value
        assertEquals(1, powerups.getSpeedMultiplier(), 0.01f);

        // set new speed multiplier
        powerups.setSpeedMultiplier(2);

        // check new speed multiplier value
        assertEquals(2, powerups.getSpeedMultiplier(), 0.01f);
    }

    @Test
    public void testGetStationSpeed() {
        money.addMoney(3000);
        Powerups powerups = new Powerups(game, money, null);


        // check default station speed value
        assertEquals(1, powerups.getStationSpeed(), 0.01f);
        System.out.println(powerups.getStationSpeed());

        // set new station speed value
        powerups.setStationSpeed(2);
        System.out.println(powerups.getStationSpeed());

        // check new station speed value
        assertEquals(2, powerups.getStationSpeed(), 0.01f);
    }



}
