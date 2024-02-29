package com.gdx.game.course.introduction;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.course.introduction.common.SampleBase;
import com.gdx.game.course.introduction.common.SampleInfo;

public class BitmapFontSample extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(BitmapFontSample.class);
    private static final Logger log = new Logger(BitmapFontSample.class.getName(), Logger.DEBUG);

    private static final float WIDTH = 1080f; //world units
    private static final float HEIGHT = 720f; //world units
    private OrthographicCamera camera;
    private Viewport currentViewport;
    private SpriteBatch spriteBatch;
    private BitmapFont font;

    private GlyphLayout glyphLayout = new GlyphLayout();

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        camera = new OrthographicCamera();
        currentViewport = new FitViewport(WIDTH, HEIGHT, camera);
        spriteBatch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("introduction/fonts/oswald_black_48.fnt"));
        font.getData().markupEnabled = true; //enables color marks inside text string
    }

    @Override
    public void resize(int width, int height) {
        currentViewport.update(width, height, true);
    }

    @Override
    public void render() {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        draw();
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
    }

    private void draw() {
        String txt = "AAAAAAAA";
        font.draw(spriteBatch, txt, 0, HEIGHT); //fonts are rendered from top left corner!!!! Textures are from bottom left -> here y is HEIGHT

        String txt2 = "BBBBBB";
        glyphLayout.setText(font, txt2); //glyphLayout will calculate the size knowing font and text
        font.draw(spriteBatch, txt2,
                (WIDTH - glyphLayout.width) / 2f,
                (HEIGHT - glyphLayout.height) / 2f
        );

        String txt3 = "[#FF0000]CCCCCCCCC [BLUE]DDDDDD [PINK]EEE";
        font.draw(spriteBatch, txt3, 50, HEIGHT-100, 100, 0, true); //wrap text to the required width 100
    }
}
