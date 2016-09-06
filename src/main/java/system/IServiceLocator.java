package system;

import audio.IAudioManager;
import audio.IBackgroundMusic;
import audio.IMusicFactory;
import audio.ISoundFactory;
import enemies.IEnemyBuilder;
import filesystem.IFileSystem;
import input.IInputManager;
import math.ICalc;
import objects.blocks.IBlockFactory;
import objects.blocks.IPlatformFactory;
import objects.doodles.IDoodleFactory;
import objects.powerups.IPowerupFactory;
import rendering.IRenderer;
import sprites.ISpriteFactory;

/**
 * Created by joost on 6-9-16.
 */
public interface IServiceLocator {
    void provide(IAudioManager audioManager);
    void provide(IMusicFactory musicFactory);
    void provide(ISoundFactory soundFactory);
    void provide(IEnemyBuilder enemyBuilder);
    void provide(IFileSystem fileSystem);
    void provide(IInputManager inputManager);
    void provide(ICalc calc);
    void provide(IBlockFactory blockFactory);
    void provide(IDoodleFactory doodleFactory);
    void provide(IPowerupFactory powerupFactory);
    void provide(IRenderer renderer);
    void provide(ISpriteFactory spriteFactory);
    void provide(ILevelBuilder levelBuilder);

    IAudioManager getAudioManager();
    IMusicFactory getMusicFactory();
    ISoundFactory getSoundFactory();
    IEnemyBuilder getEnemyBuilder();
    IFileSystem getFileSystem();
    IInputManager getInputManager();
    ICalc getCalc();
    IBlockFactory getBlockFactory();
    IDoodleFactory getDoodleFactory();
    IPowerupFactory getPowerupFactory();
    IRenderer getRenderer();
    ISpriteFactory getSpriteFactory();
    ILevelBuilder getLevelBuilder();
}
