package system;

import buttons.IButtonFactory;
import constants.IConstants;
import filesystem.IFileSystem;
import input.IInputManager;
import input.InputManager;
import logging.ILoggerFactory;
import math.ICalc;
import objects.blocks.IBlockFactory;
import objects.blocks.platform.IPlatformFactory;
import objects.doodles.IDoodleFactory;
import objects.enemies.IEnemyBuilder;
import objects.powerups.IPowerupFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rendering.IRenderer;
import resources.IRes;
import resources.audio.IAudioManager;
import resources.sprites.ISpriteFactory;
import scenes.ISceneFactory;

import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

public class ServiceLocatorTest {
    private ServiceLocator serviceLocator = new ServiceLocator();

    @Before
    public void Init() {
    }

    // PROVIDERS

    @Test
    public void TestProvideAudioManager() {
        IAudioManager mock = mock(IAudioManager.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getAudioManager());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideAudioManagerNull() {
        serviceLocator.provide((IAudioManager) null);
    }

    @Test
    public void TestProvideEnemyBuilder() {
        IEnemyBuilder mock = mock(IEnemyBuilder.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getEnemyBuilder());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideEnemyBuilderNull() {
        serviceLocator.provide((IEnemyBuilder) null);
    }

    @Test
    public void TestProvideFileSystem() {
        IFileSystem mock = mock(IFileSystem.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getFileSystem());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideFileSystemNull() {
        serviceLocator.provide((IFileSystem) null);
    }

    @Test
    public void TestProvideInputManager() {
        IInputManager mock = mock(IInputManager.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getInputManager());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideInputManagerNull() {
        serviceLocator.provide((InputManager) null);
    }

    @Test
    public void TestProvideCalc() {
        ICalc mock = mock(ICalc.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getCalc());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideCalcNull() {
        serviceLocator.provide((ICalc) null);
    }

    @Test
    public void TestProvideBlockFactory() {
        IBlockFactory mock = mock(IBlockFactory.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getBlockFactory());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideBlockFactoryNull() {
        serviceLocator.provide((IBlockFactory) null);
    }

    @Test
    public void TestProvideDoodleFactory() {
        IDoodleFactory mock = mock(IDoodleFactory.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getDoodleFactory());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideDoodleFactoryNull() {
        serviceLocator.provide((IDoodleFactory) null);
    }

    @Test
    public void TestProvidePowerupFactory() {
        IPowerupFactory mock = mock(IPowerupFactory.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getPowerupFactory());
    }

    @Test(expected = AssertionError.class)
    public void TestProvidePowerupFactoryNull() {
        serviceLocator.provide((IPowerupFactory) null);
    }

    @Test
    public void TestProvideRenderer() {
        IRenderer mock = mock(IRenderer.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getRenderer());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideRendererNull() {
        serviceLocator.provide((IRenderer) null);
    }

    @Test
    public void TestProvideSpriteFactory() {
        ISpriteFactory mock = mock(ISpriteFactory.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getSpriteFactory());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideSpriteFactoryNull() {
        serviceLocator.provide((ISpriteFactory) null);
    }

    @Test
    public void TestProvideSceneFactory() {
        ISceneFactory mock = mock(ISceneFactory.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getSceneFactory());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideSceneFactoryNull() {
        serviceLocator.provide((ISceneFactory) null);
    }

    @Test
    public void TestProvidePlatformFactory() {
        IPlatformFactory mock = mock(IPlatformFactory.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getPlatformFactory());
    }

    @Test(expected = AssertionError.class)
    public void TestProvidePlatformFactoryNull() {
        serviceLocator.provide((IPlatformFactory) null);
    }

    @Test
    public void TestProvideRes() {
        IRes mock = mock(IRes.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getRes());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideResNull() {
        serviceLocator.provide((IRes) null);
    }

    @Test
    public void TestProvideButtonFactory() {
        IButtonFactory mock = mock(IButtonFactory.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getButtonFactory());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideButtonFactoryNull() {
        serviceLocator.provide((IButtonFactory) null);
    }

    @Test
    public void TestProvideConstants() {
        IConstants mock = mock(IConstants.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getConstants());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideConstantsNull() {
        serviceLocator.provide((IConstants) null);
    }

    @Test
    public void TestProvideLoggerFactory() {
        ILoggerFactory mock = mock(ILoggerFactory.class);
        serviceLocator.provide(mock);
        assertEquals(mock, serviceLocator.getLoggerFactory());
    }

    @Test(expected = AssertionError.class)
    public void TestProvideLoggerFactoryNull() {
        serviceLocator.provide((ILoggerFactory) null);
    }

    // GETTERS


}
