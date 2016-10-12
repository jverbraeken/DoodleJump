package objects.blocks.platform;

import objects.blocks.BlockFactory;
import system.IServiceLocator;

/**
 * The platform decorator used to describe vertical movement.
 */
public final class PlatformVertical extends PlatformDecorator implements IPlatform {

    /**
     * One third of the game height.
     */
    private static double movingDistance;

    /**
     * One fifth.
     */
    private static final double ONE_FIFTH = 0.2d;

    /**
     * Fifty-fifty chance.
     */
    private static final double FIFTY_FIFTY = 0.5d;

    /**
     * Platform constructor.
     *
     * @param sL       the servicelocator.
     * @param platform the encapsulated platform.
     */
    PlatformVertical(final IServiceLocator sL, final IPlatform platform) {
        super(sL, platform);
        getContained().setSprite(sL.getSpriteFactory().getPlatformSpriteVert());
        getContained().getProps().put(Platform.PlatformProperties.movingVertically, 1);

        int gameHeight = sL.getConstants().getGameHeight();
        movingDistance = gameHeight * ONE_FIFTH;

        getDirections().put(Platform.Directions.up, 1);
        getDirections().put(Platform.Directions.down, -1);

        int upOrDown = 1;
        if (sL.getCalc().getRandomDouble(1) < FIFTY_FIFTY) {
            upOrDown = -1;
        }
        platform.getProps().put(Platform.PlatformProperties.movingVertically, upOrDown);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double delta) {
        final double xPos = this.getXPos();
        final double yPos = this.getYPos();

        if (BlockFactory.isSpecialPlatform(this)) {
            updateEnums(xPos, yPos);
        }

        if (getProps().containsKey(Platform.PlatformProperties.movingVertically)) {

            int movingProperty = getProps().get(Platform.PlatformProperties.movingVertically);

            if (movingProperty == getDirections().get(Platform.Directions.up)) {
                setYPos(yPos - 2);
                setOffset(getOffset() - 2);
            } else if (movingProperty == getDirections().get(Platform.Directions.down)) {
                setYPos(yPos + 2);
                setOffset(getOffset() + 2);
            }
        }

        getContained().update(delta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEnums(final double xPos, final double yPos) {
        if (getOffset() > movingDistance) {
            this.getProps().replace(Platform.PlatformProperties.movingVertically, 1);
        } else if (getOffset() < -movingDistance) {
            this.getProps().replace(Platform.PlatformProperties.movingVertically, -1);
        }
    }
}
