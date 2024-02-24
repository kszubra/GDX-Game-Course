package com.gdx.game.course.common;

import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.Objects;

public class SampleFactory {

    public static SampleBase newSample(String name) {
        if(Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("Sample name is required");
        }

        SampleInfo info = SampleInfos.find(name);

        try {
            return (SampleBase) ClassReflection.newInstance(info.getClazz());
        } catch (Exception e) {
            throw new RuntimeException("Can not create sample with name: " + name);
        }

    }

    private SampleFactory() {
    }
}
