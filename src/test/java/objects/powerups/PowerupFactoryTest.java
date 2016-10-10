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
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.IServiceLocator;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PowerupFactory.class)
public class PowerupFactoryTest {

    private static ILogger logger = mock(ILogger.class);
    private static ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private static IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private static ISpriteFactory spriteFactory = mock(ISpriteFactory.class);
    private static ISprite sprite = mock(ISprite.class);

    private static Jetpack jetpack = mock(Jetpack.class);
    private static Propeller propeller = mock(Propeller.class);
    private static SizeDown sizeDown = mock(SizeDown.class);
    private static SizeUp sizeUp = mock(SizeUp.class);
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

        when(loggerFactory.createLogger(PowerupFactory.class)).thenReturn(logger);
        when(spriteFactory.getJetpackSprite()).thenReturn(sprite);
        when(spriteFactory.getPropellerSprite()).thenReturn(sprite);
        when(spriteFactory.getSizeDownSprite()).thenReturn(sprite);
        when(spriteFactory.getSizeUpSprite()).thenReturn(sprite);
        when(spriteFactory.getSpringSprite()).thenReturn(sprite);
        when(spriteFactory.getSpringShoesSprite()).thenReturn(sprite);
        when(spriteFactory.getTrampolineSprite()).thenReturn(sprite);
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
    public void testCreateJetpack() throws Exception {
        whenNew(Jetpack.class).withArguments(serviceLocator, xPos, yPos).thenReturn(jetpack);
        powerupFactory.createJetpack(xPos, yPos);
        verifyNew(Jetpack.class).withArguments(serviceLocator, xPos, yPos);
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
    public void testCreateSpring() throws Exception {
        whenNew(Spring.class).withArguments(serviceLocator, xPos, yPos).thenReturn(spring);
        powerupFactory.createSpring(xPos, yPos);
        verifyNew(Spring.class).withArguments(serviceLocator, xPos, yPos);
    }

    @Test
    public void testCreateSpringShoes() throws Exception {
        whenNew(SpringShoes.class).withArguments(serviceLocator, xPos, yPos).thenReturn(springShoes);
        powerupFactory.createSpringShoes(xPos, yPos);
        verifyNew(SpringShoes.class).withArguments(serviceLocator, xPos, yPos);
    }

    @Test
    public void testCreateTrampoline() throws Exception {
        whenNew(Trampoline.class).withArguments(serviceLocator, xPos, yPos).thenReturn(trampoline);
        powerupFactory.createTrampoline(xPos, yPos);
        verifyNew(Trampoline.class).withArguments(serviceLocator, xPos, yPos);
    }

}