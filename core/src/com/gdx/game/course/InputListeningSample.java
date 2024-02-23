package com.gdx.game.course;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.course.utils.GdxUtils;

public class InputListeningSample implements ApplicationListener, InputProcessor {

	private static final Logger log = new Logger(InputListeningSample.class.getName(), Logger.DEBUG);

	private static final int MAX_MESSAGE_COUNT = 15;
	private final Array<String> messages = new Array<>();
	private OrthographicCamera camera;
	private Viewport viewport;
	private SpriteBatch batch;
	private BitmapFont font;
	private BitmapFont font2;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		camera = new OrthographicCamera();
		viewport = new FitViewport(1920, 1080, camera);
		batch = new SpriteBatch();
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 24;
		font = new FreeTypeFontGenerator(Gdx.files.internal("fonts/data-unifon.ttf")).generateFont(parameter);
		font2 = new BitmapFont(Gdx.files.internal("fonts/oswald_black_48.fnt"));

//		Gdx.input.setInputProcessor(this);

		InputMultiplexer multiplexer = new InputMultiplexer();
		InputAdapter firstProcessor = new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				log.debug("first_rocessor key_down: " + keycode + " will return false");
				return false; //returning false means event wasn't fully processed and next processor will deal with it
			}

			@Override
			public boolean keyUp(int keycode) {
				log.debug("first_processor key_up: " + keycode + " will return true");
				return true; //returning true means even was processed and won't be delegated to next processor
			}
		};
		InputAdapter secondProcessor = new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				log.debug("second_rocessor key_down: " + keycode + " will return false");
				return false;
			}

			@Override
			public boolean keyUp(int keycode) {
				log.debug("second_processor key_up: " + keycode + " will return true");
				return false;
			}
		};

		multiplexer.addProcessor(firstProcessor);
		multiplexer.addProcessor(secondProcessor);
		multiplexer.addProcessor(this);
		Gdx.input.setInputProcessor(multiplexer);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void render() {
		// clear screen
		GdxUtils.clearScreen(Color.BLACK);

		batch.setProjectionMatrix(camera.combined); // tell batch the position and zoom of camera
		batch.begin();
		draw();
		batch.end();
	}

	private void draw() {
		for(int i = 0; i < messages.size; i++) {
			font2.draw(batch, messages.get(i),
					20.0f,
					720 - 45.0f * (i+1));
		}
	}

	private void addMessage(String message) {
		messages.add(message);
		if(messages.size > MAX_MESSAGE_COUNT) {
			messages.removeIndex(0);
		}
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

	@Override
	public boolean keyDown(int keycode) {
		String message = String.format("key_down, key_code: %s", keycode);
		log.debug(message);
		addMessage(message);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		String message = String.format("key_up, key_code: %s", keycode);
		log.debug(message);
		addMessage(message);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		String message = String.format("key_typed, character: %s", character);
		log.debug(message);
		addMessage(message);
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		String message = String.format("touch_down: screen_x: %s, screen_y: %s, pointer: %s, button: %s",
				screenX, screenY, pointer, button);
		log.debug(message);
		addMessage(message);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		String message = String.format("touch_up: screen_x: %s, screen_y: %s, pointer: %s, button: %s",
				screenX, screenY, pointer, button);
		log.debug(message);
		addMessage(message);
		return true;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		String message = String.format("touch_cancelled: screen_x: %s, screen_y: %s, pointer: %s, button: %s",
				screenX, screenY, pointer, button);
		log.debug(message);
		addMessage(message);
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		String message = String.format("touch_dragged: screen_x: %s, screen_y: %s, pointer: %s", screenX, screenY, pointer);
		log.debug(message);
		addMessage(message);
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		String message = String.format("mouse_moved: screen_x: %s, screen_y: %s", screenX, screenY);
		log.debug(message);
		addMessage(message);
		return true;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		String message = String.format("scrolled: screen_x: %s, screen_y: %s", amountX, amountY);
		log.debug(message);
		addMessage(message);
		return true;
	}
}

