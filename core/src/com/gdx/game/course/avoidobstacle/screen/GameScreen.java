package com.gdx.game.course.avoidobstacle.screen;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {

    private GameController controller;
    private GameRenderer renderer;

    @Override
    public void show() { //like create(), used to initiate game and load resources
        controller = new GameController();
        renderer = new GameRenderer(controller);
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
