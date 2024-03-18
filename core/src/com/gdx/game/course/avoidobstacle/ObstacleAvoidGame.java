package com.gdx.game.course.avoidobstacle;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;
import com.gdx.game.course.avoidobstacle.screen.game.GameScreen;
import com.gdx.game.course.avoidobstacle.screen.loading.LoadingScreen;

public class ObstacleAvoidGame extends Game {

    private AssetManager assetManager;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        assetManager = new AssetManager(); //making asset manager static can cause bugs and momory leaks
        assetManager.getLogger().setLevel(Logger.DEBUG);

        setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }
}
