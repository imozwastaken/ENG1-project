package com.mygdx.game.Clickables;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Powerups.Powerups;
import com.mygdx.game.Screens.GameScreen;

public class UnlockBaking {
    ImageButton unlockBakingButton;

    public UnlockBaking(Utils utils, final GameScreen screen) {
        unlockBakingButton = utils.createImageClickable(new Texture("key.png"), 24, 24);
        unlockBakingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (screen.getMoney().getCurrentMoney() >= 100 && !screen.bakingUnlocked()) {
                    screen.getMoney().addMoney(-100);
                    screen.setBakingUnlocked(true);
                } else {
                    System.out.println("Not enough money");
                }
            }
        });
    }

    public ImageButton getUnlockBakingButton() {
        return unlockBakingButton;
    }
}
