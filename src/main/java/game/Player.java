package game;

import java.util.Random;
import java.util.Set;

/**
 * Class-thread represent game player.<br>
 * the player has the following fields:<br>
 * - the ball played by a player<br>
 * - the field on which the player plays<br>
 * - name of the player<br>
 * - reaction speed of player<br>
 * - random player actions
 */
public class Player implements Runnable {
    /**
     * the ball played by a player
     */
    private volatile Ball playerBall;
    /**
     * the field on which the player plays
     */
    private volatile GameField gameField;
    /**
     * name of the player
     */
    private final String playerName;
    /**
     * reaction speed of player
     */
    private long playerSpeed;
    /**
     * random player actions
     */
    private final Random playerRandom = new Random();
    /**
     * Count of maximum nearest neighbors of the ball
     *
     * @see Player#isSurrounded(Ball, Set)
     */
    private static final int MAXIMUM_NEAREST_NEIGHBOR_COUNT = 4;

    public Player(Ball playerBall, GameField gameField, String playerName) {
        this.playerBall = playerBall;
        this.gameField = gameField;
        this.playerName = playerName;
        this.playerSpeed = playerRandom.nextInt(5000-1000);
        new Thread(this).start();
    }

    /**
     * Creates new player and starts its thread
     *
     * @param playerBall  the ball played by a player
     * @param gameField   the field on which the player plays
     * @param playerName  name of the player
     * @param playerSpeed reaction speed of player
     */
    public Player(Ball playerBall, GameField gameField, String playerName, long playerSpeed) {
        this.playerBall = playerBall;
        this.gameField = gameField;
        this.playerName = playerName;
        this.playerSpeed = playerSpeed;
        new Thread(this).start();
    }

    /**
     * Checks whether the player's ball is surrounded on 4 sides
     *
     * @param playerBall the ball played by a player
     * @param balls      set of the balls
     * @return true if the player's ball is surrounded on 4 sides
     * @see GameField#balls
     */
    private synchronized boolean isSurrounded(Ball playerBall, Set<Ball> balls) {
        int nearestNeighbor = 0;
        balls.remove(playerBall);
        for (Ball ball : balls) {
            //check neighbors by abscissa
            if (ball.getPositionX() == playerBall.getPositionX()) {
                if ((ball.getPositionY() == playerBall.getPositionY() + 1) || (ball.getPositionY() == playerBall.getPositionY() - 1)) {
                    nearestNeighbor++;
                }
            }
            //check neighbors by ordinate
            if (ball.getPositionY() == playerBall.getPositionY()) {
                if ((ball.getPositionX() == playerBall.getPositionX() + 1) || (ball.getPositionX() == playerBall.getPositionX() - 1)) {
                    nearestNeighbor++;
                }
            }
        }
        //this condition means that ball is surrounded by other balls on 4 sides
        if (nearestNeighbor == MAXIMUM_NEAREST_NEIGHBOR_COUNT) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Check if player can move the ball in the specified direction
     *
     * @param step ball with new specified direction
     * @return false if player can move the ball in the specified direction
     */
    private boolean isMove(Ball step) {
        Set<Ball> balls = gameField.getBalls();
        balls.remove(playerBall);
        int neighborsCount = 0;
        //checks if specified direction does not exceed the field limits
        if ((step.getPositionX() > gameField.getSizeX()) || (step.getPositionX() < 0) || (step.getPositionY() > gameField.getSizeY()) || (step.getPositionY() < 0)) {
            return true;
        }
        for (Ball ball : balls) {
            //checks if specified direction is incorrect
            if ((Math.abs(ball.getPositionX() - step.getPositionX()) <= 1) && (Math.abs(ball.getPositionY() - step.getPositionY()) <= 1)) {
                neighborsCount++;
            }
        }
        if (neighborsCount == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Determines the movement of the ball by the player
     *
     * @see Player#isSurrounded(Ball, Set)
     * @see Player#isMove(Ball)
     */
    private void move() {
        Ball step = new Ball(playerBall.getPositionX(), playerBall.getPositionY());
        do {
            //if the player's ball is surrounded on 4 sides - can't touch him
            if (isSurrounded(playerBall, gameField.getBalls())) {
                break;
            }
            step.setPositionY(playerBall.getPositionY());
            step.setPositionX(playerBall.getPositionX());
            if (playerRandom.nextDouble() <= 0.25) {
                step.setPositionX(step.getPositionX() + 1);
            } else if (playerRandom.nextDouble() <= 0.5) {
                step.setPositionX(step.getPositionX() - 1);
            } else if (playerRandom.nextDouble() <= 0.75) {
                step.setPositionY(step.getPositionY() + 1);
            } else if (playerRandom.nextDouble() <= 1) {
                step.setPositionY(step.getPositionY() - 1);
            }
            //move is performed until there is find a direction in which the ball can be moved
        } while (isMove(step));
        playerBall.setPositionX(step.getPositionX());
        playerBall.setPositionY(step.getPositionY());
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(playerSpeed);
                move();
                System.out.printf("%s перекатывает свой шар на: \n x:%s y:%s \n", playerName, playerBall.getPositionX(), playerBall.getPositionY());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
