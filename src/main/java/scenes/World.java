package scenes;

import logging.ILogger;
import objects.IGameObject;
import objects.blocks.IBlock;
import objects.blocks.IBlockFactory;
import objects.buttons.IButton;
import objects.doodles.Doodle;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import rendering.IDrawable;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class World implements IScene {

    // TODO: Add JavaDoc
    private final static double SCOREMULTIPLIER = 0.15;
    private final static int PAUSEOFFSET = 38;
    private final IServiceLocator serviceLocator;
    private final ILogger logger;

    /**
     * The fastest the doodle can go vertically.
     */
    public final static double vSpeedLimit = 20;
    /**
     * Set of all object (excluding Doodle) in the world.
     */
    private final Set<IGameObject> elements = new HashSet<>();
    /**
     * The background of the world.
     */
    private final ISprite background;
    /**
     * The Doodle for the world.
     */
    private final IDoodle doodle;
    /**
     * The scorebar for the world.
     */
    private final Scorebar scorebar;
    /**
     * The vertical speed, negative if going up and positive if going down.
     */
    private double vSpeed = -20;
    /**
     * How much the doodle is affected by gravity.
     */
    public static final double gravityAcceleration = .5;
    /**
     * The score for the world.
     */
    private double score;

    /* package */ World(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
        this.logger = serviceLocator.getLogger();

        Game.setAlive(true);

        IBlockFactory blockFactory = serviceLocator.getBlockFactory();
        IBlock lastCreatedBlock = blockFactory.createStartBlock();
        elements.add(lastCreatedBlock);

        for (int i = 1; i < 3; i++) {
            lastCreatedBlock = blockFactory.createBlock(getTopObject());
            elements.add(lastCreatedBlock);
        }

        background = serviceLocator.getSpriteFactory().getBackground();

        scorebar = new Scorebar();

        IDoodleFactory doodleFactory = serviceLocator.getDoodleFactory();
        this.doodle = doodleFactory.createDoodle();
        this.vSpeed = -9;

        serviceLocator.getLogger().log("Level started");
    }

    /** {@inheritDoc} */
    @Override
    public void start() { }

    /** {@inheritDoc} */
    @Override
    public void stop() { }

    /** {@inheritDoc} */
    @Override
    public void paint() {
        serviceLocator.getRenderer().drawSprite(this.background, 0, 0);

        for (IGameObject e : elements) {
            e.render();
        }

        this.doodle.render();

        scorebar.render();
    }

    /** {@inheritDoc} */
    @Override
    public void update(double delta) {
        updateSpeed();
        updateObjects();
        applySpeed();
        cleanUp();

        // TODO: check if doodle is alive

        newBlocks();
        Game.setAlive(doodle.getYPos() < Game.HEIGHT);
    }

    /**
     * TODO: Add JavaDoc
     */
    private void updateSpeed() {
        for (IGameObject e : elements) {
            IBlock block = (IBlock) e;
            ArrayList<IGameObject> inside = block.getContent();
            for (IGameObject item : inside) {
                if (vSpeed > 0 && this.doodle.collide(item)) {
                    vSpeed = item.getBoost();
                }
            }
        }

        this.applyGravity();

        this.doodle.setVerticalSpeed(vSpeed);
    }

    /**
     * TODO: Add JavaDoc
     */
    private void applySpeed() {
        if (this.vSpeed < 0 && doodle.getYPos() < .5d * Game.HEIGHT - doodle.getHeight()) {
            for (IGameObject e : elements) {
                e.addYPos(-this.vSpeed);
                score -= this.vSpeed * SCOREMULTIPLIER;
            }
        } else {
            doodle.addYPos(this.vSpeed);
        }
    }

    /**
     * Applies gravity vAcceleration to the doodle.
     */
    private void applyGravity() {
        this.vSpeed += this.gravityAcceleration;
    }

    /**
     * TODO: Add JavaDoc
     */
    private void updateObjects() {
        for (IGameObject e : elements) {
            e.update();
        }

        doodle.update();
    }

    /**
     * Checks for all the Blocks if they are under over the height
     * of the screen, if that's the case, delete that Block.
     */
    private void cleanUp() {
        HashSet<IGameObject> toRemove = new HashSet<>();
        for (IGameObject e : elements) {
            if (e.getClass().equals(Doodle.class)) {
                if (e.getYPos() > Game.HEIGHT) {
                    toRemove.add(e);
                }
            } else if (e instanceof IBlock) {
                if (e.getYPos() + Game.HEIGHT * 0.01  > Game.HEIGHT) {
                    toRemove.add(e);
                }
            }
        }

        for (IGameObject e : toRemove) {
            elements.remove(e);
        }
    }

    /**
     * TODO: Add JavaDoc
     */
    private void newBlocks() {
        if (elements.size() < 3) {
            double minY = Double.MAX_VALUE;
            for (IGameObject e : elements) {
                if (e.getYPos() < minY) {
                    minY = e.getYPos();
                }
            }
            IGameObject topObject = getTopObject();
            //TODO: implements New Block
            elements.add(serviceLocator.getBlockFactory().createBlock(topObject));
        }
    }

    /**
     * TODO: Add JavaDoc
     */
    private IGameObject getTopObject() {
        IBlock topBlock = (IBlock) elements.iterator().next();
        for (IGameObject e : elements) {
            if (e.getYPos() < topBlock.getYPos()) {
                topBlock = (IBlock) e;
            }
        }
        ArrayList<IGameObject> arr = topBlock.getContent();
        IGameObject topObject = arr.get(arr.size() - 1);

        return topObject;
    }

    /**
     * IMMUTABLE
     * <p>
     * The bar on top of the screen displaying the score and pause button
     */
    private final class Scorebar {
        /**
         * The transparant and black border at the bottom of the scoreBar that is not take into account when
         * drawing scoreBar content.
         */
        private static final int SCOREBARDEADZONE = 28;
        private final double scaling;
        private final ISprite scoreBarSprite;
        private final int scoreBarHeight;
        private final PauseButton pauseButton;
        private final ScoreText scoreText;
        private final ILogger logger = serviceLocator.getLogger();

        private Scorebar() {
            scoreBarSprite = serviceLocator.getSpriteFactory().getScorebarSprite();
            scaling = (double) Game.WIDTH / (double) scoreBarSprite.getWidth();
            scoreBarHeight = (int) (scaling * scoreBarSprite.getHeight());

            ISprite[] digitSprites = new ISprite[10];
            for (int i = 0; i < 10; i++) {
                digitSprites[i] = serviceLocator.getSpriteFactory().getDigitSprite(i);
            }
            int scoreX = (int) (digitSprites[2].getWidth() * scaling);
            int scoreY = (int) (scaling * (scoreBarSprite.getHeight() - SCOREBARDEADZONE) / 2);
            scoreText = new ScoreText(scoreX, scoreY, scaling, digitSprites);

            ISprite pauseSprite = serviceLocator.getSpriteFactory().getPauseButtonSprite();
            int pauseX = (int) (Game.WIDTH - pauseSprite.getWidth() * scaling - PAUSEOFFSET);
            int pauseY = (int) (((double) Game.WIDTH / (double) scoreBarSprite.getWidth()) * (scoreBarSprite.getHeight() - SCOREBARDEADZONE) / 2 - pauseSprite.getHeight() / 2);
            pauseButton = new PauseButton(pauseX, pauseY, scaling, pauseSprite);

            serviceLocator.getInputManager().addObserver(pauseButton);
        }

        private void render() {
            serviceLocator.getRenderer().drawSprite(scoreBarSprite, 0, 0, Game.WIDTH, scoreBarHeight);
            scoreText.render();
            pauseButton.render();
        }

        private class PauseButton implements IButton {
            private final int x, y, width, height;
            private final ISprite sprite;

            private PauseButton(int x, int y, double scaling, ISprite sprite) {
                this.x = x;
                this.y = y;
                this.width = (int) (sprite.getWidth() * scaling);
                this.height = (int) (sprite.getHeight() * scaling);
                this.sprite = sprite;
            }

            @Override
            public void render() {
                serviceLocator.getRenderer().drawSprite(sprite, x, y, width, height);
            }

            @Override
            public void mouseClicked(int mouseX, int mouseY) {
                if (mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height) {
                    logger.info("Pause button was clicked!");
                    Game.setPaused(true);
                }
            }
        }

        private class ScoreText implements IDrawable {
            private final int x;
            private final ISprite[] digitSprites;
            /**
             * We use an array so that we get very good cache prediction and a 4 times smaller size than width
             * seperate (integer) variables.
             * <br>
             * [top-Y, width, height] for each of the 10 entries -> total length = 30
             */
            private final byte[] digitData;

            private ScoreText(int x, int yCenter, double scaling, ISprite[] digitSprites) {
                assert digitSprites.length == 10;
                this.x = x;
                digitData = new byte[30];
                for (int i = 0; i < 10; i++) {
                    digitData[i * 3] = (byte) (yCenter - digitSprites[i].getHeight() / 2);
                    digitData[i * 3 + 1] = (byte) (digitSprites[i].getWidth() * scaling);
                    digitData[i * 3 + 2] = (byte) (digitSprites[i].getHeight() * scaling);
                }
                this.digitSprites = digitSprites;
            }

            @Override
            public void render() {
                int roundedScore = (int) score;
                int digit;
                Stack<Integer> scoreDigits = new Stack<>();
                while (roundedScore != 0) {
                    digit = roundedScore % 10;
                    roundedScore = roundedScore / 10;
                    scoreDigits.push(digit);
                }

                int pos = x;
                ISprite sprite;
                while (!scoreDigits.isEmpty()) {
                    digit = scoreDigits.pop();
                    sprite = digitSprites[digit];
                    serviceLocator.getRenderer().drawSprite(sprite, pos, digitData[digit * 3], digitData[digit * 3 + 1], digitData[digit * 3 + 2]);
                    pos += digitData[digit * 3 + 1] + 1;
                }
            }
        }
    }

}
