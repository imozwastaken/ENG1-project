package com.mygdx.game.Clickables;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.Screens.GameScreen;

public class Baking {
    ImageButton bakingClickable;

    public Baking(PiazzaPanic _game, Utils utils, final GameScreen screen) {
        this.bakingClickable =  utils.createImageClickable(32, 32);
        // function exectutes when you press on the baking station on screen
        // it sets the baking station as the currently selected station - this moves the cook to the baking station

        bakingClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.setSationSelected(2);
            }
        });
    }
    public ImageButton getBakingClickable() {
        return bakingClickable;
    }


}
