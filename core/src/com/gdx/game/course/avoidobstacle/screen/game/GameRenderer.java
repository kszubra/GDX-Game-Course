package com.gdx.game.course.avoidobstacle.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.course.avoidobstacle.assets.AssetDescriptors;
import com.gdx.game.course.avoidobstacle.assets.RegionNames;
import com.gdx.game.course.avoidobstacle.config.GameConfig;
import com.gdx.game.course.avoidobstacle.entity.Obstacle;
import com.gdx.game.course.avoidobstacle.entity.Player;
import com.gdx.game.course.avoidobstacle.util.GdxUtils;
import com.gdx.game.course.avoidobstacle.util.ViewportUtils;
import com.gdx.game.course.avoidobstacle.util.debug.DebugCameraController;
import com.gdx.game.course.introduction.utils.GifDecoder;

public class GameRenderer implements Disposable {
    private final AssetManager assetManager;

    // == attributes ==
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private SpriteBatch batch;
    private BitmapFont font;
    private final GlyphLayout layout = new GlyphLayout();
    private DebugCameraController debugCameraController;
    private final GameController controller;
    private TextureRegion playerTexture;
    private TextureRegion obstacleTexture;
    private TextureRegion backgroundTexture;

    Animation<TextureRegion> animation;
    float elapsed;

    // == constructors ==
    public GameRenderer(AssetManager assetManager, GameController controller) {
        this.controller = controller;
        this.assetManager = assetManager;
        init();
    }

    // == init ==
    private void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);
        batch = new SpriteBatch();
        font = assetManager.get(AssetDescriptors.FONT);

        // create debug camera controller
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);
        TextureAtlas gameAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);

        playerTexture = gameAtlas.findRegion(RegionNames.PLAYER);
        obstacleTexture = gameAtlas.findRegion(RegionNames.OBSTACLE);
        backgroundTexture = gameAtlas.findRegion(RegionNames.BACKGROUND);;
        animation = (GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("avoidobstacle/gameplay/blaidd.gif").read()));
    }

    // == public methods ==
    public void render(float delta) {
        // not wrapping inside alive cuz we want to be able to control camera even when there is game over
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        if(Gdx.input.isTouched() && !controller.isGameOver()) {
            Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            Vector2 worldTouch = viewport.unproject(new Vector2(screenTouch));

            System.out.println("screenTouch= " + screenTouch);
            System.out.println("worldTouch= " + worldTouch);

            Player player = controller.getPlayer();
            worldTouch.x = MathUtils.clamp(worldTouch.x, 0, GameConfig.WORLD_WIDTH - player.getWidth());
            player.setX(worldTouch.x);
        }

        // clear screen
        GdxUtils.clearScreen();

        renderGamePlay();

        // render ui/hud
        renderUi();

        // render debug graphics
        renderDebug();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        batch.dispose();
    }

    // == private methods ==
    private void renderGamePlay() {
        viewport.apply(); //whenever we have multiple viewports with different sizes we need to call
        // apply() before drawing so that openGL viewport is set to out viewport
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // draw background
        elapsed += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsed), 0f, 0f, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
//        Background background = controller.getBackground();
//        batch.draw(backgroundTexture,
//                background.getX(), background.getY(),
//                background.getWidth(), background.getHeight()
//        );

        // draw player
        Player player = controller.getPlayer();
        batch.draw(playerTexture,
                player.getX(), player.getY(),
                player.getWidth(), player.getHeight()
        );

        // draw obstacles
        for (Obstacle obstacle : controller.getObstacles()) {
            batch.draw(obstacleTexture,
                    obstacle.getX(), obstacle.getY(),
                    obstacle.getWidth(), obstacle.getHeight()
            );
        }

        batch.end();
    }

    private void renderUi() {
        hudViewport.apply();
        batch.setProjectionMatrix(hudCamera.combined);
        batch.begin();

        String livesText = "LIVES: " + controller.getLives();
        layout.setText(font, livesText);

        font.draw(batch, livesText,
                20,
                GameConfig.HUD_HEIGHT - layout.height
        );

        String scoreText = "SCORE: " + controller.getDisplayScore();
        layout.setText(font, scoreText);

        font.draw(batch, scoreText,
                GameConfig.HUD_WIDTH - layout.width - 20,
                GameConfig.HUD_HEIGHT - layout.height
        );

        batch.end();
    }

    private void renderDebug() {
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();

        ViewportUtils.drawGrid(viewport, renderer);
    }

    private void drawDebug() {
        Player player = controller.getPlayer();
        player.drawDebug(renderer);

        Array<Obstacle> obstacles = controller.getObstacles();

        for (Obstacle obstacle : obstacles) {
            obstacle.drawDebug(renderer);
        }
    }
}
