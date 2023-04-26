package com.mygdx.game.Clickables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Cook;
import com.mygdx.game.Food.Ingredient;
import com.mygdx.game.Screens.GameScreen;
import com.badlogic.gdx.utils.Array;


public class CheeseClickable {
    ImageButton cheeseClickable;

    public CheeseClickable(Utils utils, final GameScreen screen) {
        this.cheeseClickable = utils.createImageClickable(new Texture("cheese.png"), 24, 24);
        cheeseClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Array<Cook> cooks = screen.getCooks();
                int selected = screen.getSelected();
                if (cooks.get(selected).CookStack.size() < 5) {
                    cooks.get(selected).CookStack.push(new Ingredient("cheese", new Texture("cheese.png"), new Texture("cheese.png")));
                }
            }
        });
    }
    public ImageButton getCheeseClickable() {
        return cheeseClickable;
    }
}
