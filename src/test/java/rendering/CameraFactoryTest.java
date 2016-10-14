package rendering;

import logging.ILogger;
import logging.ILoggerFactory;
import objects.doodles.IDoodle;
import objects.powerups.PowerupFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import resources.sprites.ISprite;
import system.IServiceLocator;

import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CameraFactory.class, ArcadeCamera.class, DoodleCamera.class, StaticCamera.class})
public class CameraFactoryTest {

    private static ILogger logger = mock(ILogger.class);
    private static ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
    private static IServiceLocator serviceLocator = mock(IServiceLocator.class);
    private static ISprite sprite = mock(ISprite.class);
    private static IDoodle doodle = mock(IDoodle.class);

    private static ArcadeCamera arcadeCamera = mock(ArcadeCamera.class);
    private static DoodleCamera doodleCamera = mock(DoodleCamera.class);
    private static StaticCamera staticCamera = mock(StaticCamera.class);

    private ICameraFactory cameraFactory;

    @BeforeClass
    public static void initMock() throws Exception {
        when(loggerFactory.createLogger(CameraFactory.class)).thenReturn(logger);
        when(sprite.getWidth()).thenReturn(0);
        when(sprite.getHeight()).thenReturn(0);
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
    }

    @Before
    public void init() throws Exception {
        CameraFactory.register(serviceLocator);
        cameraFactory = Whitebox.invokeConstructor(CameraFactory.class);
    }

    @After
    public void finish() {
        cameraFactory = null;
    }

    @Test
    public void testCreateArcadeCamera() throws Exception {
        whenNew(ArcadeCamera.class).withNoArguments().thenReturn(arcadeCamera);
        cameraFactory.createArcadeCamera();
        verifyNew(ArcadeCamera.class).withNoArguments();
    }

    @Test
    public void testCreateDoodleCamera() throws Exception {
        whenNew(DoodleCamera.class).withArguments(serviceLocator, doodle).thenReturn(doodleCamera);
        cameraFactory.createDoodleCamera(doodle);
        verifyNew(DoodleCamera.class).withArguments(serviceLocator, doodle);
    }

    @Test
    public void testCreateStaticCamera() throws Exception {
        whenNew(StaticCamera.class).withNoArguments().thenReturn(staticCamera);
        cameraFactory.createStaticCamera();
        verifyNew(StaticCamera.class).withNoArguments();
    }

}
