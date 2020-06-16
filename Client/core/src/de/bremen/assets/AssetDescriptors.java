package de.bremen.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {



    public static final AssetDescriptor<Skin> SGX_SKIN =
            new AssetDescriptor<>(AssetPaths.SGX_SKIN, Skin.class);

    public static final AssetDescriptor<TextureAtlas> BACKGROUND_AREA =
            new AssetDescriptor<>(AssetPaths.BACKGROUND_AREA, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> BACKGROUND_LOGIN =
            new AssetDescriptor<>(AssetPaths.BACKGROUND_LOGIN, TextureAtlas.class);


    private AssetDescriptors() {
    }
}
