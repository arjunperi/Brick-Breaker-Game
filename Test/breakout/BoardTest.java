package breakout;


import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;


class BoardTest extends DukeApplicationTest {


    private final Board myBoard = new Board();
    private Scene myScene;

    private Rectangle brick;
    private Rectangle paddle;
    private Ball myBall;


    @Override
    public void start(Stage stage) {
        myScene = myBoard.setupScene(Board.SIZE, Board.SIZE, Board.BACKGROUND);
        stage.setScene(myScene);
        stage.show();


        brick = lookup("#brick").query();
        paddle = lookup("#paddle").query();

        //this is giving an error for some reason?
        //myBall = lookup("#ball").query();

    }

    @Test
    public void testPaddleInitSize() {
        assertEquals(75, paddle.getWidth());
        assertEquals(10, paddle.getHeight());
    }

    @Test
    public void testPaddleInitPosition() {
        assertEquals(400 / 2 - 75 / 2, paddle.getX());
        assertEquals(400 - 10, paddle.getY());
    }

    @Test
    public void testPaddleMovement() {
        paddle.setX(150);
        paddle.setY(390);
        press(myScene, KeyCode.RIGHT);
        assertEquals(160, paddle.getX());
        assertEquals(390, paddle.getY());
        press(myScene, KeyCode.LEFT);
        assertEquals(150, paddle.getX());
        assertEquals(390, paddle.getY());
    }
}