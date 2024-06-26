package com.gdx.game.course.introduction.common;

import com.gdx.game.course.introduction.*;

import java.util.*;

public class SampleInfos {

    public static final List<SampleInfo> ALL_SAMPLES = Arrays.asList(
            ApplicationListenerSample.SAMPLE_INFO,
            GdxGeneratedSample.SAMPLE_INFO,
            GdxReflectionSample.SAMPLE_INFO,
            InputListeningSample.SAMPLE_INFO,
            InputPollingSample.SAMPLE_INFO,
            OrthographicCameraSample.SAMPLE_INFO,
            ViewportSample.SAMPLE_INFO,
            SpriteBatchSample.SAMPLE_INFO,
            ShapeRendererSample.SAMPLE_INFO,
            GifDisplaySample.SAMPLE_INFO,
            BitmapFontSample.SAMPLE_INFO,
            PoolingSample.SAMPLE_INFO,
            AssetManagerSample.SAMPLE_INFO,
            TextureAtlasSample.SAMPLE_INFO,
            CustomActorSample.SAMPLE_INFO,
            ActionsSample.SAMPLE_INFO,
            TableSample.SAMPLE_INFO,
            SkinSample.SAMPLE_INFO,
            AshleyEngineSample.SAMPLE_INFO,
            AshleySystemSample.SAMPLE_INFO
    );

    public static List<String> getSampleNames() {
        List<String> result = new ArrayList<>();

        for (SampleInfo sample : ALL_SAMPLES) {
            result.add(sample.getName());
        }

        Collections.sort(result);
        return result;
    }

    public static SampleInfo find(String name) {
        if(Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("Sample name is required");
        }

        SampleInfo ret = null;

        for(SampleInfo info : ALL_SAMPLES) {
            if(info.getName().equals(name)) {
                ret = info;
                break;
            }
        }

        if(Objects.isNull(ret)) {
            throw new RuntimeException("Cannot find sample info for name: " + name);
        }
        return ret;
    }
}
