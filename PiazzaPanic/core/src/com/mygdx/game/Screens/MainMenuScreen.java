package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.PiazzaPanic;


public class MainMenuScreen implements Screen {
    PiazzaPanic game;
    Texture logo;
    Texture playBtnTex;
    Texture playBtnTexHover;
    Texture exitBtnTex;
    Texture exitBtnTexHover;
    Texture infoBtnTex;
    Texture infoBtnTexHover;
    Texture endlessBtnTex;
    FitViewport view;
    Stage gameStage;

    // image buttons for when not hovering on the button
    TextureRegion playBtnRegion;
    TextureRegionDrawable playBtnDrawable;
    ImageButton playBtn;

    TextureRegion exitBtnRegion;
    TextureRegionDrawable exitBtnDrawable;
    ImageButton exitBtn;

    TextureRegion infoBtnRegion;
    TextureRegionDrawable infoBtnDrawable;
    ImageButton infoBtn;

    TextureRegion endlessBtnRegion;
    TextureRegionDrawable endlessBtnDrawable;
    ImageButton endlessBtn;

    // image buttons for when hovering on the button
    TextureRegion playBtnRegionHover;
    TextureRegionDrawable playBtnDrawableHover;
    ImageButton playBtnHover;

    TextureRegion exitBtnRegionHover;
    TextureRegionDrawable exitBtnDrawableHover;
    ImageButton exitBtnHover;

    TextureRegion infoBtnRegionHover;
    TextureRegionDrawable infoBtnDrawableHover;
    ImageButton infoBtnHover;

    public MainMenuScreen(PiazzaPanic game) {
        this.game = game;

    }

    @Override
    public void show() {
        logo = new Texture("Piazza_Panic_Logo.png");

        // textures for normal buttons
        playBtnTex = new Texture("playBtn.png");
        exitBtnTex = new Texture("exitBtn.png");
        infoBtnTex = new Texture("infoBtn.png");
        endlessBtnTex = new Texture("endless.png");
        // textures for hovered buttons
        infoBtnTexHover = new Texture("infoBtn2.png");
        playBtnTexHover = new Texture("playBtn2.png");
        exitBtnTexHover = new Texture("exitBtn2.png");

        view = new FitViewport(game.GAME_WIDTH, game.GAME_HEIGHT);
        view.getCamera().position.set(game.GAME_WIDTH / 2, game.GAME_HEIGHT / 2, 1f);
        gameStage = new Stage(view, game.batch);

        // normal buttons
        playBtnRegion = new TextureRegion(playBtnTex);
        playBtnDrawable = new TextureRegionDrawable(playBtnRegion);
        playBtn = new ImageButton(playBtnDrawable);

        exitBtnRegion = new TextureRegion(exitBtnTex);
        exitBtnDrawable = new TextureRegionDrawable(exitBtnRegion);
        exitBtn = new ImageButton(exitBtnDrawable);

        infoBtnRegion = new TextureRegion(infoBtnTex);
        infoBtnDrawable = new TextureRegionDrawable(infoBtnRegion);
        infoBtn = new ImageButton(infoBtnDrawable);

        endlessBtnRegion = new TextureRegion(endlessBtnTex);
        endlessBtnDrawable = new TextureRegionDrawable(endlessBtnRegion);
        endlessBtn = new ImageButton(endlessBtnDrawable);

        // hovered buttons
        playBtnRegionHover = new TextureRegion(playBtnTexHover);
        playBtnDrawableHover = new TextureRegionDrawable(playBtnRegionHover);

        exitBtnRegionHover = new TextureRegion(exitBtnTexHover);
        exitBtnDrawableHover = new TextureRegionDrawable(exitBtnRegionHover);

        infoBtnRegionHover = new TextureRegion(infoBtnTexHover);
        infoBtnDrawableHover = new TextureRegionDrawable(infoBtnRegionHover);

        // listeners for hovering on the buttons
        playBtn.addListener(new ClickListener() {
            final ImageButton playNormal = new ImageButton(playBtnDrawable);
            final ImageButton playHover = new ImageButton(playBtnDrawableHover);

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                playBtn.setStyle(playHover.getStyle());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                playBtn.setStyle(playNormal.getStyle());
            }
        });

        exitBtn.addListener(new ClickListener() {
            final ImageButton exitNormal = new ImageButton(exitBtnDrawable);
            final ImageButton exitHover = new ImageButton(exitBtnDrawableHover);

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitBtn.setStyle(exitHover.getStyle());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                exitBtn.setStyle(exitNormal.getStyle());
            }
        });

        endlessBtn.addListener(new ClickListener() {

        });

        infoBtn.addListener(new ClickListener() {
            final ImageButton infoNormal = new ImageButton(infoBtnDrawable);
            final ImageButton infoHover = new ImageButton(infoBtnDrawableHover);

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                infoBtn.setStyle(infoHover.getStyle());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                infoBtn.setStyle(infoNormal.getStyle());
            }
        });
    }

    @Override
    public void render(float delta) {
        gameStage.act();

        ScreenUtils.clear(1, 1, 1, 1);
        view.apply();

        game.batch.setProjectionMatrix(view.getCamera().combined);
        game.batch.begin();
        game.batch.draw(logo, ((game.GAME_WIDTH / 2) - (logo.getWidth() / 2)), 250);
        game.batch.end();

        gameStage.getViewport().apply();

        gameStage.addActor(playBtn);
        playBtn.setPosition((((game.GAME_WIDTH / 2) - (playBtn.getWidth() / 2)) - endlessBtn.getWidth() / 2) - 25, 125);
        gameStage.addActor(endlessBtn);
        endlessBtn.setPosition((((game.GAME_WIDTH / 2) - (playBtn.getWidth() / 2)) + endlessBtn.getWidth() / 2) + 25, 125);
        gameStage.addActor(exitBtn);
        exitBtn.setPosition((game.GAME_WIDTH / 2) - (exitBtn.getWidth() / 2), 35);

        gameStage.addActor(infoBtn);
        infoBtn.setPosition(game.GAME_WIDTH - infoBtn.getWidth(), game.GAME_HEIGHT - infoBtn.getHeight());

        if (playBtn.isPressed()) {
            game.setScreen(new GameScreen(game, view, false));
        }

        if (exitBtn.isPressed()) {
            dispose();
            Gdx.app.exit();
        }

        if (infoBtn.isPressed()) {
            game.setScreen(new CreditsScreen(game));
        }
        if (endlessBtn.isPressed()) {
            game.setScreen(new GameScreen(game, view, true));
        }

        Gdx.input.setInputProcessor(gameStage);
        gameStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameStage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        logo.dispose();
        gameStage.dispose();
    }

}
