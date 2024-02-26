package com.gdx.game.course.introduction;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.badlogic.gdx.utils.reflect.Method;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.course.introduction.common.SampleBase;
import com.gdx.game.course.introduction.common.SampleInfo;

import java.util.Arrays;

public class GdxReflectionSample extends SampleBase {
	public static final SampleInfo SAMPLE_INFO = new SampleInfo(GdxReflectionSample.class);

	private static final Logger log = new Logger(ApplicationListenerSample.class.getName(), Logger.DEBUG);
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
		font = new FreeTypeFontGenerator(Gdx.files.internal("introduction/fonts/data-unifon.ttf")).generateFont(parameter);
		font2 = new BitmapFont(Gdx.files.internal("introduction/fonts/oswald_black_48.fnt"));

		debugReflection(GdxReflectionSample.class);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void render() {
	}

	private static void debugReflection(Class<?> clazz) {
		Field[] fields = ClassReflection.getDeclaredFields(clazz);
		Method[] methods = ClassReflection.getDeclaredMethods(clazz);

		log.debug("== debug reflection class== " + clazz.getName());
		log.debug("fields count: " + fields.length);
		for(Field field : fields) {
			log.debug("name: " + field.getName() + ", type: " + field.getType());
		}
		log.debug("methods count: " + methods.length);
		for(Method method : methods) {
			log.debug("name: " + method.getName() + ", parameterTypes: " + Arrays.asList(method.getParameterTypes()));
		}
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

