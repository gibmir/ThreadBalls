package game;

import java.util.Set;

/**
 * Represent the playing field on which the game is played(sorry for tautology)<br>
 * the game field has the following fields:<br>
 * size of the field by abscissa(width of game field)<br>
 * size of the field by ordinate(height of game field)<br>
 * set of the balls(contains balls which take part in the game)
 */
public class GameField {
    /**
     * size of the field by abscissa(width of game field)
     */
    private final int sizeX;
    /**
     * size of the field by ordinate(height of game field)
     */
    private final int sizeY;
    /**
     * set of the balls(contains balls which take part in the game)
     */
    private Set<Ball> balls;

    /**
     * Creates new game field
     *
     * @param sizeX width of game field
     * @param sizeY height of game field
     * @param balls set of the balls
     */
    public GameField(int sizeX, int sizeY, Set<Ball> balls) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.balls = balls;
    }

    /**
     * Getter for {@link GameField#sizeX}
     *
     * @return width of game field
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Getter for {@link GameField#sizeY}
     *
     * @return height of game field
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Getter for {@link GameField#balls}
     *
     * @return set of the balls
     */
    public Set<Ball> getBalls() {
        return balls;
    }
}
