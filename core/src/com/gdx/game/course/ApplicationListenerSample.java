package com.gdx.game.course;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import com.gdx.game.course.common.SampleBase;
import com.gdx.game.course.common.SampleInfo;

public class ApplicationListenerSample extends SampleBase {
	private static final Logger log = new Logger(ApplicationListenerSample.class.getName(), Logger.DEBUG);
	private boolean renderInterrupted = true;
	public static final SampleInfo SAMPLE_INFO = new SampleInfo(ApplicationListenerSample.class);

	@Override
	public void create() {
		//initiate game and load resources
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		log.debug("create()");
	}

	@Override
	public void resize(int width, int height) {
		//handles setting new screen size
		log.debug(String.format("resize(): width: %s, height: %s", width, height));
	}

	@Override
	public void render() {
		//updates and renders game element, called 60 times /s
		if(renderInterrupted) {
			log.debug("render()");
			renderInterrupted = false;
		}
	}

	@Override
	public void pause() {
		//saves game state when it loses focus. Doesn't mean pausing gameplay
		log.debug("pause()");
		renderInterrupted = true;
	}

	@Override
	public void resume() {
		//restores game from paused
		log.debug("resume()");
		renderInterrupted = true;
	}

	@Override
	public void dispose() {
		//frees resources and cleans up
		log.debug("dispose()");
	}
}
