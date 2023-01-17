package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator implements ApplicationListener {

    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 96, FRAME_ROWS = 2;

    // Objects used
    Animation<TextureRegion> bobIdleRight; // Must declare frame type (TextureRegion)
    Texture sheet;
    SpriteBatch spriteBatch;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    public void create(){
        sheet = new Texture(Gdx.files.internal("charAnimations.png"));
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth() / FRAME_COLS,
                sheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] bobIRFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                bobIRFrames[index++] = tmp[i][j];
            }
        }

        bobIdleRight = new Animation<TextureRegion>(0.025f, bobIRFrames);
        spriteBatch = new SpriteBatch();
        stateTime = 0f;

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
