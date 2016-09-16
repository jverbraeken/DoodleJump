package scenes;

import input.IKeyInputObserver;
import input.KeyCode;
import input.Keys;
import objects.buttons.IButton;
import objects.buttons.IButtonFactory;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

public class Menu implements IScene, IKeyInputObserver {

    private final IServiceLocator serviceLocator;

    private final IButton playButton;
    private final ISprite cover;
    private static final double playButtonXPercentage = 0.15;
    private static final double playButtonYPercentage = 0.25;

    /* package */ Menu(IServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;

        ISpriteFactory spriteFactory = serviceLocator.getSpriteFactory();
        cover = spriteFactory.getStartCoverSprite();

        IButtonFactory buttonFactory = serviceLocator.getButtonFactory();
        playButton = buttonFactory.createPlayButton((int) (Game.WIDTH * playButtonXPercentage), (int) (Game.HEIGHT * playButtonYPercentage));
    }

    /** {@inheritDoc} */
    @Override
    public void start() {
        serviceLocator.getInputManager().addObserver(playButton);
        serviceLocator.getInputManager().addObserver(this);
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        serviceLocator.getInputManager().removeObserver(playButton);
        serviceLocator.getInputManager().removeObserver(this);
    }

    /** {@inheritDoc} */
    @Override
    public void paint() {
        serviceLocator.getRenderer().drawSprite(this.cover, 0, 0);
        playButton.render();
    }

    /** {@inheritDoc} */
    @Override
    public void update(double delta) { }

    /** {@inheritDoc} */
    @Override
    public void keyPress(int keyCode) { }

    /** {@inheritDoc} */
    @Override
    public void keyRelease(int keyCode) {
        if (KeyCode.getKeyCode(Keys.enter) == keyCode || KeyCode.getKeyCode(Keys.space) == keyCode) {
            // TODO: Start game
        }
    }
}
