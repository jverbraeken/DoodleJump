package objects.blocks.platform;

import system.Factory;

public interface IPlatformFactory extends Factory {
    Platform newPlatform(final int x, final int y);
}
