package objects.doodles;

import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.doodle_behavior.MovementBehavior;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import scenes.World;
import system.IServiceLocator;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DoodleFactory.class, Doodle.class, World.class, StartScreenDoodle.class})
public class DoodleFactoryTest {

    Doodle doodle = mock(Doodle.class);
    MovementBehavior behavior = mock(MovementBehavior.class);
    StartScreenDoodle ssDoodle = mock(StartScreenDoodle.class);
    ILogger logger = mock(ILogger.class);
    ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    IServiceLocator serviceLocator = mock(IServiceLocator.class);
    ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    World world = mock(World.class);

    ISprite[] spritesBlue = new ISprite[]{mock(ISprite.class), mock(ISprite.class), mock(ISprite.class), mock(ISprite.class)};
    ISprite[] spritesGreen = new ISprite[]{mock(ISprite.class), mock(ISprite.class), mock(ISprite.class), mock(ISprite.class)};
    ISprite[] spritesRed = new ISprite[]{mock(ISprite.class), mock(ISprite.class), mock(ISprite.class), mock(ISprite.class)};

    IDoodleFactory doodleFactory;

    @Before
    public void init() throws Exception {
        when(loggerFactory.createLogger(DoodleFactory.class)).thenReturn(logger);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(spriteFactory.getBlueDoodleSprites()).thenReturn(spritesBlue);
        when(spriteFactory.getGreenDoodleSprites()).thenReturn(spritesGreen);
        when(spriteFactory.getRedDoodleSprites()).thenReturn(spritesRed);

        Whitebox.setInternalState(DoodleFactory.class, "serviceLocator", serviceLocator);
        doodleFactory = Whitebox.invokeConstructor(DoodleFactory.class);

        Whitebox.setInternalState(doodle, "behavior", behavior);
    }

    @Test
    public void testCreateDoodleBlue() throws Exception {
        whenNew(Doodle.class).withArguments(serviceLocator, spritesBlue, world).thenReturn(doodle);
        doodleFactory.createDoodle(world, DoodleColors.blue);
        verifyNew(Doodle.class).withArguments(serviceLocator, spritesBlue, world);
        verify(doodle, times(1)).setVerticalSpeed(anyInt());
    }

    @Test
    public void testCreateDoodleGreen() throws Exception {
        whenNew(Doodle.class).withArguments(serviceLocator, spritesGreen, world).thenReturn(doodle);
        doodleFactory.createDoodle(world, DoodleColors.green);
        verifyNew(Doodle.class).withArguments(serviceLocator, spritesGreen, world);
        verify(doodle, times(1)).setVerticalSpeed(anyInt());
    }

    @Test
    public void testCreateDoodleRed() throws Exception {
        whenNew(Doodle.class).withArguments(serviceLocator, spritesRed, world).thenReturn(doodle);
        doodleFactory.createDoodle(world, DoodleColors.red);
        verifyNew(Doodle.class).withArguments(serviceLocator, spritesRed, world);
        verify(doodle, times(1)).setVerticalSpeed(anyInt());
    }

    @Test
    public void testCreateStartScreenDoodle() throws Exception {
        whenNew(StartScreenDoodle.class).withArguments(spritesGreen, serviceLocator).thenReturn(ssDoodle);
        doodleFactory.createStartScreenDoodle();
        verifyNew(StartScreenDoodle.class).withArguments(spritesGreen, serviceLocator);
    }

}
