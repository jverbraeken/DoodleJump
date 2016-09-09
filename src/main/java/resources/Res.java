package resources;

import resources.audio.IAudioManager;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

public final class Res implements IRes {
    private static transient IServiceLocator serviceLocator;
    public static void register(IServiceLocator serviceLocator_) {
        assert serviceLocator_ != null;
        serviceLocator = serviceLocator_;
        serviceLocator.provide(new Res());
    }

    private Map<IRes.sprites, String> sprites = new EnumMap<>(IRes.sprites.class);

    {
        String spritepath = "sprites/";
        // TODO this should be removed in the final version when all sprites are ready
        for (IRes.sprites sprite : IRes.sprites.values()) {
            sprites.put(sprite, spritepath + "unimplemented.jpg");
        }
        sprites.put(IRes.sprites.doodle, spritepath + "blue-lik-right@2x.png");
        sprites.put(IRes.sprites.platform1, spritepath + "platform-green@2x.png");
    }

    private Res() {
    }

    /** {@inheritDoc} */
    @Override
    public String getSpritePath(IRes.sprites sprite) {
        return sprites.get(sprite);
    }
}