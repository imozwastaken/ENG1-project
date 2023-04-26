package com.mygdx.game.Clickables;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.PiazzaPanic;
import com.mygdx.game.Food.Ingredient;
import com.mygdx.game.Screens.GameScreen;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Cook;
import com.badlogic.gdx.graphics.Texture;

public class Baking {
    ImageButton bakingClickable;

    public Baking(PiazzaPanic _game, Utils utils, final GameScreen screen) {
        this.bakingClickable = utils.createImageClickable(32, 32);
        // function exectutes when you press on the baking station on screen
        // it sets the baking station as the currently selected station - this moves the
        // cook to the baking station

        bakingClickable.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Baking clicked");
                Array<Cook> cooks = screen.getCooks();
                int selected = screen.getSelected();
                screen.setSationSelected(2);

                Ingredient bakedPizza = new Ingredient("pizza", new Texture("rawPizza.png"),
                        new Texture("prepdPizza.png"));
                bakedPizza.prepare();
                bakedPizza.updateCurrentTexture();
                if ((Math.abs(screen.getCooks().get(screen.getSelected()).CookBody.getY() - 64f) < 2)
                        && (Math.abs(cooks.get(selected).CookBody.getX() - 64f) < 2) && screen.bakingUnlocked()) {
                    if (!(screen.getCooks().get(screen.getSelected()).isBusy)) {
                        // used to limit to preping only one ingredient per press
                        boolean ingredientDone = false;
                        Ingredient selectedIngredient = null;
                        // preps the first vegetable in the current cook's stack after pressing the
                        // station again
                        // while busy creates a progress bar to indicate when the cook can move again
                        for (Ingredient ingredient : screen.getCooks().get(screen.getSelected()).CookStack) {
                            if ((ingredient.name == "pizza") && (!ingredient.getState()) && (!ingredientDone)) {
                                selectedIngredient = ingredient;
                            } else {
                                System.out.println("No pizza to bake");
                            }
                        }
                        if (!(selectedIngredient == null)) {
                            cooks.get(selected).isBusy = true;
                            screen.createProgressBar(80, 86, cooks.get(selected));
                            screen.incrementBakingClicked();
                            // used for the flipping mechanism (the station has to be pressed twice for the
                            // patty to be prepared)
                            if ((screen.getBakingClicked()) % 2 == 0) {
                                ingredientDone = true;
                                cooks.get(selected).CookStack.push(bakedPizza);
                                screen.setPizzaAtBaking(false);
                            } else {
                                cooks.get(selected).CookStack.remove(selectedIngredient);
                                screen.setPizzaAtBaking(true);
                            }
                        } else {
                            if (screen.getPizzaAtBaking()) {
                                cooks.get(selected).isBusy = true;
                                screen.createProgressBar(80, 86, cooks.get(selected));
                                screen.incrementBakingClicked();
                                cooks.get(selected).CookStack.push(bakedPizza);
                                screen.setPizzaAtBaking(false);
                            }
                        }
                    }
                } else {
                    if (screen.bakingUnlocked()) {
                        System.out.println("Cook not at station");
                    } else {
                        System.out.println("Baking station not unlocked");
                    }
                }
            }
        });
    }

    public ImageButton getBakingClickable() {
        return bakingClickable;
    }

}
