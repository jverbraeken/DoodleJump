package objects.blocks.platform;

import objects.AGameObject;
import objects.blocks.BlockFactory;
import objects.doodles.IDoodle;
import resources.audio.IAudioManager;
import resources.sprites.ISprite;
import system.IServiceLocator;

import java.util.EnumMap;
import java.util.Map;

/**
 * This class focuses on the implementation of platforms.
 */
public class Platform extends AGameObject implements IPlatform {

    /**
     * The boost the Doodle gets from colliding with the platform.
     */
    private static final double BOOST = -18;

    /**
     * The height of the game.
     */
    private static int GAME_HEIGHT;

    /**
     * One third of the game height.
     */
    private static double MOVINGDISTANCE;

    /**
     * An enum to define what the platform does.
     */
    public enum PlatformProperties {
        /**
         * Defined if the platform moves vertically.
         */
        movingVertically,
        /**
         * Defined if the platform moves horizontally.
         */
        movingHorizontally,
        /**
         * Defined if the platform breaks when colliding with Doodle.
         */
        breaks
    }

    /**
     * The properties for this platform.
     */
    private Map<PlatformProperties, Integer> props = new EnumMap<>(PlatformProperties.class);

    /**
     * The start y of the platform.
     */
    private int offSet = 0;

    /**
     * Platform constructor.
     *
     * @param sL - The games service locator.
     * @param x - The X location for the platform.
     * @param y - The Y location for the platform.
     */
    /* package */ Platform(final IServiceLocator sL, final int x, final int y, final ISprite sprite) {
        super(sL, x, y, sprite);

        GAME_HEIGHT = sL.getConstants().getGameHeight();
        MOVINGDISTANCE = GAME_HEIGHT * 0.20;

        //int cameraYpos = (int) sL.getRenderer().getCamera().getYPos();
        //startX = x + cameraYpos;
        //startY = y + cameraYpos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoost() {
        return Platform.BOOST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render() {
        double xpos = this.getXPos();
        double ypos = this.getYPos();

        if(BlockFactory.isSpecialPlatform(this)) {
            updateEnums(xpos, ypos);
        }

        if (props.containsKey(PlatformProperties.movingHorizontally)) {
            if (props.get(PlatformProperties.movingHorizontally) > 0) {
                this.setXPos(xpos + 2);
            } else {
                this.setXPos(xpos - 2);
            }
        } else if (props.containsKey(PlatformProperties.movingVertically)) {
            if (props.get(PlatformProperties.movingVertically) > 0) {
                this.setYPos(ypos - 2);
                offSet = offSet - 2;
            } else if (props.get(PlatformProperties.movingVertically) < 0) {
                this.setYPos(ypos + 2);
                offSet = offSet + 2;
            }
        }

        xpos = this.getXPos();
        ypos = this.getYPos();

        if (getProps().containsKey(PlatformProperties.breaks)) {
            int breaks = (int) getProps().get(PlatformProperties.breaks);
            if (breaks == 1) {
                sL.getRenderer().drawSprite(getSprite(), (int) xpos, (int) ypos);
            } else if (breaks < 5 && breaks > 1) {
                sL.getRenderer().drawSprite(getBrokenSprite(breaks), (int) xpos, (int) ypos);
            }
        } else {
            sL.getRenderer().drawSprite(getSprite(), (int) xpos, (int) ypos);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void updateEnums(double xpos, double ypos) {

        int gameWidth = sL.getConstants().getGameWidth();
        if (xpos > gameWidth - this.getSprite().getWidth()) {
            this.props.replace(PlatformProperties.movingHorizontally, -1);
        } else if (xpos < 1) {
            this.props.replace(PlatformProperties.movingHorizontally, 1);
        }

        if (offSet > MOVINGDISTANCE) {
            this.props.replace(PlatformProperties.movingVertically, 1);
        } else if (offSet < - MOVINGDISTANCE ) {
            this.props.replace(PlatformProperties.movingVertically, -1);
        }
    }


    /** {@inheritDoc} */
    @Override
    public void collidesWith(IDoodle doodle) {
        if (getProps().containsKey(PlatformProperties.breaks)) {
            if ((int) getProps().get(PlatformProperties.breaks) == 1) {
                getProps().replace(PlatformProperties.breaks, 2);
                playBreakSound();
            }

        } else {
            this.playSound();
            doodle.collide(this);
        }
    }

    /**
     * Play the breaking sound for the Platform.
     */
    private void playBreakSound() {
        IAudioManager audioManager = sL.getAudioManager();
        audioManager.playLomise();
    }

    /**
     * Play the sound for the Platform.
     */
    private void playSound() {
        IAudioManager audioManager = sL.getAudioManager();
        audioManager.playJump();
    }

    /** {@inheritDoc} */
    @Override
    public Map getProps() {
        return props;
    }

    /** {@inheritDoc} */
    @Override
    public void setOffset(final int offSet) {
        this.offSet = offSet;
    }

    /** {@inheritDoc} */
    @Override
    public int getOffset() {
        return offSet;
    }

    /** {@inheritDoc} */
    @Override
    public ISprite getBrokenSprite(final int numberOfAnimation) {
        if (numberOfAnimation == 2) {
            getProps().replace(PlatformProperties.breaks, 3);
            return sL.getSpriteFactory().getPlatformBrokenSprite2();
        } else if (numberOfAnimation == 3) {
            getProps().replace(PlatformProperties.breaks, 4);
            return sL.getSpriteFactory().getPlatformBrokenSprite3();
        } else if (numberOfAnimation == 4) {
            getProps().replace(PlatformProperties.breaks, -1);
            return sL.getSpriteFactory().getPlatformBrokenSprite4();
        } else {
            return getSprite();
        }
    }

}
