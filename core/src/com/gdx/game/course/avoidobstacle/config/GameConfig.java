package com.gdx.game.course.avoidobstacle.config;

public class GameConfig {

    public static final float WIDTH = 480f; // pixels, size of desktop window
    public static final float HEIGHT = 800f; // pixels, size of desktop window

    public static final float HUD_WIDTH = 480f; // world units
    public static final float HUD_HEIGHT = 800f; // world units

    public static final float WORLD_WIDTH = 6.0f; // world units
    public static final float WORLD_HEIGHT = 10.0f; // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f; // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f; // world units

    public static final float MAX_PLAYER_X_SPEED = 0.25f; // max player speed
    public static final float OBSTACLE_SPAWN_TIME = 0.50f; // spawn obstacle every interval
    public static final float SCORE_MAX_TIME = 1.25f; // add score every interval

    public static final int LIVES_START = 3; // lives on start

    public static final float EASY_OBSTACLE_SPEED = 0.05f;
    public static final float MEDIUM_OBSTACLE_SPEED = 0.07f;
    public static final float HARD_OBSTACLE_SPEED = 0.09f;

    public static final float PLAYER_BOUNDS_RADIUS = 0.4f; // world units
    public static final float PLAYER_SIZE = 2 * PLAYER_BOUNDS_RADIUS; // world units

    public static final float OBSTACLE_BOUNDS_RADIUS = 0.3f; // world units
    public static final float OBSTACLE_SIZE = 2 * OBSTACLE_BOUNDS_RADIUS; // world units

    private GameConfig() {}
}
