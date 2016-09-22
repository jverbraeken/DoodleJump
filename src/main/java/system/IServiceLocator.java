package system;

import logging.ILoggerFactory;
import objects.ICollisions;
import buttons.IButtonFactory;
import resources.IRes;
import resources.audio.IAudioManager;
import objects.enemies.IEnemyBuilder;
import filesystem.IFileSystem;
import input.IInputManager;
import math.ICalc;
import objects.blocks.IBlockFactory;
import objects.doodles.IDoodleFactory;
import objects.blocks.platform.IPlatformFactory;
import objects.powerups.IPowerupFactory;
import rendering.IRenderer;
import scenes.ISceneFactory;
import resources.sprites.ISpriteFactory;

public interface IServiceLocator {

    void provide(IAudioManager audioManager);
    void provide(IEnemyBuilder enemyBuilder);
    void provide(IFileSystem fileSystem);
    void provide(IInputManager inputManager);
    void provide(ICalc calc);
    void provide(IBlockFactory blockFactory);
    void provide(IDoodleFactory doodleFactory);
    void provide(IPowerupFactory powerupFactory);
    void provide(IRenderer renderer);
    void provide(ISpriteFactory spriteFactory);
    void provide(ISceneFactory sceneFactory);
    void provide(IPlatformFactory platformFactory);
    void provide(IRes res);
    void provide(IButtonFactory buttonFactory);
    void provide(ICollisions collisions);
    void provide(ILoggerFactory LoggerFactory);

    IAudioManager getAudioManager();
    IEnemyBuilder getEnemyBuilder();
    IFileSystem getFileSystem();
    IInputManager getInputManager();
    ICalc getCalc();
    IBlockFactory getBlockFactory();
    IDoodleFactory getDoodleFactory();
    IPowerupFactory getPowerupFactory();
    IRenderer getRenderer();
    ISpriteFactory getSpriteFactory();
    ISceneFactory getSceneFactory();
    IPlatformFactory getPlatformFactory();
    IRes getRes();
    IButtonFactory getButtonFactory();
    ICollisions getCollisions();
    ILoggerFactory getLoggerFactory();

}
