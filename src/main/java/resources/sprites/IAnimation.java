package resources.sprites;

/**
 * Defines an animation.
 */
public interface IAnimation {
    /**
     * Get a frame from the animation.
     * @param index The index of the frame of the animation
     * @return The frame of the animation
     */
    ISprite getFromIndex(int index);

    /**
     * @return The amount of frames of the animation
     */
    int getLength();
}
