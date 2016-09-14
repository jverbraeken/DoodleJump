package resources;

import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

public final class Res implements IRes {

    /**
     * Used to gain access to all services.
     */
    private static transient IServiceLocator serviceLocator;
    private Map<IRes.sprites, String> sprites = new EnumMap<>(IRes.sprites.class);

    {
        String spritepath = "sprites/";
        // TODO this should be removed in the final version when all sprites are ready
        for (IRes.sprites sprite : IRes.sprites.values()) {
            sprites.put(sprite, spritepath + "unimplemented.jpg");
        }
        sprites.put(IRes.sprites.background, spritepath + "bck@2x.png");
        sprites.put(IRes.sprites.platform1, spritepath + "platform-green@2x.png");
        sprites.put(IRes.sprites.playButton, spritepath + "play@2x.png");
        sprites.put(IRes.sprites.resumeButton, spritepath + "resume@2x.png");
        sprites.put(IRes.sprites.pauseCover, spritepath + "pause-cover@2x.png");

        // Top bar

        sprites.put(IRes.sprites.scorebar, spritepath + "scorebar.png");
        sprites.put(IRes.sprites.pause, spritepath + "pause.png");
        sprites.put(IRes.sprites.zero, spritepath + "0.png");
        sprites.put(IRes.sprites.one, spritepath + "1.png");
        sprites.put(IRes.sprites.two, spritepath + "2.png");
        sprites.put(IRes.sprites.three, spritepath + "3.png");
        sprites.put(IRes.sprites.four, spritepath + "4.png");
        sprites.put(IRes.sprites.five, spritepath + "5.png");
        sprites.put(IRes.sprites.six, spritepath + "6.png");
        sprites.put(IRes.sprites.seven, spritepath + "7.png");
        sprites.put(IRes.sprites.eight, spritepath + "8.png");
        sprites.put(IRes.sprites.nine, spritepath + "9.png");

        // Doodle
        sprites.put(IRes.sprites.doodleLeft, spritepath + "blue-lik-left@2x.png");
        sprites.put(IRes.sprites.doodleRight, spritepath + "blue-lik-right@2x.png");
    }

    private Res() {
    }

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Res.serviceLocator = serviceLocator;
        serviceLocator.provide(new Res());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSpritePath(IRes.sprites sprite) {
        return sprites.get(sprite);
    }
}