package com.mygdx.game.Clickables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Cook;
import com.mygdx.game.Food.Ingredient;
import com.mygdx.game.Screens.GameScreen;

public class PizzaClickable {
    ImageButton pizzaClickable;

    public PizzaClickable(Utils utils, final GameScreen screen) {
        this.pizzaClickable = utils.createImageClickable(new Texture("rawPizza.png"), 24, 24);
        pizzaClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (screen.bakingUnlocked()) {
                    Array<Cook> cooks = screen.getCooks();
                    int selected = screen.getSelected();
                    if (cooks.get(selected).CookStack.size() < 5) {
                        cooks.get(selected).CookStack
                                .push(new Ingredient("pizza", new Texture("rawPizza.png"),
                                        new Texture("prepdPizza.png"), new Texture("burntPizza.png")));
                    }
                }

            }
        });
    }

    public ImageButton getPizzaClickable() {
        return pizzaClickable;
    }

}
