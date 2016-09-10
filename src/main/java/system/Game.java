package system;

import objects.backgrounds.BackgroundFactory;
import objects.buttons.ButtonFactory;
import resources.Res;
import resources.audio.AudioManager;
import resources.audio.IAudioManager;
import filesystem.FileSystem;
import input.InputManager;
import math.Calc;
import objects.BlockFactory;
import objects.doodles.DoodleFactory;
import objects.enemies.EnemyBuilder;
import objects.platform.PlatformFactory;
import objects.powerups.PowerupFactory;
import rendering.IRenderer;
import rendering.Renderer;
import scenes.SceneFactory;
import resources.sprites.SpriteFactory;

public final class Game {

    private static IServiceLocator serviceLocator = new ServiceLocator();
    public final static int height = 512;
    public final static int width = 320;

    private static void initServices() {
        AudioManager.register(serviceLocator);
        EnemyBuilder.register(serviceLocator);
        FileSystem.register(serviceLocator);
        InputManager.register(serviceLocator);
        Calc.register(serviceLocator);
        BlockFactory.register(width, height, serviceLocator);
        DoodleFactory.register(serviceLocator);
        PowerupFactory.register(serviceLocator);
        SpriteFactory.register(serviceLocator);
        Renderer.register(serviceLocator);
        SceneFactory.register(serviceLocator);
        PlatformFactory.register(serviceLocator);
        Res.register(serviceLocator);
        ButtonFactory.register(serviceLocator);
        BackgroundFactory.register(serviceLocator);
    }

    public static void main(String[] argv) {
        initServices();

        IRenderer renderer = serviceLocator.getRenderer();
        renderer.setScene(serviceLocator.getSceneFactory().getStartMenu());
        renderer.start();
    }

}