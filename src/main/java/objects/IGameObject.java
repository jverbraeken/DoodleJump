package objects;

import rendering.IDrawable;

/**
 * The interface implemented by {@link AGameObject}, the super class of all classes that represents objects in the game.
 */
public interface IGameObject extends IDrawable {

    /**
     * Change the x position of the game object.
     * @param xPos the amount to add.
     */
    void addXPos(double xPos);

    /**
     * Change the y position of the game object.
     * @param yPos the amount to add.
     */
    void addYPos(double yPos);

    /**
     * Animate the game object.
     */
    void animate();

    /**
     * Retrieve the amount of boost the game object gives.
     * @return the amount of boost.
     */
    double getBoost();

    /**
     * Retrieve the sprite of the game object.
     * @return the sprite.
     */
    Object getSprite();

    /**
     * Retrieve the hitbox of the game object.
     * @return the hitbox.
     */
    double[] getHitBox();

    /**
     * Set the hitbox of the game object.
     * @param hitbox the hitbox.
     */
    void setHitBox(double[] hitbox);

    /**
     * Retrieve the height of the game object.
     * @return the height
     */
    int getHeight();

    /**
     * Set the height of the game object.
     * @param height the to be height.
     */
    void setHeight(int height);

    /**
     * Retrieve the width of the game object.
     * @return the width.
     */
    int getWidth();

    /**
     * Set the width of the game object.
     * @param width the to be width.
     */
    void setWidth(int width);

    /**
     * Retrieve the x position of the game object.
     * @return the x position.
     */
    double getXPos();

    /**
     * Set the x position of the game object.
     * @param xPos the to be x position.
     */
    void setXPos(double xPos);

    /**
     * Retrieve the y position of the game object.
     * @return the y position.
     */
    double getYPos();

    /**
     * Set the y position of the game object.
     * @param yPos the to be y position.
     */
    void setYPos(double yPos);

    /**
     * Move the game object.
     */
    void move();

    /**
     * Render the sprite of the game object.
     */
    void render();

    /**
     * Update the game object.
     */
    void update();

}
