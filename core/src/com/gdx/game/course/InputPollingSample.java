package com.gdx.game.course;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InputPollingSample implements ApplicationListener {

	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;
	private BitmapFont font;
	private BitmapFont font2;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		camera = new OrthographicCamera();
		viewport = new FitViewport(1080, 720, camera);
		batch = new SpriteBatch();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 24;
		font = new FreeTypeFontGenerator(Gdx.files.internal("fonts/data-unifon.ttf")).generateFont(parameter);
		font2 = new BitmapFont(Gdx.files.internal("fonts/oswald_black_48.fnt"));
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void render() {
		// clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined); // tell batch the position and zoom of camera
		batch.begin();
		draw();
		batch.end();
	}

	private void draw() {
		//mouse / touch xy
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();


		//mouse buttons
		boolean leftPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
		boolean rightPressed = Gdx.input.isButtonPressed(Input.Buttons.RIGHT);

		font.draw(batch,
				"mouse x: " + mouseX + " y: " + mouseY,
				20f,
				720-20f);

		font.draw(batch,
				leftPressed ? "left button pressed" : "left button not pressed",
				20f,
				720-50f);

		font.draw(batch,
				rightPressed ? " right button pressed" : "right button not pressed",
				20f,
				720-80f);

		//keys
		boolean wPressed = Gdx.input.isKeyPressed(Input.Keys.W);
		boolean sPressed = Gdx.input.isKeyPressed(Input.Keys.S);

		font2.draw(batch,
				wPressed ? "w pressed" : "w  not pressed",
				20f,
				720-120f);

		font2.draw(batch,
				sPressed ? "s pressed" : "s not pressed",
				20f,
				720-160f);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();

	}
}

