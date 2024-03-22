package com.gdx.game.course.introduction.ashley.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.course.introduction.ashley.component.PositionComponent;
import com.gdx.game.course.introduction.ashley.component.SizeComponent;
import com.gdx.game.course.introduction.ashley.component.TextureComponent;

public class RenderSystem extends EntitySystem {

    private static final Logger log = new Logger(RenderSystem.class.getName(), Logger.DEBUG);

    //System will work on entities having all those components
    private static final Family FAMILY = Family.all(
            PositionComponent.class,
            SizeComponent.class,
            TextureComponent.class
    ).get();

    //component mappers allow fat retrieving objects for given component
    private static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);

    private static final ComponentMapper<SizeComponent> SIZE =
            ComponentMapper.getFor(SizeComponent.class);

    private static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);

    private Viewport viewport;
    private SpriteBatch batch;

    private ImmutableArray<Entity> entities;

    public RenderSystem(Viewport viewport, SpriteBatch batch) {
        this.viewport = viewport;
        this.batch = batch;
    }

    @Override
    public void addedToEngine(Engine engine) {
        log.debug("added to engine");
        entities = engine.getEntitiesFor(FAMILY);
        log.debug("family entities= " + entities.size());
        log.debug("all entities= " + engine.getEntities().size());
    }

    @Override
    public void update(float deltaTime) {
        // called every frame
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        draw();

        batch.end();
    }

    private void draw() {
        for (Entity entity : entities) {
            PositionComponent position = POSITION.get(entity);
            SizeComponent size = SIZE.get(entity);
            TextureComponent texture = TEXTURE.get(entity);

            batch.draw(texture.texture,
                    position.x, position.y,
                    size.width, size.height
            );
        }
    }
}
