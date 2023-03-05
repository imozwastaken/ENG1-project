package com.mygdx.game.Clickables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Cook;
import com.mygdx.game.Customer;
import com.mygdx.game.Food.Burger;
import com.mygdx.game.Food.Ingredient;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.Screens.GameScreen;

public class BurgerClickable {
    ImageButton burgerClickable;
    public BurgerClickable(PiazzaPanic _game, Utils utils, final GameScreen screen){
        this.burgerClickable = utils.createImageClickable(new Texture("burger.png"), 24, 24);
        burgerClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Array<Cook> cooks = screen.getCooks();
                int selected = screen.getSelected();
                Array<Customer> customers = screen.getCustomers();
                int customerCount = screen.getCustomerCount();
                Burger recipe = new Burger();
                Ingredient buns = new Ingredient("buns", null, null);
                buns.prepare();
                Ingredient patty = new Ingredient("patty", null, null);
                patty.prepare();
                Ingredient lettuce = new Ingredient("lettuce", null, null);
                lettuce.prepare();
                if (recipe.has(cooks.get(selected).CookStack)) {
                    if (customers.get(customerCount).customerOrder.getName() == "burger") {
                        cooks.get(selected).CookStack.remove(buns);
                        cooks.get(selected).CookStack.remove(patty);
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

    public ImageButton getBurgerClickable() {
        return burgerClickable;
    }
}