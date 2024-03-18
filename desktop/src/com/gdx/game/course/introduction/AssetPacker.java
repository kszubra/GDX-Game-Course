package com.gdx.game.course.introduction;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

    private static final boolean DRAW_DEBUG_OUTLINE = true;
    private static final String RAW_ASSETS_PATH = "desktop/assets-raw";
    private static final String ASSETS_PATH = "core/assets";

    public static void main(String[] args) {
        TexturePacker.Settings setting = new TexturePacker.Settings();
        setting.debug = DRAW_DEBUG_OUTLINE;
        setting.maxHeight = 2048;
        setting.maxWidth = 2048;

                TexturePacker.process(
                setting,
                RAW_ASSETS_PATH + "/images",
                ASSETS_PATH + "/images",
                "atlasSample" //just name
        );
    }

}
