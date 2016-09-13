package scenes;

import input.IMouseInputObserver;
import objects.blocks.Block;
import objects.blocks.IBlock;
import objects.blocks.IBlockFactory;
import objects.IGameObject;
import objects.buttons.IButton;
import objects.doodles.Doodle;
import objects.doodles.IDoodle;
import objects.doodles.IDoodleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rendering.IDrawable;
import resources.sprites.ISprite;
import system.Game;
import system.IServiceLocator;

import java.util.*;

public class World implements IScene {

    private final IServiceLocator serviceLocator;
    private final Set<IGameObject> elements = new HashSet<>();
    private final IDrawable background;

    private final Scorebar scorebar;

    private final IDoodle doodle;

    // The vertical speed, negative if going up and positive if going down.
    private double vSpeed = -9;
    // The fastest the doodle can go vertically.
    private double vSpeedLimit = 9;
    // How much the doodle is affected by gravity.
    private double gravityAcceleration = .15;

    private final static double SCOREMULTIPLIER = 0.015;

    private double score;

    /* package */ World(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

        IBlockFactory blockFactory = serviceLocator.getBlockFactory();
        elements.add(blockFactory.createStartBlock());
        for(int i = 1; i < 3; i++) {
            elements.add(blockFactory.createBlock());
        }

        background = serviceLocator.getBackgroundFactory().createBackground();

        scorebar = new Scorebar();

        IDoodleFactory doodleFactory = serviceLocator.getDoodleFactory();
        this.doodle = doodleFactory.createDoodle();
        this.vSpeed = -9;
    }

    @Override
    /** {@inheritDoc} */
    public void start() { }

    @Override
    /** {@inheritDoc} */
    public void stop() { }

    @Override
    public void paint() {
        background.paint();

        for (IGameObject e : elements) {
            e.paint();
        }

        doodle.paint();

        scorebar.render();
    }

    @Override
    public void update(double delta) {

        updateObjects();
        updateSpeed();
        applySpeed();
        cleanUp();

        newBlocks();
    }

    private void updateSpeed(){
        for(IGameObject e : elements) {
            IBlock block = (IBlock) e;
            HashSet<IGameObject> inside = block.getContent();
            for(IGameObject item : inside) {
                if (vSpeed > 0 && this.doodle.collide(item)){
                    vSpeed = -vSpeedLimit;
                }
            }
        }
        this.applyGravity();
    }

    private void applySpeed(){
        if(this.vSpeed < 0 && doodle.getYPos() < .5d * Game.HEIGHT) {
            for(IGameObject e : elements) {
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
        if(this.vSpeed >= -vSpeedLimit) {
            this.vSpeed += this.gravityAcceleration;
        }
    }

    private void updateObjects(){
        for(IGameObject e: elements){
            e.update();
        }
        doodle.update();
    }

    private void cleanUp(){
        for(IGameObject e : elements) {
            if(e.getClass().equals(Doodle.class)){
                if(e.getYPos() > Game.HEIGHT) {
                    elements.remove(e);
                }
            }
        }
    }

    private void newBlocks(){
        if (elements.size() < 4) {
            double minY = Double.MAX_VALUE;
            for(IGameObject e : elements) {
                if(e.getYPos() < minY){
                    minY = e.getYPos();
                }
            }
            //TODO: implements New Block
            elements.add(serviceLocator.getBlockFactory().createBlock());
        }
    }

    /**
     * IMMUTABLE
     *
     * The bar on top of the screen displaying the score and pause button
     */
    private final class Scorebar {
        private final double scaling;

        private final ISprite scoreBarSprite;
        private final int scoreBarHeight;

        private final PauseButton pauseButton;
        private final ScoreText scoreText;

        private final Logger logger = LoggerFactory.getLogger(Scorebar.class);


        /** The transparant and black border at the bottom of the scoreBar that is not take into account when
         * drawing scoreBar content.
         */
        private static final int SCOREBARDEADZONE = 28;

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

            ISprite pauseSprite = serviceLocator.getSpriteFactory().getPauseSprite();
            int pauseX = (int) (Game.WIDTH - 2 * pauseSprite.getWidth() * scaling);
            int pauseY = (int) (((double) Game.WIDTH / (double) scoreBarSprite.getWidth()) * (scoreBarSprite.getHeight() - SCOREBARDEADZONE) / 2 - pauseSprite.getHeight() / 2);
            pauseButton = new PauseButton(pauseX, pauseY, scaling, pauseSprite);

            serviceLocator.getInputManager().addObserver(pauseButton);
        }

        private void render() {
            serviceLocator.getRenderer().drawSprite(scoreBarSprite, 0, 0, Game.WIDTH, scoreBarHeight);
            scoreText.paint();
            pauseButton.paint();
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
            public void paint() {
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
            /** We use an array so that we get very good cache prediction and a 4 times smaller size than width
             * seperate (integer) variables.
             * </br>
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
            public void paint() {
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
