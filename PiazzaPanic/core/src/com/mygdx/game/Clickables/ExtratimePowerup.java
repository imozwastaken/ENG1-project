package com.mygdx.game.Clickables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.Powerups.Powerups;

public class ExtratimePowerup {
    ImageButton extraTimeClickable;

    public ExtratimePowerup(Utils utils, final GameScreen screen, final Powerups powerups) {
        extraTimeClickable = utils.createImageClickable(new Texture("stopwatchplus.png"), 24, 24);
        extraTimeClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                powerups.buyExtraTime();
            }
        });
    }

    public ImageButton getExtraTimeClickable() {
        return extraTimeClickable;
    }
}
