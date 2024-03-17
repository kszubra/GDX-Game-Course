package com.gdx.game.course.avoidobstacle.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.gdx.game.course.avoidobstacle.ObstacleAvoidGame;
import com.gdx.game.course.avoidobstacle.assets.AssetDescriptors;

public class GameScreen implements Screen {
    private final AssetManager assetManager;

    private GameController controller;
    private GameRenderer renderer;

    public GameScreen(ObstacleAvoidGame game) {
        this.assetManager = game.getAssetManager();
    }

    @Override
    public void show() { //like create(), used to initiate game and load resources
        assetManager.load(AssetDescriptors.FONT);
        assetManager.load(AssetDescriptors.BACKGROUND);
        assetManager.load(AssetDescriptors.PLAYER);
        assetManager.load(AssetDescriptors.OBSTACLE);
        assetManager.finishLoading();
        controller = new GameController();
        renderer = new GameRenderer(assetManager, controller);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);
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
        dispose();
    } //when hiding screen, for example changing between screens like game and menu

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
