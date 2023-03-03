package com.mygdx.game.Clickables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.Screens.GameScreen;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

public class Savegame {

    ImageButton saveClickable;
    GameScreen scren;

    public Savegame(PiazzaPanic _game, Utils utils, final GameScreen _screen) throws IOException {
        this.scren = _screen;
        this.saveClickable = utils.createImageClickable(new Texture("Save.png"),24,24);
        saveClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveState();
            }
        });
    }
    /*
    * Time taken
    * Money
    * Customers served
    * */

    public Boolean saveState() {
        DateFormat dform = new SimpleDateFormat("ddMMyyHH");
        Date obj = new Date();
        String path = String.format("save%s.json",dform.format(obj) );
        JSONObject json = new JSONObject();
        int money = scren.getMoney().getCurrentMoney();
        long timetaken = scren.getGameTime();
        try {
            json.put("test", "testvalue");
            json.put("Money", money);
            json.put("timetaken", timetaken);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(json.toString());
        } catch (Exception e ) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ImageButton getSaveClickable() {return saveClickable;}
}

