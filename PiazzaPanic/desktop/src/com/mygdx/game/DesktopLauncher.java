package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.Screens.MainMenuNew;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		if (StartOnFirstThreadHelper.startNewJvmIfRequired()) return;
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setResizable(true);
		config.setWindowedMode(1280, 720);
		config.setTitle("Piazza Panic");
		new Lwjgl3Application(new PiazzaPanic(), config);
	}
}
