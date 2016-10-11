package objects.blocks.platform;

import objects.IGameObject;
import objects.doodles.IDoodle;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.Map;

/**
 * Created by Nick on 11-10-2016.
 */
public abstract class PlatformDecorator implements IPlatform{

    protected IPlatform contained;

    protected IServiceLocator serviceLocator;

    /**
     * Platform decorator.
     *
     * @param sL the servicelocator.
     * @param platform the encapsulated platform.
     */
    public PlatformDecorator(IServiceLocator sL, IPlatform platform) {
        serviceLocator = sL;
        contained = platform;
    }

    /** {@inheritDoc} */
    @Override
    public void update(final double delta) {
        contained.update(delta);
    }

    /** {@inheritDoc} */
    @Override
    public double getBoost() {
        return contained.getBoost();
    }

    /** {@inheritDoc} */
    @Override
    public void render() {
        //TODO: refactor base platform render.
        contained.render();
        serviceLocator.getRenderer().drawSprite(getSprite(), (int) contained.getXPos(), (int) contained.getYPos());
    }

    /** {@inheritDoc} */
    @Override
    public void addXPos(final double xPos) {
        contained.addXPos(xPos);
    }

    /** {@inheritDoc} */
    @Override
    public void addYPos(final double yPos) {
        contained.addYPos(yPos);
    }

    /** {@inheritDoc} */
    @Override
    public boolean checkCollision(final IGameObject gameObject) {
        return contained.checkCollision(gameObject);
    }

    /** {@inheritDoc} */
    @Override
    public void collidesWith(IDoodle doodle) {
        contained.collidesWith(doodle);
    }

    /** {@inheritDoc} */
    @Override
    public double[] getHitBox() {
        return contained.getHitBox();
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getSprite() {
        return contained.getSprite();
    }

    /** {@inheritDoc} */
    @Override
    public double getXPos() {
        return contained.getXPos();
    }

    /** {@inheritDoc} */
    @Override
    public double getYPos() {
        return contained.getYPos();
    }

    /** {@inheritDoc} */
    @Override
    public void setHitBox(int left, int top, int right, int bottom) {
        contained.setHitBox(left, top, right, bottom);
    }

    /** {@inheritDoc} */
    @Override
    public void setSprite(ISprite sprite) {
        contained.setSprite(sprite);
    }

    /** {@inheritDoc} */
    @Override
    public void setXPos(double xPos) {
        contained.setXPos(xPos);
    }

    /** {@inheritDoc} */
    @Override
    public void setYPos(double yPos) {
        contained.setYPos(yPos);
    }

    /** {@inheritDoc} */
    @Override
    public Map<Platform.PlatformProperties, Integer> getProps(){
        return contained.getProps();
    }

    /** {@inheritDoc} */
    @Override
    public void updateEnums(double xpos, double ypos) {
        contained.updateEnums(xpos, ypos);
    }

    /** {@inheritDoc} */
    @Override
    public void setOffset(int offSet) {
        contained.setOffset(offSet);
    }

    /** {@inheritDoc} */
    @Override
    public int getOffset() {
        return contained.getOffset();
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getBrokenSprite(final int numberOfAnimation) {
        return contained.getBrokenSprite(numberOfAnimation);
    }

    /** {@inheritDoc} */
    @Override
    public Map<Platform.Directions, Integer> getDirections() {
        return contained.getDirections();
    }
}
