package system;

import buttons.IButtonFactory;
import constants.IConstants;
import filesystem.IFileSystem;
import input.IInputManager;
import logging.ILoggerFactory;
import math.ICalc;
import objects.blocks.IBlockFactory;
import objects.blocks.platform.IPlatformFactory;
import objects.doodles.IDoodleFactory;
import objects.doodles.projectiles.IProjectileFactory;
import objects.enemies.IEnemyFactory;
import objects.powerups.IPowerupFactory;
import progression.IMissionFactory;
import progression.IProgressionManager;
import rendering.ICameraFactory;
import rendering.IRenderer;
import resources.IRes;
import resources.audio.IAudioManager;
import resources.sprites.ISpriteFactory;
import scenes.ISceneFactory;

/**
 * Interface for a ServiceLocator.
 */
public interface IServiceLocator {

    void provide(IAudioManager audioManager);

    void provide(IEnemyFactory enemyFactory);

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

    void provide(IConstants constants);

    void provide(ILoggerFactory loggerFactory);

    void provide(IProgressionManager progressionManager);

    void provide(IMissionFactory missionFactory);

    void provide(ICameraFactory cameraFactory);

    void provide(IProjectileFactory projectileFactory);

    IAudioManager getAudioManager();

    IEnemyFactory getEnemyFactory();

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

    IConstants getConstants();

    ILoggerFactory getLoggerFactory();

    IProgressionManager getProgressionManager();

    IMissionFactory getMissionFactory();

    ICameraFactory getCameraFactory();

    IProjectileFactory getProjectileFactory();

}
