package com.mygdx.game.Clickables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Cook;
import com.mygdx.game.Food.Ingredient;
import com.mygdx.game.Screens.GameScreen;

public class PattyClickable {
    ImageButton pattyClickable;
    public PattyClickable(Utils utils, final GameScreen screen) {
        pattyClickable = utils.createImageClickable(new Texture("rawPatty.png"), 24, 24);
        pattyClickable.addListener(new ClickListener() {
            @Override

            public void clicked(InputEvent event, float x, float y) {
                Array<Cook> cooks = screen.getCooks();
                int selected = screen.getSelected();
                if (cooks.get(selected).CookStack.size() < 5) {
                    cooks.get(selected).CookStack.push(new Ingredient("patty", new Texture("rawPatty.png"), new Texture("prepdPatty.png")));
                }
            }
        });

    }

    public ImageButton getPattyClickable() {
        return pattyClickable;
    }
}
