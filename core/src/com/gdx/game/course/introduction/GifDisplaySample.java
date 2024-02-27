package com.gdx.game.course.introduction;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.course.introduction.common.SampleBase;
import com.gdx.game.course.introduction.common.SampleInfo;
import com.gdx.game.course.introduction.utils.GdxUtils;
import com.gdx.game.course.introduction.utils.GifDecoder;

public class GifDisplaySample extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(GifDisplaySample.class);
    private static final float WORLD_WIDTH = 10.4f; //world units
    private static final float WORLD_HEIGHT = 12f; //world units
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch spriteBatch;
    Animation<TextureRegion> animation;
    float elapsed;


    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        spriteBatch = new SpriteBatch();
        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("introduction/raw/pindeath.gif").read());

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        GdxUtils.clearScreen(Color.BLACK);
        elapsed += Gdx.graphics.getDeltaTime();

        //rendering
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        draw();
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
    }

    private void draw() {
        spriteBatch.draw(animation.getKeyFrame(elapsed), 0f, 0f, WORLD_WIDTH, WORLD_HEIGHT);
    }
}
