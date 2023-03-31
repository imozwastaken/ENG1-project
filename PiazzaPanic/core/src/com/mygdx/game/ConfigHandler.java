package com.mygdx.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;

public class ConfigHandler {
    JSONObject config;
    String configPath = Gdx.files.getLocalStoragePath() + "core\\src\\com\\mygdx\\game\\config.json";

    public ConfigHandler() throws IOException {
        System.out.println("Config path: " + configPath);
        String content = new String(Files.readAllBytes(Paths.get(configPath)));
        System.out.println("Config: " + content);
        config = new JSONObject(content);
    }

    public JSONObject getConfig() {
        return config;
    }

    public int getCustomersToServe() {
        return config.getInt("customersToServe");
    }

    public boolean muteMode() {
        return config.getBoolean("muteMode");
    }

    public void setCustomersToServe(int customersToServe) {
        config.put("customersToServe", customersToServe);
        saveConfig();
    }

    public void setMuteMode(boolean muteMode) {
        config.put("muteMusic", muteMode);
        saveConfig();
    }

    private void saveConfig() {
        try {
            Files.write(Paths.get(configPath), config.toString().getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error saving config: " + e.getMessage());
        }
    }

}
