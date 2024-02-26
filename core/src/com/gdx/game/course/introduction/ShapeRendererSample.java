package com.gdx.game.course.introduction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.course.introduction.common.SampleBase;
import com.gdx.game.course.introduction.common.SampleInfo;
import com.gdx.game.course.introduction.utils.GdxUtils;

public class ShapeRendererSample extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(ShapeRendererSample.class);
    private static final Logger log = new Logger(ShapeRendererSample.class.getName(), Logger.DEBUG);

    private static final float WORLD_WIDTH = 40f;
    private static final float WORLD_HEIGHT = 20f;
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private boolean drawGrid = true;
    private boolean drawCircles = true;
    private boolean drawRectangles = true;
    private boolean drawPoints = true;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    } //not centering the camera!!!! Centering moves red axis to bottom left

    @Override
    public void render() {
        GdxUtils.clearScreen(Color.BLACK);

        //rendering
        renderer.setProjectionMatrix(camera.combined);

        if(drawGrid) {
            drawGrid();
        }

        if(drawCircles) {
            drawCircles();
        }

        if(drawRectangles) {
            drawRectangles();
        }

        if(drawPoints) {
            drawPoints();
        }
    }

    private void drawPoints() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.MAGENTA);
        renderer.point(-5, 0, 0);
        renderer.point(5, -3, 0);
        renderer.point(8, 6, 1);
        renderer.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.VIOLET);
        renderer.x(-10, 0, 0.25f);
        renderer.end();
    }

    private void drawRectangles() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.ORANGE);
        renderer.rect(-8, 4, 4,2);
        renderer.rect(-11, 3, 1,5);
        renderer.end();
    }

    private void drawCircles() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        renderer.circle(0, 0, 2, 30); //segments tell how many "walls" circle is made of. With 4 it's gives square
        renderer.circle(-5, -5, 2, 6);
        renderer.end();
    }

    private void drawGrid() {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);

        int worldWidth = (int) WORLD_WIDTH;
        int worldHeight = (int) WORLD_HEIGHT;

        drawGrid(worldWidth, worldHeight);

        renderer.setColor(Color.RED);
        renderer.line(-worldWidth, 0.0f, worldWidth, 0.0f); //horizontal axis
        renderer.line(0.0f, -worldHeight, 0.0f, worldHeight); //vertical axis

        renderer.end();
    }

    private void drawGrid(int worldWidth, int worldHeight) {
        for(int x = -worldWidth; x < worldHeight; x++) { //vertical lines
            renderer.line(x, -worldHeight, x, worldHeight);
        }
        for(int y = -worldHeight; y < worldHeight; y++) {
            renderer.line(-worldWidth, y, worldWidth, y);
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {

        switch(keycode) {
            case Input.Keys.G -> drawGrid = !drawGrid;
            case Input.Keys.C -> drawCircles = !drawCircles;
            case Input.Keys.R -> drawRectangles = !drawRectangles;
            case Input.Keys.P -> drawPoints = !drawPoints;
        }

        return true;
    }
}
