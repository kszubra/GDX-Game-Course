package com.gdx.game.course.avoidobstacle;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.game.course.avoidobstacle.config.GameConfig;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) GameConfig.WIDTH;
        config.height = (int) GameConfig.HEIGHT;

        new LwjglApplication(new ObstacleAvoidGame(), config);
    }
}
