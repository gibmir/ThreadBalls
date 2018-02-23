package game;

import java.util.Random;

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
    private Ball playerBall;
    /**
     * the field on which the player plays
     */
    private GameField gameField;
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
     * @see Player#isSurrounded()
     */
    private static final int MAXIMUM_NEAREST_NEIGHBOR_COUNT = 4;

    /**
     * Creates new player and starts its thread
     *
     * @param playerBall the ball played by a player
     * @param gameField  the field on which the player plays
     * @param playerName name of the player
     */
    public Player(Ball playerBall, GameField gameField, String playerName) {
        this.playerBall = playerBall;
        this.gameField = gameField;
        this.playerName = playerName;
        this.playerSpeed = /*1000*/ playerRandom.nextInt(5000 - 1000);
        new Thread(this).start();
    }

    /**
     * Checks whether the player's ball is surrounded on 4 sides
     *
     * @return true if the player's ball is surrounded on 4 sides
     * @see GameField#balls
     */
    private boolean isSurrounded() {
        int nearestNeighbor = 0;
        int x = playerBall.getPositionX();
        int y = playerBall.getPositionY();

        for (Ball ball : gameField.getBalls()) {

            //check neighbors by abscissa
            if (ball.getPositionX() == x) {
                if ((ball.getPositionY() == y + 1) || (ball.getPositionY() == y - 1)) {
                    nearestNeighbor++;
                }
            }
            //check neighbors by ordinate
            if (ball.getPositionY() == y) {
                if ((ball.getPositionX() == x + 1) || (ball.getPositionX() == x - 1)) {
                    nearestNeighbor++;
                }
            }

        }
        //abscissa border check
        if ((x == 0) || (x == gameField.getSizeX())) {
            nearestNeighbor++;
        }
        //ordinate border check
        if ((y == 0) || (y == gameField.getSizeY())) {
            nearestNeighbor++;
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

        int neighborsCount = 0;
        //checks if specified direction does not exceed the field limits
        if ((step.getPositionX() >= gameField.getSizeX()) || (step.getPositionX() < 0) || (step.getPositionY() >= gameField.getSizeY()) || (step.getPositionY() < 0)) {
            return true;
        }
        for (Ball ball : gameField.getBalls()) {
            //checks if specified direction is incorrect(if player stepped on the ball)
            if ((Math.abs(ball.getPositionX() - step.getPositionX()) == 0) && (Math.abs(ball.getPositionY() - step.getPositionY()) == 0)) {
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
     * @see Player#isSurrounded()
     * @see Player#isMove(Ball)
     */
    private void move() {
        synchronized (gameField) {
            Ball step = new Ball(playerBall.getPositionX(), playerBall.getPositionY());
            do {
                //if the player's ball is surrounded on 4 sides - can't touch him
                if (isSurrounded()) {
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
            gameField.getBalls().remove(playerBall);
            playerBall.setPositionX(step.getPositionX());
            playerBall.setPositionY(step.getPositionY());
            gameField.getBalls().add(playerBall);
            print();
        }
    }

    /**
     * Prints change the game field to the console
     */
    private void print() {
        int sizeX = gameField.getSizeX();
        int sizeY = gameField.getSizeY();
        char[][] gameField = new char[sizeX][sizeY];
        //fill the array with values
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                gameField[i][j] = '#';
            }
        }
        //place balls
        for (Ball ball : this.gameField.getBalls()) {
            gameField[ball.getPositionX()][ball.getPositionY()] = '*';
        }
        StringBuilder stringBuilder = new StringBuilder();
        //form output string
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[i].length; j++) {
                stringBuilder.append(gameField[i][j]);
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder.toString());
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(playerSpeed);
                move();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
