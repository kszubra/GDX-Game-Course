package com.gdx.game.course.introduction;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.course.introduction.common.SampleBase;
import com.gdx.game.course.introduction.common.SampleInfo;
import com.gdx.game.course.introduction.utils.GdxUtils;

public class OrthographicCameraSample extends SampleBase {

    public static final SampleInfo SAMPLE_INFO = new SampleInfo(OrthographicCameraSample.class);
    private static final Logger log = new Logger(OrthographicCameraSample.class.getName(), Logger.DEBUG);

    private static final float WORLD_WIDTH = 10.0f; //world units
    private static final float WORLD_HEIGHT = 7.2f; //world units
    private static final float CAMERA_SPEED = 2.0f; //world units
    private static final float CAMERA_ZOOM_SPEED = 2.0f; //world units
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    private Texture texture;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        spriteBatch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("introduction/raw/level-bg.png"));

        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render() {
        queryInput();

        GdxUtils.clearScreen(Color.BLACK);

        //rendering
        spriteBatch.setProjectionMatrix(camera.combined); //tell sprite batch about camera location and zoom levels
        spriteBatch.begin();
        draw();
        spriteBatch.end();

    }

    private void queryInput() {
        float deltaTime = Gdx.graphics.getDeltaTime(); // time passed between 2 frames
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x -= CAMERA_SPEED * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += CAMERA_SPEED * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y += CAMERA_SPEED * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= CAMERA_SPEED * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_UP)) {
            camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.PAGE_DOWN)) {
            camera.zoom += CAMERA_ZOOM_SPEED * deltaTime;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            log.debug(String.format("Camera position: %s", camera.position));
            log.debug(String.format("Camera zoom: %s", camera.zoom));
        }

        camera.update(); // need to update camera after every change of position or zoom to recalculate everything

    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        log.debug(String.format("Scroll: amountX: %s, amountY: %s", amountX, amountY));
        float deltaTime = Gdx.graphics.getDeltaTime(); // time passed between 2 frames
        if(amountY < 0) { //scroll down
            camera.zoom -= CAMERA_ZOOM_SPEED * deltaTime;
        } else if (amountY > 0){ //scroll up
            camera.zoom += CAMERA_ZOOM_SPEED * deltaTime;
        }
        camera.update();
        return false;

    }

    private void draw() {
        spriteBatch.draw(texture,
                0, 0, //x,y
                WORLD_WIDTH, WORLD_HEIGHT

        );
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        texture.dispose();
    }
}
