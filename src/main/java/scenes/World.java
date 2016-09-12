package scenes;

import input.IMouseInputObserver;
import objects.blocks.Block;
import objects.blocks.IBlock;
import objects.blocks.IBlockFactory;
import objects.IGameObject;
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
    private Set<IGameObject> elements = new HashSet<>();
    private final IDrawable background;

    private Scorebar scorebar;

    private IDoodle doodle;

    // The vertical speed, negative if going up and positive if going down.
    private double vSpeed = -9;
    // The fastest the doodle can go vertically.
    private double vSpeedLimit = 9;
    // How much the doodle is affected by gravity.
    private double gravityAcceleration = .15;

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

        for(IGameObject e : elements) {
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
            for(IGameObject e : elements)
            e.addYPos(-this.vSpeed);
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
    private final class Scorebar implements IMouseInputObserver {
        private final ISprite scoreBarSprite, pauseSprite;
        private final ISprite[] digitSprites;
        private final int scoreXOffset, YOffset;
        private final int pauseXOffsetFromRight, pauseClickRight, pauseClickBottom;
        private final Logger logger = LoggerFactory.getLogger(Scorebar.class);

        private Scorebar() {
            scoreBarSprite = serviceLocator.getSpriteFactory().getScorebarSprite();
            pauseSprite = serviceLocator.getSpriteFactory().getPauseSprite();
            digitSprites = new ISprite[10];
            for (int i = 0; i < 10; i++) {
                digitSprites[i] = serviceLocator.getSpriteFactory().getDigitSprite(i);
            }
            scoreXOffset = digitSprites[2].getWidth();
            YOffset = scoreBarSprite.getHeight() / 2 - digitSprites[1].getHeight() / 2;

            pauseXOffsetFromRight = Game.WIDTH - 2 * pauseSprite.getWidth();
            pauseClickRight = pauseXOffsetFromRight + pauseSprite.getWidth();
            pauseClickBottom = YOffset + pauseSprite.getHeight();

            serviceLocator.getInputManager().addObserver(this);
        }

        private void render() {
            renderBar();
            renderScore();
            renderPauseButton();
        }

        private void renderBar() {
            serviceLocator.getRenderer().drawSprite(scoreBarSprite, 0, 0, Game.WIDTH, Game.HEIGHT);
        }

        private void renderScore() {
            int roundedScore = (int) score;
            int digit;
            ArrayList<Integer> scoreDigits = new ArrayList<>(6);
            while (roundedScore != 0) {
                digit = roundedScore % 10;
                roundedScore = roundedScore / 10;
                scoreDigits.add(digit);
            }

            int pos = scoreXOffset;
            ISprite sprite;
            for (int i = scoreDigits.size()-1; i >= 0; i--) {
                sprite = digitSprites[scoreDigits.get(i)];
                serviceLocator.getRenderer().drawSprite(sprite, pos, YOffset);
                pos += sprite.getWidth() + 1;
            }
        }

        private void renderPauseButton() {
            serviceLocator.getRenderer().drawSprite(pauseSprite, pauseXOffsetFromRight, YOffset);
        }

        @Override
        public void mouseClicked(int x, int y) {
            if (x >= pauseXOffsetFromRight && x < pauseClickRight && y >= YOffset && y < pauseClickBottom) {
                logger.info("Pause button was clicked!");
            }
        }
    }
}
