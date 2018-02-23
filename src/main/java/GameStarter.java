import game.Ball;
import game.GameField;
import game.Player;
import java.util.HashSet;
import java.util.Set;

public class GameStarter  {
    public static void main(String[] args) {
        Ball mustaineBall = new Ball(0, 1);
        Ball friedmanBall = new Ball(0, 0);
        Ball menzaBall = new Ball(1, 1);
        Ball ellefsonBall = new Ball(1, 0);
        Set<Ball> balls = new HashSet<Ball>();
        balls.add(mustaineBall);
        balls.add(friedmanBall);
        balls.add(menzaBall);
        balls.add(ellefsonBall);
        GameField gameField = new GameField(10, 10, balls);
        Player player = new Player(mustaineBall, gameField, "Dave Mustaine");
        Player player1 = new Player(friedmanBall, gameField, "Marty Friedman");
        Player player2 = new Player(menzaBall, gameField, "Nick Menza");
        Player player3 = new Player(ellefsonBall, gameField, "David  Ellefson");
    }
}
