package breakout;


import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;

@Ignore
class PaddleTest extends DukeApplicationTest {


    private final BreakoutGame myBreakoutGame = new BreakoutGame();
    private Scene myScene;
    private Paddle myPaddle;

    PaddleTest() throws FileNotFoundException {
    }


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        myScene = myBreakoutGame.setupScene(BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        stage.setScene(myScene);
        stage.show();

        myPaddle = lookup("#paddle").query();

    }

    @Test
    public void testPaddleInitSize() {
        assertEquals(75, myPaddle.getWidth());
        assertEquals(10, myPaddle.getHeight());
    }

    @Test
    public void testPaddleInitPosition() {
        assertEquals(400 / 2 - 75 / 2, myPaddle.getX());
        assertEquals(400 - 10, myPaddle.getY());
    }

    @Test
    public void testPaddleMovement() {
        myPaddle.setX(150);
        myPaddle.setY(390);
        press(myScene, KeyCode.RIGHT);
        assertEquals(160, myPaddle.getX());
        assertEquals(390, myPaddle.getY());
        press(myScene, KeyCode.LEFT);
        assertEquals(150, myPaddle.getX());
        assertEquals(390, myPaddle.getY());
    }

    @Test
    public void testPaddleReset() {
        myPaddle.setX(0);
        press(myScene, KeyCode.R);
        assertEquals(BreakoutGame.SIZE / 2 - Paddle.PADDLE_WIDTH / 2, myPaddle.getX());
        assertEquals(BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT, myPaddle.getY());

    }
}