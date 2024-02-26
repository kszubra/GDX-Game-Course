package com.gdx.game.course.introduction;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.*;
import com.gdx.game.course.introduction.common.SampleBase;
import com.gdx.game.course.introduction.common.SampleInfo;
import com.gdx.game.course.introduction.utils.GdxUtils;

public class ViewportSample extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(ViewportSample.class);
    private static final Logger log = new Logger(ViewportSample.class.getName(), Logger.DEBUG);

    private static final float WORLD_WIDTH = 800.0f; //world units
    private static final float WORLD_HEIGHT = 600.0f; //world units
    private OrthographicCamera camera;
    private Viewport currentViewport;
    private SpriteBatch spriteBatch;
    private Texture texture;
    private BitmapFont font;

    private ArrayMap<String, Viewport> viewports = new ArrayMap<>();

    private int currentViewportIndex;
    private String currentViewportName;


    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        currentViewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        spriteBatch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("introduction/raw/level-bg-small.png"));
        font = new BitmapFont(Gdx.files.internal("introduction/fonts/oswald_black_48.fnt"));

        createViewports();
        selectNextViewport();

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {
        currentViewport.update(width, height, true);
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
        selectNextViewport();
        return true;
    }

    private void selectNextViewport() {
        currentViewportIndex = (currentViewportIndex + 1) % viewports.size;
        currentViewport = viewports.getValueAt(currentViewportIndex);
        currentViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        currentViewportName = viewports.getKeyAt(currentViewportIndex);
        log.debug(String.format("Current viewport: %s", currentViewportName));
    }

    private void createViewports() {
        //stretches image, supports working with virtual screen size (provided world size). Having virtual
        //screen size assumes screen is always same size. Always fills the screen but stretches image. Does not keep the aspect ratio
        viewports.put(StretchViewport.class.getSimpleName(), new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));

        //Supports virtual screen size and keeps aspect ratio, but may leave bars. Most recommended
        viewports.put(FitViewport.class.getSimpleName(), new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));

        //Keeps aspect ratio, always fills the screen so may cut off something
        viewports.put(FillViewport.class.getSimpleName(), new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));

        //Doesn't support virtual size, bars around, matches window size -> no scaling. Not recommended
        viewports.put(ScreenViewport.class.getSimpleName(), new ScreenViewport(camera));

        //Keeps aspect ratio.
        viewports.put(ExtendViewport.class.getSimpleName(), new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera));

        currentViewportIndex = -1;
    }

    private void draw() {
        spriteBatch.draw(texture,
                0, 0, //x,y
                WORLD_WIDTH, WORLD_HEIGHT

        );
        font.draw(spriteBatch, currentViewportName, 50, 100);
    }
}
