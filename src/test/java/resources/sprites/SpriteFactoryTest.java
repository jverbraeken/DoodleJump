package resources.sprites;

import logging.ILoggerFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import resources.IRes;
import system.IServiceLocator;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SpriteFactory.class)
public class SpriteFactoryTest {
    private static ISpriteFactory spriteFactory;

    @BeforeClass
    public static void init() throws Exception {
        ILoggerFactory loggerFactory = mock(ILoggerFactory.class);
        IServiceLocator serviceLocator = mock(IServiceLocator.class);
        IRes res = mock(IRes.class);
        when(res.getSpritePath(anyObject())).thenReturn(mock(String.class));
        when(serviceLocator.getLoggerFactory()).thenReturn(loggerFactory);
        when(serviceLocator.getRes()).thenReturn(res);
        Whitebox.setInternalState(SpriteFactory.class, "sL", serviceLocator);
        spriteFactory = PowerMockito.spy(Whitebox.invokeConstructor(SpriteFactory.class));
        when(spriteFactory, method(SpriteFactory.class, "loadISprite", IRes.Sprites.class))
                .withArguments(anyObject())
                .thenReturn(mock(ISprite.class));
    }

    @Test
    public void TestGetMenuButtonSprite() {
        spriteFactory.getMenuButtonSprite();
        int a;
    }
}
