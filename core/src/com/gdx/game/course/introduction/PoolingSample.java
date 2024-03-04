package com.gdx.game.course.introduction;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.gdx.game.course.introduction.common.SampleBase;
import com.gdx.game.course.introduction.common.SampleInfo;

public class PoolingSample extends SampleBase {
    public static final SampleInfo SAMPLE_INFO = new SampleInfo(PoolingSample.class);
    private static final Logger log = new Logger(PoolingSample.class.getName(), Logger.DEBUG);
    private static final float BULLET_SPAWN_TIME = 1f;
    private static final float BULLET_ALIVE_TIME = 3f;

    private Array<Bullet> bullets = new Array<Bullet>();

    private float timer;

//    private final Pool<Bullet> bulletPool = Pools.get(Bullet.class, 15); // return reflection Pool
    private final Pool<Bullet> bulletPool = new Pool<Bullet>() {
    @Override
    protected Bullet newObject() {
        log.debug("newObject");
        return new Bullet();
    }

    @Override
    public void free(Bullet object) {
        log.debug("Before free object: " + object + " free: " + getFree());
        super.free(object);
        log.debug("After free object: " + object + " free: " + getFree());
    }

    @Override
    public Bullet obtain() {
        log.debug("Before obtain free: " + getFree());
        Bullet ret = super.obtain();
        log.debug("After obtain free: " + getFree());
        return ret;
    }

    @Override
    protected void reset(Bullet object) {
        log.debug("Resetting object: " + object);
        super.reset(object); //resets most of the fields, alive = false, can't have it alive again
    }
};

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        timer += delta;

        if(timer > BULLET_SPAWN_TIME) {
            timer = 0;
            Bullet bullet = bulletPool.obtain();
            bullets.add(bullet);

            log.debug("Bullets alive: " + bullets.size);
        }

        for (int i = 0; i < bullets.size; i++) {
            Bullet bullet = bullets.get(i);
            bullet.update(delta);

            if(!bullet.alive) {
                bullets.removeIndex(i);
                bulletPool.free(bullet);
                log.debug("After free alive bullets: " + bullets.size);
            }
        }

    }

    @Override
    public void dispose() {
        bulletPool.freeAll(bullets);
        bulletPool.clear();
        bullets.clear();
    }

    public static class Bullet implements Pool.Poolable {
        boolean alive = true;
        float timer;

        public Bullet() {
        }

        public void update(float delta) {
            timer += delta;
            if (alive && timer > BULLET_ALIVE_TIME) {
                alive = false;
            }
        }

        @Override
        public void reset() { //own implementation to keep bullet alive
            alive = true;
            timer = 0;
        }
    }
}
