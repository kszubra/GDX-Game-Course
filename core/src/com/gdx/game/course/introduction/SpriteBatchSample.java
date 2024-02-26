package com.gdx.game.course.introduction;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.course.introduction.common.SampleBase;
import com.gdx.game.course.introduction.common.SampleInfo;
import com.gdx.game.course.introduction.utils.GdxUtils;

public class SpriteBatchSample extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(SpriteBatchSample.class);
    private static final Logger log = new Logger(SpriteBatchSample.class.getName(), Logger.DEBUG);

    private static final float WORLD_WIDTH = 10.8f; //world units
    private static final float WORLD_HEIGHT = 7.2f; //world units
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    private Texture texture;
    private BitmapFont font;
    private Color oldColor;
    private int width = 1; //world units
    private int height = 1; //world units

    private float rotation = 0.0f;


    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        spriteBatch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("introduction/raw/character.png"));
        font = new BitmapFont(Gdx.files.internal("introduction/fonts/oswald_black_48.fnt"));

        oldColor = new Color();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        GdxUtils.clearScreen(Color.BLACK);

        //rendering
        spriteBatch.setProjectionMatrix(camera.combined); //tell sprite batch about camera location and zoom levels
        spriteBatch.begin();
        draw();
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
        texture.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    private void draw() {
        spriteBatch.draw(texture,
                0, 0,                           //x,y,
                width / 2f, height / 2f,       //originX, originX -> point around which sprite rotates
                width, height,                        //
                1.0f, 1.0f,                    //scaleX, scaleY
                0.0f,                                 // rotation
                0, 0,                            //srcX, srcY
                texture.getWidth(), texture.getHeight(), //src width, src height
                false, false

        );

        spriteBatch.draw(texture,
                4, 2, // move it
                width / 2f, height / 2f,
                width, height,
                2.0f, 2.0f, // make it twice bigger
                0.0f,
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false

        );

        // save batch color
        oldColor.set(spriteBatch.getColor());

        // set new color for sprite batch
        spriteBatch.setColor(Color.ORANGE);

        spriteBatch.draw(texture,
                8, 1, // move it
                width / 2f, height / 2f,
                width, height,
                2.0f, 2.0f, // make it twice bigger
                0.0f,
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false

        );
        spriteBatch.setColor(oldColor); //back to original so that new frame doesn't use new color for all elements

        //flip character
        spriteBatch.draw(texture,
                5, 4, // move it
                width / 2f, height / 2f,
                width, height,
                2.0f, 2.0f, // make it twice bigger
                0.0f,
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, true

        );

        //try rotating
        rotation += (rotation + 1.0f) % 10;
        spriteBatch.draw(texture,
                7, 5, // move it
                width / 2f, height / 2f,
                width, height,
                2.0f, 2.0f, // make it twice bigger
                rotation,
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, false
        );
    }

}
