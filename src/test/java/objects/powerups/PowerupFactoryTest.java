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
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import progression.IProgressionManager;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PowerupFactory.class,
        Jetpack.class,
        SpaceRocket.class,
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
    private static ISprite[] sprites = {sprite, sprite,};
    private static ITrampolineCreatedObserver trampolineCreatedObserver = mock(ITrampolineCreatedObserver.class);
    private static Jetpack jetpack = mock(Jetpack.class);
    private static Propeller propeller = mock(Propeller.class);
    private static SizeDown sizeDown = mock(SizeDown.class);
    private static SizeUp sizeUp = mock(SizeUp.class);
    private static SpaceRocket spaceRocket = mock(SpaceRocket.class);
    private static Spring spring = mock(Spring.class);
    private static SpringShoes springShoes = mock(SpringShoes.class);
    private static Trampoline trampoline = mock(Trampoline.class);

    private static int xPos = 0;
    private static int yPos = 0;

    private IPowerupFactory powerupFactory;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void initMock() throws Exception {
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getSpriteFactory()).thenReturn(spriteFactory);
        when(serviceLocator.getProgressionManager()).thenReturn(progressionManager);
        when(loggerFactory.createLogger(PowerupFactory.class)).thenReturn(logger);
        when(spriteFactory.getPowerupSprite(anyObject(), anyInt())).thenReturn(sprite);
        when(sprite.getWidth()).thenReturn(0);
        when(sprite.getHeight()).thenReturn(0);

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
    public void testCreateJetpackStage1() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.jetpack)).thenReturn(1);
        whenNew(Jetpack.class).withArguments(serviceLocator, xPos, yPos).thenReturn(jetpack);
        powerupFactory.createJetpack(xPos, yPos);
        verifyNew(Jetpack.class).withArguments(serviceLocator, xPos, yPos);
    }

    @Test
    public void testCreateJetpackStage2() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.jetpack)).thenReturn(2);
        whenNew(SpaceRocket.class).withArguments(serviceLocator, xPos, yPos).thenReturn(spaceRocket);
        powerupFactory.createJetpack(xPos, yPos);
        verifyNew(SpaceRocket.class).withArguments(serviceLocator, xPos, yPos);
    }

    @Test
    public void testCreateJetpackLevelOutsideRange() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.jetpack)).thenReturn(3);
        assertEquals(null, powerupFactory.createJetpack(xPos, yPos));
    }


    @Test
    public void testCreatePropeller() throws Exception {
        whenNew(Propeller.class).withArguments(serviceLocator, xPos, yPos).thenReturn(propeller);
        powerupFactory.createPropeller(xPos, yPos);
        verifyNew(Propeller.class).withArguments(serviceLocator, xPos, yPos);
    }

    @Test
    public void testCreateSizeDown() throws Exception {
        whenNew(SizeDown.class).withArguments(serviceLocator, xPos, yPos).thenReturn(sizeDown);
        powerupFactory.createSizeDown(xPos, yPos);
        verifyNew(SizeDown.class).withArguments(serviceLocator, xPos, yPos);
    }

    @Test
    public void testCreateSizeUp() throws Exception {
        whenNew(SizeUp.class).withArguments(serviceLocator, xPos, yPos).thenReturn(sizeUp);
        powerupFactory.createSizeUp(xPos, yPos);
        verifyNew(SizeUp.class).withArguments(serviceLocator, xPos, yPos);
    }

    @Test
    public void testCreateSpring1() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.spring)).thenReturn(1);
        when(spriteFactory.getPowerupSprite(Powerups.spring, 1)).thenReturn(sprite);
        when(spriteFactory.getSpringUsedSprite(1)).thenReturn(sprite);

        int[] boost = Whitebox.getInternalState(PowerupFactory.class, "BOOST_SPRING");

        whenNew(Spring.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[0]).thenReturn(spring);
        powerupFactory.createSpring(xPos, yPos);
        verifyNew(Spring.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[0]);
    }

    @Test
    public void testCreateSpring2() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.spring)).thenReturn(2);
        when(spriteFactory.getPowerupSprite(Powerups.spring, 2)).thenReturn(sprite);
        when(spriteFactory.getSpringUsedSprite(2)).thenReturn(sprite);

        int[] boost = Whitebox.getInternalState(PowerupFactory.class, "BOOST_SPRING");

        whenNew(Spring.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[1]).thenReturn(spring);
        powerupFactory.createSpring(xPos, yPos);
        verifyNew(Spring.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[1]);
    }

    @Test
    public void testCreateSpring3() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.spring)).thenReturn(3);
        when(spriteFactory.getPowerupSprite(Powerups.spring, 3)).thenReturn(sprite);
        when(spriteFactory.getSpringUsedSprite(3)).thenReturn(sprite);

        int[] boost = Whitebox.getInternalState(PowerupFactory.class, "BOOST_SPRING");

        whenNew(Spring.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[2]).thenReturn(spring);
        powerupFactory.createSpring(xPos, yPos);
        verifyNew(Spring.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[2]);
    }

    @Test
    public void testCreateSpringLevelOutsideRange() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.spring)).thenReturn(4);
        assertEquals(null, powerupFactory.createSpring(xPos, yPos));
    }


    @Test
    public void testCreateSpringShoes() throws Exception {
        whenNew(SpringShoes.class).withArguments(serviceLocator, xPos, yPos).thenReturn(springShoes);
        powerupFactory.createSpringShoes(xPos, yPos);
        verifyNew(SpringShoes.class).withArguments(serviceLocator, xPos, yPos);
    }

    @Test
    public void testCreateTrampoline1() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.trampoline)).thenReturn(1);
        when(spriteFactory.getPowerupSprite(Powerups.trampoline, 1)).thenReturn(sprite);
        when(spriteFactory.getTrampolineUsedSprite(1)).thenReturn(sprite);

        int[] boost = Whitebox.getInternalState(PowerupFactory.class, "BOOST_TRAMPOLINE");

        whenNew(Trampoline.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[0]).thenReturn(trampoline);
        powerupFactory.createTrampoline(xPos, yPos);
        verifyNew(Trampoline.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[0]);
    }

    @Test
    public void testCreateTrampoline2() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.trampoline)).thenReturn(2);
        when(spriteFactory.getPowerupSprite(Powerups.trampoline, 2)).thenReturn(sprite);
        when(spriteFactory.getTrampolineUsedSprite(2)).thenReturn(sprite);

        int[] boost = Whitebox.getInternalState(PowerupFactory.class, "BOOST_TRAMPOLINE");

        whenNew(Trampoline.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[1]).thenReturn(trampoline);
        powerupFactory.createTrampoline(xPos, yPos);
        verifyNew(Trampoline.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[1]);
    }

    @Test
    public void testCreateTrampoline3() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.trampoline)).thenReturn(3);
        when(spriteFactory.getPowerupSprite(Powerups.trampoline, 3)).thenReturn(sprite);
        when(spriteFactory.getTrampolineUsedSprite(3)).thenReturn(sprite);

        int[] boost = Whitebox.getInternalState(PowerupFactory.class, "BOOST_TRAMPOLINE");

        whenNew(Trampoline.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[2]).thenReturn(trampoline);
        powerupFactory.createTrampoline(xPos, yPos);
        verifyNew(Trampoline.class).withArguments(serviceLocator, xPos, yPos, sprite, sprite, boost[2]);
    }

    @Test
    public void testCreateTrampolineLevelOutsideRange() throws Exception {
        when(progressionManager.getPowerupLevel(Powerups.trampoline)).thenReturn(4);
        assertEquals(null, powerupFactory.createTrampoline(xPos, yPos));
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
