package com.mygdx.game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.json.JSONObject;

import com.badlogic.gdx.Gdx;

import static java.nio.file.Files.writeString;
import static java.nio.file.Path.of;

public class ConfigHandler {
    JSONObject config;
    String configPath = Gdx.files.getLocalStoragePath() + "core\\src\\com\\mygdx\\game\\config.json";

    public ConfigHandler() throws IOException {
        System.out.println("Config path: " + configPath);
        if (!Files.exists(Paths.get(configPath))) {
            Files.createFile(Paths.get(configPath));
            Files.write(Paths.get(configPath), "{\"difficulty\":\"Easy\",\"muteMusic\":false,\"customersToServe\":5,\"maxTimeToServe\":10}".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        }
        String content = new String(Files.readAllBytes(Paths.get(configPath)));
        System.out.println("Config: " + content);
        config = new JSONObject(content);
    }
    public ConfigHandler(String path) throws IOException {
        System.out.println(path);
        configPath = path;
        if (!Files.exists(Paths.get(configPath))) {
            Files.createFile(Paths.get(configPath));
            Files.write(Paths.get(configPath), "{\"difficulty\":\"Easy\",\"muteMusic\":false,\"customersToServe\":5,\"maxTimeToServe\":10}".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        }
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
    public String getDifficulty() {
        return config.getString("difficulty");
    }

    public boolean muteMode() {
        return config.getBoolean("muteMusic");
    }

    public void setCustomersToServe(int customersToServe) {
        config.put("customersToServe", customersToServe);
        saveConfig();
    }

    public void setMuteMode(boolean muteMode) {
        config.put("muteMusic", muteMode);
        saveConfig();
    }

    public void setDifficulty(String difficulty) {
        config.put("difficulty", difficulty);
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
