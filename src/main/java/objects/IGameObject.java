package objects;

import rendering.IDrawable;

/**
 * The interface implemented by {@link AGameObject}, the super class of all classes that represents objects in the game.
 */
public interface IGameObject extends IDrawable {

    /**
     * Add the amount xPos to the x position of the object.
     * @param xPos the amount of x that has to be added.
     */
    void addXPos(int xPos);

    /**
     * Add the amount yPos to the y position of the object.
     * @param yPos the amount of y that has to be added.
     */
    void addYPos(int yPos);

    /**
     * Make the sprite of the object animate.
     */
    void animate();

    /**
     * Returns true if the Object collides with the collidee object.
     * @param collidee the object that has to be checked for collision.
     */
    void collide(IGameObject collidee);

    /**
     * Returns the sprite of the object.
     * @return the sprite of the object.
     */
    Object getSprite();

    /**
     * Returns the hitbox of the object.
     * @return the hitbox of the object.
     */
    double[] getHitBox();

    /**
     * Returns the height of the object.
     * @return the height of the object.
     */
    int getHeight();

    /**
     * Returns the width of the object.
     * @return the width of the object.
     */
    int getWidth();

    /**
     * Returns the xPos of the object.
     * @return the xPos of the object.
     */
    int getXPos();

    /**
     * Returns the yPos of the object.
     * @return the yPos of the object.
     */
    int getYPos();

    void move();

    void paint();

    /**
     * Sets the height of the object.
     * @param height what has to be set in the object.
     */
    void setHeight(int height);

    /**
     * Sets the width of the object.
     * @param width what has to be set in the object.
     */
    void setWidth(int width);

    /**
     * Sets the xPos of the object.
     * @param xPos what has to be set in the object.
     */
    void setXPos(int xPos);

    /**
     * Sets the yPos of the object.
     * @param yPos what has to be set in the object.
     */
    void setYPos(int yPos);

    void update();
}
