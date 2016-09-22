package objects.blocks.platform;

import system.IFactory;

public interface IPlatformFactory extends IFactory {

    IPlatform createPlatform(int x, int y);

}
