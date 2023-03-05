package com.mygdx.game.Clickables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Cook;
import com.mygdx.game.Customer;
import com.mygdx.game.Food.Ingredient;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.Screens.GameScreen;

public class SaladClickable {
    ImageButton saladClickable;
    public SaladClickable(PiazzaPanic _game, Utils utils, final GameScreen screen) {
        this.saladClickable = utils.createImageClickable(new Texture("salad.png"),24,24);
        saladClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Array<Cook> cooks = screen.getCooks();
                Array<Customer> customers = screen.getCustomers();
                int selected = screen.getSelected();
                int customerCount = screen.getCustomerCount();
                com.mygdx.game.Food.Salad recipe = new com.mygdx.game.Food.Salad();
                Ingredient tomato = new Ingredient("tomato", null, null);
                tomato.prepare();
                Ingredient lettuce = new Ingredient("lettuce", null, null);
                lettuce.prepare();
                if (recipe.has(cooks.get(selected).CookStack)) {
                    if (customers.get(customerCount).customerOrder.getName() == "salad") {
                        cooks.get(selected).CookStack.remove(tomato);
                        cooks.get(selected).CookStack.remove(lettuce);
                        customers.get(customerCount).orderComplete = true;
                        screen.getMoney().addMoney(100);

                        screen.hideServingScreen();
                        cooks.get(selected).isBusy = false;
                    }
                } else {
                    // some or all ingredients are not in the current cook's stack
                }
            }
        });

    }

    public ImageButton getSaladClickable() {
        return saladClickable;
    }
}