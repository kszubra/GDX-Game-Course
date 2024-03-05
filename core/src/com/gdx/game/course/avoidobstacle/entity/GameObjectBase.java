package com.gdx.game.course.avoidobstacle.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public abstract class GameObjectBase {
    private float x;
    private float y;
    private float width = 1;
    private float height = 1;

    private Circle bounds; //area object takes for collision detection

    public GameObjectBase(float boundsRadius) {
        bounds = new Circle(x, y, boundsRadius);
    }

    public void drawDebug(ShapeRenderer renderer) {
        renderer.x(bounds.x, bounds.y, 0.1f);
        renderer.circle(bounds.x, bounds.y, bounds.radius, 30);
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void updateBounds() {
        float halfWidth = width / 2f;
        float halfHeight = height / 2f;
        bounds.setPosition(x + halfWidth, y + halfHeight); //updating bounds to stay aligned with textures.
        // shape renderer draws circle at 00 meaning middle at 00, texture at 00 means bottom left corner at 00 -> moving bounds
        // up and right to have bottom left corner at 00 too
    }

    public Circle getBounds() {
        return bounds;
    }
}
