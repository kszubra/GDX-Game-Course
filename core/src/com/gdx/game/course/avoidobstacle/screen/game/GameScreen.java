package com.gdx.game.course.avoidobstacle.screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;
import com.gdx.game.course.avoidobstacle.ObstacleAvoidGame;
import com.gdx.game.course.avoidobstacle.assets.AssetDescriptors;
import com.gdx.game.course.avoidobstacle.screen.menu.MenuScreen;

public class GameScreen implements Screen {
    private static final Logger log = new Logger(GameScreen.class.getName(), Logger.DEBUG);
    private final AssetManager assetManager;

    private GameController controller;
    private GameRenderer renderer;

    private final ObstacleAvoidGame game;

    public GameScreen(ObstacleAvoidGame game) {
        this.assetManager = game.getAssetManager();
        this.game = game;
    }

    @Override
    public void show() { //like create(), used to initiate game and load resources
        log.debug("show()");
        controller = new GameController(game);
        renderer = new GameRenderer(game.getBatch(), assetManager, controller);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);

        if (controller.isGameOver()) {
            log.debug("Game is over, going to menu screen");
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        log.debug("hide");
        dispose();
    } //when hiding screen, for example changing between screens like game and menu

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
