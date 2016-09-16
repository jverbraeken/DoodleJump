package scenes;

import objects.buttons.IButton;
import objects.buttons.IButtonFactory;
import resources.sprites.ISprite;
import resources.sprites.ISpriteFactory;
import system.Game;
import system.IServiceLocator;

public class Menu implements IScene {

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
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        serviceLocator.getInputManager().removeObserver(playButton);
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

}
