package com.mygdx.game.Clickables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Cook;
import com.mygdx.game.Food.Ingredient;
import com.mygdx.game.Screens.GameScreen;

public class BunClickable {
    ImageButton bunClickable;
    public BunClickable(Utils utils, final GameScreen screen) {
        bunClickable = utils.createImageClickable(new Texture("buns.png"), 24, 24);
        bunClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Array<Cook> cooks = screen.getCooks();
                int selected = screen.getSelected();
                if (cooks.get(selected).CookStack.size() < 5) {
                    Ingredient buns = new Ingredient("buns", new Texture("buns.png"), new Texture("buns.png"));
                    buns.prepare();
                    cooks.get(selected).CookStack.push(buns);
                }
            }
        });
    }

    public ImageButton getBunClickable() {
        return bunClickable;
    }
}
