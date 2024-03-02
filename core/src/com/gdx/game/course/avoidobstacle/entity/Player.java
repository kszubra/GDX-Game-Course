package com.gdx.game.course.avoidobstacle.entity;

import com.gdx.game.course.avoidobstacle.config.GameConfig;

public class Player extends GameObjectBase {

    public Player() {
        super(GameConfig.PLAYER_BOUNDS_RADIUS);
        setSize(GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE);
    }
    
}
