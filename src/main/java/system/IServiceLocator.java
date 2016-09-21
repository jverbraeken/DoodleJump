package system;

import filesystem.IFileSystem;
import input.IInputManager;
import math.ICalc;
import objects.ICollisions;
import objects.blocks.IBlockFactory;
import objects.blocks.platform.IPlatformFactory;
import objects.buttons.IButtonFactory;
import objects.doodles.IDoodleFactory;
import objects.enemies.IEnemyBuilder;
import objects.powerups.IPowerupFactory;
import rendering.IRenderer;
import resources.IRes;
import resources.audio.IAudioManager;
import resources.sprites.ISpriteFactory;
import scenes.ISceneFactory;

@SuppressWarnings({"checkstyle:javadocvariable", "checkstyle:javadoctype", "checkstyle:javadocmethod"})
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

}
