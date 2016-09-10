package system;

import resources.Res;
import resources.audio.AudioManager;
import resources.audio.IAudioManager;
import filesystem.FileSystem;
import input.InputManager;
import math.Calc;
import objects.blocks.BlockFactory;
import objects.doodles.DoodleFactory;
import objects.enemies.EnemyBuilder;
import objects.blocks.platform.PlatformFactory;
import objects.powerups.PowerupFactory;
import rendering.IRenderer;
import rendering.Renderer;
import scenes.SceneFactory;
import resources.sprites.SpriteFactory;

public final class Game {

    private static IServiceLocator serviceLocator = new ServiceLocator();
    public static final int HEIGHT = 800;
    public static final int WIDTH = 500;

    private static IRenderer renderer;
    private static IAudioManager audioManager;

    private static void initServices() {
        AudioManager.register(serviceLocator);
        EnemyBuilder.register(serviceLocator);
        FileSystem.register(serviceLocator);
        InputManager.register(serviceLocator);
        Calc.register(serviceLocator);
        BlockFactory.register(WIDTH, HEIGHT, serviceLocator);
        DoodleFactory.register(serviceLocator);
        PowerupFactory.register(serviceLocator);
        SpriteFactory.register(serviceLocator);
        Renderer.register(serviceLocator);
        SceneFactory.register(serviceLocator);
        PlatformFactory.register(serviceLocator);
        Res.register(serviceLocator);
    }

    /**
     * Prevents the creation of a new {@code Game} object.
     */
    private Game() {

    }

    public static void main(String[] argv) {
        initServices();

        renderer = serviceLocator.getRenderer();
        renderer.setScene(serviceLocator.getSceneFactory().getNewWorld());

        audioManager = serviceLocator.getAudioManager();
        audioManager.preload();
        audioManager.playStart();
        renderer.start();
    }
}
