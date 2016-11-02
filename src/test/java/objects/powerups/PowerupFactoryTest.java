package objects.powerups;

import logging.ILogger;
import logging.ILoggerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import progression.IProgressionManager;
import resources.IRes;
import resources.animations.IAnimation;
import resources.animations.IAnimationFactory;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.awt.Point;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PowerupFactory.class,
        Jetpack.class,
        Propeller.class,
        SizeDown.class,
        SizeUp.class,
        Spring.class,
        SpringShoes.class,
        Trampoline.class})
public class PowerupFactoryTest {

    private static ILogger logger = mock(ILogger.class);
    private static ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private static IProgressionManager progressionManager = mock(IProgressionManager.class);
    private static IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private static ISpringCreatedObserver springCreatedObserver = mock(ISpringCreatedObserver.class);
    private static ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    private static ISprite sprite = mock(ISprite.class);
    private static IAnimation sprites = mock(IAnimation.class);
    private static ITrampolineCreatedObserver trampolineCreatedObserver = mock(ITrampolineCreatedObserver.class);
    private static IAnimationFactory animationFactory = mock(IAnimationFactory.class);
    private static Jetpack jetpack = mock(Jetpack.class);
    private static Propeller propeller = mock(Propeller.class);
    private static SizeDown sizeDown = mock(SizeDown.class);
    private static SizeUp sizeUp = mock(SizeUp.class);
    private static Spring spring = mock(Spring.class);
    private static SpringShoes springShoes = mock(SpringShoes.class);
    private static Trampoline trampoline = mock(Trampoline.class);

    private static int level = 1;
    private static int xPos = 0;
    private static int yPos = 0;
    private static Point point = new Point(xPos, yPos);

    private IPowerupFactory powerupFactory;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void initMock() throws Exception {
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getProgressionManager()).thenReturn(progressionManager);
        when(loggerFactory.createLogger(PowerupFactory.class)).thenReturn(logger);
        when(animationFactory.getAnimation(Matchers.<IRes.Animations>any())).thenReturn(sprites);
        when(spriteFactory.getPowerupSprite(anyObject(), anyInt())).thenReturn(sprite);
        when(sprite.getWidth()).thenReturn(0);
        when(sprite.getHeight()).thenReturn(0);
        when(progressionManager.getPowerupLevel(any(Powerups.class))).thenReturn(1);
    }

    @Before
    public void init() throws Exception {
        PowerupFactory.register(serviceLocator);
        powerupFactory = Whitebox.invokeConstructor(PowerupFactory.class);
    }

    @After
    public void finish() {
        powerupFactory = null;
    }


    @Test
    public void testCreateJetpack() throws Exception {
        when(progressionManager.getPowerupLevel(anyObject())).thenReturn(1);
        whenNew(Jetpack.class).withArguments(eq(serviceLocator), eq(point), anyInt()).thenReturn(jetpack);
        powerupFactory.createJetpack(xPos, yPos);
        verifyNew(Jetpack.class).withArguments(eq(serviceLocator), eq(point), anyInt());
    }

    @Test
    public void testCreatePropeller() throws Exception {
        whenNew(Propeller.class).withArguments(eq(serviceLocator), eq(point), anyInt()).thenReturn(propeller);
        powerupFactory.createPropeller(xPos, yPos);
        verifyNew(Propeller.class).withArguments(eq(serviceLocator), eq(point), anyInt());
    }

    @Test
    public void testCreateSizeDown() throws Exception {
        whenNew(SizeDown.class).withArguments(eq(serviceLocator), eq(point), anyInt()).thenReturn(sizeDown);
        powerupFactory.createSizeDown(xPos, yPos);
        verifyNew(SizeDown.class).withArguments(eq(serviceLocator), eq(point), anyInt());
    }

    @Test
    public void testCreateSizeUp() throws Exception {
        whenNew(SizeUp.class).withArguments(eq(serviceLocator), eq(point), anyInt()).thenReturn(sizeUp);
        powerupFactory.createSizeUp(xPos, yPos);
        verifyNew(SizeUp.class).withArguments(eq(serviceLocator), eq(point), anyInt());
    }

    @Test
    public void testCreateSpring() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.spring)).thenReturn(level);
        when(spriteFactory.getSprite(Matchers.<IRes.Sprites>any())).thenReturn(sprite);

        whenNew(Spring.class).withArguments(eq(serviceLocator), eq(point), anyInt()).thenReturn(spring);
        powerupFactory.createSpring(xPos, yPos);
        verifyNew(Spring.class).withArguments(eq(serviceLocator), eq(point), anyInt());
    }

    @Test
    public void testCreateTrampoline1() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.trampoline)).thenReturn(1);
        when(spriteFactory.getSprite(Matchers.<IRes.Sprites>any())).thenReturn(sprite);

        whenNew(Trampoline.class).withArguments(eq(serviceLocator), eq(point), anyInt()).thenReturn(trampoline);
        powerupFactory.createTrampoline(xPos, yPos);
        verifyNew(Trampoline.class).withArguments(eq(serviceLocator), eq(point), anyInt());
    }

    @Test
    public void testAddObserverSpring() throws Exception {
        powerupFactory.addObserver(springCreatedObserver);
        List<ISpringCreatedObserver> springObservers = Whitebox.getInternalState(powerupFactory, "springObservers");
        assertTrue(springObservers.contains(springCreatedObserver));
    }

    @Test
    public void testAddObserverSpringNullInput() throws Exception {
        ISpringCreatedObserver nullObserver = null;
        thrown.expect(IllegalArgumentException.class);
        powerupFactory.addObserver(nullObserver);
    }

    @Test
    public void testRemoveObserverSpring() throws Exception {
        powerupFactory.addObserver(springCreatedObserver);
        List<ISpringCreatedObserver> springObservers = Whitebox.getInternalState(powerupFactory, "springObservers");
        powerupFactory.removeObserver(springCreatedObserver);
        assertFalse(springObservers.contains(springCreatedObserver));
    }

    @Test
    public void testRemoveObserverSpringNullInput() throws Exception {
        ISpringCreatedObserver nullObserver = null;
        thrown.expect(IllegalArgumentException.class);
        powerupFactory.removeObserver(nullObserver);
    }

    @Test
    public void testAddObserverTrampoline() throws Exception {
        powerupFactory.addObserver(trampolineCreatedObserver);
        List<ITrampolineCreatedObserver> trampolineObservers = Whitebox.getInternalState(powerupFactory, "trampolineObservers");
        assertTrue(trampolineObservers.contains(trampolineCreatedObserver));
    }

    @Test
    public void testAddObserverTrampolineNullInput() throws Exception {
        ITrampolineCreatedObserver nullObserver = null;
        thrown.expect(IllegalArgumentException.class);
        powerupFactory.addObserver(nullObserver);
    }

    @Test
    public void testRemoveObserverTrampoline() throws Exception {
        powerupFactory.addObserver(trampolineCreatedObserver);
        List<ITrampolineCreatedObserver> trampolineObservers = Whitebox.getInternalState(powerupFactory, "trampolineObservers");
        powerupFactory.removeObserver(trampolineCreatedObserver);
        assertFalse(trampolineObservers.contains(trampolineCreatedObserver));
    }

    @Test
    public void testRemoveObserverTrampolineNullInput() throws Exception {
        ITrampolineCreatedObserver nullObserver = null;
        thrown.expect(IllegalArgumentException.class);
        powerupFactory.removeObserver(nullObserver);
    }

}
