package com.mygdx.game.Clickables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Powerups.Powerups;
import com.mygdx.game.Screens.GameScreen;

public class StationSpeedPowerup {

    ImageButton stationSpeedClickable;
    public StationSpeedPowerup(Utils utils, final GameScreen screen, final Powerups powerups ) {
        stationSpeedClickable = utils.createImageClickable(new Texture("stopwatch.png"), 24, 24);
        stationSpeedClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                powerups.setStationSpeed(2);
            }
        });

    }

    public ImageButton getStationClickable() {
        return stationSpeedClickable;
    }

}
