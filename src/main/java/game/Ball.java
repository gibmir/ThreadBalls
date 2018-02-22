package game;

/**
 * Represent the game object <b>ball</b> <br>
 * the ball has the following fields: <b>abscissa</b> and <b>ordinate</b>
 */
public class Ball {
    /**
     * abscissa coordinate of the ball
     */
    private int positionX;
    /**
     * ordinate coordinate of the ball
     */
    private int positionY;

    /**
     * Creates new ball without coordinates
     *
     * @see Ball#Ball(int, int)
     */
    public Ball() {
    }

    /**
     * Creates new ball with coordinates
     *
     * @param positionX abscissa
     * @param positionY ordinate
     * @see Ball#Ball()
     */
    public Ball(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    /**
     * Getter for {@link Ball#positionX}
     *
     * @return abscissa coordinate of the ball
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Setter for {@link Ball#positionX}
     *
     * @param positionX abscissa
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Getter for {@link Ball#positionY}
     *
     * @return ordinate coordinate of the ball
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Setter for {@link Ball#positionY}
     *
     * @param positionY ordinate
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
