package objects.blocks.platform;

import system.IFactory;

public interface IPlatformFactory extends IFactory {
    Platform newPlatform(final int x, final int y);
}
