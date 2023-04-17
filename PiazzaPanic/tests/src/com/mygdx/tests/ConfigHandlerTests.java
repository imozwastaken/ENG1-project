package com.mygdx.tests;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.ConfigHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(GdxTestRunner.class)
public class ConfigHandlerTests {
    private ConfigHandler configHandler;

    @Before
    public void setUp() throws IOException {
        System.out.println("Setting up");
        configHandler = new ConfigHandler(Gdx.files.getLocalStoragePath() + "..\\core\\src\\com\\mygdx\\game\\config.json");
    }

    @After
    public void tearDown() {
        configHandler.setMuteMode(false);
        configHandler.setCustomersToServe(5);
    }

    @Test
    public void testSetMuteMode() {
        configHandler.setMuteMode(true);
        assertEquals(true, configHandler.muteMode());
        System.out.println("Mute mode successfully tested");
    }

    @Test
    public void testSetCustomers() {
        configHandler.setCustomersToServe(1337);
        assertEquals(1337, configHandler.getCustomersToServe());
        System.out.println("Customers served set successfully");
    }

    @Test
    public void testBoth() {
        configHandler.setMuteMode(true);
        configHandler.setCustomersToServe(1337);
        assertEquals(1337, configHandler.getCustomersToServe());
        assertEquals(true, configHandler.muteMode());

        System.out.println("Both set successfully");
    }

    @Test
    public void testInitialValues() {
        assertEquals(false, configHandler.muteMode());
        assertEquals(5, configHandler.getCustomersToServe());
        System.out.println("Both initial values are correct");
    }
}
