package objects.doodles;

import input.IKeyInputObserver;
import objects.IGameObject;
import objects.IJumpable;
import objects.blocks.IBlock;

public interface IDoodle extends IGameObject, IKeyInputObserver {

    double getVSpeed();

    void collide(IJumpable jumpable);

    void collide(IBlock block);

    /**
     * Returns the height of the legs of the doodle. When this value is very large, for example 1,
     * the doodle can jump on a platform if it only hits it with its head.
     * @return The height of the legs of the doodle
     */
    double getLegsHeight();

    /**
     * Set the vertical speed of the Doodle.
     */
    void setVerticalSpeed(double vSpeed);

    /**
     * @return The score of the doodle
     */
    double getScore();

    // Enum with directions for the Doodle.
    enum directions { left, right };

}
