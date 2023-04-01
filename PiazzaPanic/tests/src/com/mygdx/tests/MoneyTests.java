package com.mygdx.tests;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.Money;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
@RunWith(GdxTestRunner.class)
public class MoneyTests {
    private PiazzaPanic game = Mockito.mock(PiazzaPanic.class);
    private Money money;

    @Before
    public void setUp() {
        System.out.println("Setting up class");
        game = Mockito.mock(PiazzaPanic.class);
        money = new Money(game);
    }

    @After
    public void tearDown() {
    }
    @Test
    public void testAddMoney() {
        money.addMoney(100);
        assertEquals(100, money.getCurrentMoney());
        System.out.println("Adding money test success");
    }

    @Test
    public void testRemoveMoney() {
        money.addMoney(200);
        money.removeMoney(50);
        assertEquals(150, money.getCurrentMoney());
        System.out.println("Remove money test success");
    }

    @Test
    public void testBoth() {
        money.addMoney(300);
        money.removeMoney(100);
        money.addMoney(50);
        assertEquals(250, money.getCurrentMoney());
        System.out.println("Removing and adding test success");
    }

    @Test
    public void testInitialMoney() {
        assertEquals(0, money.getCurrentMoney());
        System.out.println("Money initialised correctly");
    }


}
