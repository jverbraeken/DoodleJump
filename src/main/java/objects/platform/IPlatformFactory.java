package objects.platform;

/**
 * Created by joost on 6-9-16.
 */
public interface IPlatformFactory {
    IPlatform createPlatform(int x, int y);
}
