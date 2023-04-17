package com.mygdx.game.Clickables;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Cook;
import com.mygdx.game.Food.Ingredient;
import com.mygdx.game.Screens.GameScreen;

import java.awt.*;

public class LettuceClickable {
    ImageButton lettuceClickable;

    public LettuceClickable(Utils utils, final GameScreen screen) {
        lettuceClickable = utils.createImageClickable(new Texture("lettuce.png"), 24, 24);
        lettuceClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Array<Cook> cooks = screen.getCooks();
                int selected = screen.getSelected();
                if (cooks.get(selected).CookStack.size() < 5) {
                    cooks.get(selected).CookStack.push(new Ingredient("lettuce", new Texture("lettuce.png"), new Texture("prepdLettuce.png")));
                }
            }
        });
    }

    public ImageButton getLettuceClickable() {
        return lettuceClickable;
    }
}
