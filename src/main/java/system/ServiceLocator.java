package system;

import audio.IAudioManager;
import audio.IMusicFactory;
import audio.ISoundFactory;
import objects.enemies.IEnemyBuilder;
import filesystem.IFileSystem;
import input.IInputManager;
import math.ICalc;
import objects.IBlockFactory;
import objects.doodles.IDoodleFactory;
import objects.platform.IPlatformFactory;
import objects.powerups.IPowerupFactory;
import rendering.IRenderer;
import scenes.ISceneFactory;
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

    // objects.enemies
    private IEnemyBuilder enemyBuilder;

    // util
    private ICalc calc;

    // objects
    private IPowerupFactory powerupFactory;
    private IDoodleFactory doodleFactory;
    private IBlockFactory blockFactory;
    private ILevelBuilder levelBuilder;
    private IPlatformFactory platformFactory;

    private ISceneFactory sceneFactory;

    @Override
    public void provide(IAudioManager audioManager) {
        assert audioManager != null;
        this.audioManager = audioManager;
    }

    @Override
    public void provide(IMusicFactory musicFactory) {
        assert musicFactory != null;
        this.musicFactory = musicFactory;
    }

    @Override
    public void provide(ISoundFactory soundFactory) {
        assert soundFactory != null;
        this.soundFactory = soundFactory;
    }

    @Override
    public void provide(IEnemyBuilder enemyBuilder) {
        assert enemyBuilder != null;
        this.enemyBuilder = enemyBuilder;
    }

    @Override
    public void provide(IFileSystem fileSystem) {
        assert fileSystem != null;
        this.fileSystem = fileSystem;
    }

    @Override
    public void provide(IInputManager inputManager) {
        assert inputManager != null;
        this.inputManager = inputManager;
    }

    @Override
    public void provide(ICalc calc) {
        assert calc != null;
        this.calc = calc;
    }

    @Override
    public void provide(IBlockFactory blockFactory) {
        assert blockFactory != null;
        this.blockFactory = blockFactory;
    }

    @Override
    public void provide(IDoodleFactory doodleFactory) {
        assert doodleFactory != null;
        this.doodleFactory = doodleFactory;
    }

    @Override
    public void provide(IPowerupFactory powerupFactory) {
        assert powerupFactory != null;
        this.powerupFactory = powerupFactory;
    }

    @Override
    public void provide(IRenderer renderer) {
        assert renderer != null;
        this.renderer = renderer;
    }

    @Override
    public void provide(ISpriteFactory spriteFactory) {
        assert spriteFactory != null;
        this.spriteFactory = spriteFactory;
    }

    @Override
    public void provide(ILevelBuilder levelBuilder) {
        assert levelBuilder != null;
        this.levelBuilder = levelBuilder;
    }

    @Override
    public void provide(ISceneFactory sceneFactory) {
        assert sceneFactory != null;
        this.sceneFactory = sceneFactory;
    }

    @Override
    public void provide(IPlatformFactory platformFactory) {
        assert platformFactory != null;
        this.platformFactory = platformFactory;
    }

    public IAudioManager getAudioManager() {
        return audioManager;
    }

    @Override
    public IMusicFactory getMusicFactory() {
        return musicFactory;
    }

    @Override
    public ISoundFactory getSoundFactory() {
        return soundFactory;
    }

    @Override
    public IEnemyBuilder getEnemyBuilder() {
        return enemyBuilder;
    }

    @Override
    public IFileSystem getFileSystem() {
        return fileSystem;
    }

    @Override
    public IInputManager getInputManager() {
        return inputManager;
    }

    @Override
    public ICalc getCalc() {
        return calc;
    }

    @Override
    public IBlockFactory getBlockFactory() {
        return blockFactory;
    }

    @Override
    public IDoodleFactory getDoodleFactory() {
        return doodleFactory;
    }

    @Override
    public IPowerupFactory getPowerupFactory() {
        return powerupFactory;
    }

    @Override
    public IRenderer getRenderer() {
        return renderer;
    }

    @Override
    public ISpriteFactory getSpriteFactory() {
        return spriteFactory;
    }

    @Override
    public ILevelBuilder getLevelBuilder() {
        return levelBuilder;
    }

    @Override
    public ISceneFactory getSceneFactory() {
        return sceneFactory;
    }

    @Override
    public IPlatformFactory getPlatformFactory() {
        return platformFactory;
    }

}
