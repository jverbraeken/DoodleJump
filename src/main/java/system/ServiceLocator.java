package system;

import audio.IAudioManager;
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
import org.junit.runners.parameterized.BlockJUnit4ClassRunnerWithParametersFactory;
import rendering.IRenderer;
import sprites.ISpriteFactory;

/**
 * Created by joost on 6-9-16.
 */
public class ServiceLocator implements IServiceLocator {

    // input
    private IInputManager inputManager;

    // audio
    private IAudioManager audioManager;
    private IMusicFactory musicFactory;
    private ISoundFactory soundFactory;

    // rendering
    private IRenderer renderer;

    // filesystem
    private IFileSystem fileSystem;

    // resources
    private ILevelBuilder levelFactory;
    private ISpriteFactory spriteFactory;

    // enemies
    private IEnemyBuilder enemyBuilder;

    // util
    private ICalc calc;

    // objects
    private IPowerupFactory powerupFactory;
    private IDoodleFactory doodleFactory;
    private IBlockFactory blockFactory;
    private ILevelBuilder levelBuilder;

    public void provide(IAudioManager audioManager) {
        assert audioManager != null;
        this.audioManager = audioManager;
    }

    public void provide(IMusicFactory musicFactory) {
        assert musicFactory != null;
        this.musicFactory = musicFactory;
    }

    public void provide(ISoundFactory soundFactory) {
        assert soundFactory != null;
        this.soundFactory = soundFactory;
    }

    public void provide(IEnemyBuilder enemyBuilder) {
        assert enemyBuilder != null;
        this.enemyBuilder = enemyBuilder;
    }

    public void provide(IFileSystem fileSystem) {
        assert fileSystem != null;
        this.fileSystem = fileSystem;
    }

    public void provide(IInputManager inputManager) {
        assert inputManager != null;
        this.inputManager = inputManager;
    }

    public void provide(ICalc calc) {
        assert calc != null;
        this.calc = calc;
    }

    public void provide(IBlockFactory blockFactory) {
        assert blockFactory != null;
        this.blockFactory = blockFactory;
    }

    public void provide(IDoodleFactory doodleFactory) {
        assert doodleFactory != null;
        this.doodleFactory = doodleFactory;
    }

    public void provide(IPowerupFactory powerupFactory) {
        assert powerupFactory != null;
        this.powerupFactory = powerupFactory;
    }

    public void provide(IRenderer renderer) {
        assert renderer != null;
        this.renderer = renderer;
    }

    public void provide(ISpriteFactory spriteFactory) {
        assert spriteFactory != null;
        this.spriteFactory = spriteFactory;
    }

    public void provide(ILevelBuilder levelBuilder) {
        assert levelBuilder != null;
        this.levelBuilder = levelBuilder;
    }

    public IAudioManager getAudioManager() {
        return audioManager;
    }

    public IMusicFactory getMusicFactory() {
        return musicFactory;
    }

    public ISoundFactory getSoundFactory() {
        return soundFactory;
    }

    public IEnemyBuilder getEnemyBuilder() {
        return enemyBuilder;
    }

    public IFileSystem getFileSystem() {
        return fileSystem;
    }

    public IInputManager getInputManager() {
        return inputManager;
    }

    public ICalc getCalc() {
        return calc;
    }

    public IBlockFactory getBlockFactory() {
        return blockFactory;
    }

    public IDoodleFactory getDoodleFactory() {
        return doodleFactory;
    }

    public IPowerupFactory getPowerupFactory() {
        return powerupFactory;
    }

    public IRenderer getRenderer() {
        return renderer;
    }

    public ISpriteFactory getSpriteFactory() {
        return spriteFactory;
    }

    public ILevelBuilder getLevelBuilder() {
        return levelBuilder;
    }
}
