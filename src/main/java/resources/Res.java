package resources;

import resources.audio.IAudioManager;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

public final class Res implements IRes {

    private static transient IServiceLocator serviceLocator;

    public static void register(IServiceLocator serviceLocator) {
        assert serviceLocator != null;
        Res.serviceLocator = serviceLocator;
        serviceLocator.provide(new Res());
    }


    private Map<IRes.sprites, String> sprites = new EnumMap<>(IRes.sprites.class);

    {
        String spritepath = "sprites/";
        // TODO this should be removed in the final version when all sprites are ready
        for (IRes.sprites sprite : IRes.sprites.values()) {
            sprites.put(sprite, spritepath + "unimplemented.jpg");
        }

        // Background
        sprites.put(IRes.sprites.background, spritepath + "bck@2x.png");

        // Buttons
        sprites.put(IRes.sprites.playButton, spritepath + "play@2x.png");

        // Doodle
        sprites.put(IRes.sprites.doodleLeftAscend, spritepath + "blue-lik-left@2x.png");
        sprites.put(IRes.sprites.doodleLeftDescend, spritepath + "blue-lik-left-odskok@2x.png");
        sprites.put(IRes.sprites.doodleRightAscend, spritepath + "blue-lik-right@2x.png");
        sprites.put(IRes.sprites.doodleRightDescend, spritepath + "blue-lik-right-odskok@2x.png");

        // Platforms
        sprites.put(IRes.sprites.platform1, spritepath + "platform-green@2x.png");

        // Powerups
        sprites.put(IRes.sprites.trampoline, spritepath + "powerup-trampoline@2x.png");
        sprites.put(IRes.sprites.trampolineUsed, spritepath + "powerup-trampoline-used@2x.png");
        sprites.put(IRes.sprites.spring, spritepath + "powerup-spring@2x.png");
        sprites.put(IRes.sprites.springUsed, spritepath + "powerup-spring-used@2x.png");
        sprites.put(IRes.sprites.rocket, spritepath + "powerup-rockets@2x.png");
        sprites.put(IRes.sprites.propeller, spritepath + "powerup-propeller@2x.png");
        sprites.put(IRes.sprites.shield, spritepath + "powerup-shield@2x.png");
    }


    private Res() { }


    /** {@inheritDoc} */
    @Override
    public String getSpritePath(IRes.sprites sprite) {
        return sprites.get(sprite);
    }

}