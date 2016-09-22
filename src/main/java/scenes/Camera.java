package scenes;

public final class Camera implements ICamera {
    private static double y = 0d;

    /* package */ Camera() {

    }

    @Override
    public void setYPos(double y) {
        Camera.y = y;
    }

    @Override
    public double getYPos() {
        return Camera.y;
    }
}
