package com.gdx.game.course.avoidobstacle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.gdx.game.course.avoidobstacle.screen.GameScreen;

public class ObstacleAvoidGame extends Game {

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        setScreen(new GameScreen());
    }
}
