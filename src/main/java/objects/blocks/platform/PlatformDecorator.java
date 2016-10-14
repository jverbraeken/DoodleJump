package objects.blocks.platform;

import objects.IGameObject;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.Map;

/**
 * Abstract class for the platform decorations.
 *
 * Note that the suppressed warning is intentional.
 * Design for extension doesnt apply since there are the base cases for each method and you don't want to make those final.
 * This is due to the fact that each decorator need to be able to add functionality to this base case.
 */
@SuppressWarnings("checkstyle:designforextension")
public abstract class PlatformDecorator implements IPlatform {

    /**
     * The contained platform.
     */
    private IPlatform contained;

    /**
     * Used to access all services.
     */
    private IServiceLocator serviceLocator;

    /**
     * Platform decorator.
     *
     * @param sL       the servicelocator.
     * @param platform the encapsulated platform.
     */
    public PlatformDecorator(final IServiceLocator sL, final IPlatform platform) {
        assert sL != null;
        assert platform != null;

        serviceLocator = sL;
        contained = platform;
    }

    /**
     * Return the contained platform.
     * @return the platform.
     */
    /* package */ IPlatform getContained() {
        return contained;
    }

    /**
     * Return the servicelocator.
     * @return the servicelocator.
     */
    public IServiceLocator getServiceLocator() {
        return serviceLocator;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        contained.update(delta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoost() {
        return contained.getBoost();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        contained.render();
        serviceLocator.getRenderer().drawSprite(getSprite(), (int) contained.getXPos(), (int) contained.getYPos());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addXPos(final double xPos) {
        contained.addXPos(xPos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addYPos(final double yPos) {
        contained.addYPos(yPos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkCollision(final IGameObject gameObject) {
        return contained.checkCollision(gameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collidesWith(final IDoodle doodle) {
        contained.collidesWith(doodle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] getHitBox() {
        return contained.getHitBox();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ISprite getSprite() {
        return contained.getSprite();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getXPos() {
        return contained.getXPos();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getYPos() {
        return contained.getYPos();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHitBox(final int left, final int top, final int right, final int bottom) {
        contained.setHitBox(left, top, right, bottom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSprite(final ISprite sprite) {
        contained.setSprite(sprite);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setXPos(final double xPos) {
        contained.setXPos(xPos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setYPos(final double yPos) {
        contained.setYPos(yPos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Platform.PlatformProperties, Integer> getProps() {
        return contained.getProps();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEnums(final double xpos, final double ypos) {
        contained.updateEnums(xpos, ypos);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOffset(final int offSet) {
        contained.setOffset(offSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOffset() {
        return contained.getOffset();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Platform.Directions, Integer> getDirections() {
        return contained.getDirections();
    }
}
