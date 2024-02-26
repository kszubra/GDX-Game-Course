package com.gdx.game.course.introduction.common;

public class SampleInfo {

    private final String name;
    private final Class<? extends SampleBase> clazz;

    public SampleInfo(Class<? extends SampleBase> clazz) {
        this.name = clazz.getSimpleName();
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
