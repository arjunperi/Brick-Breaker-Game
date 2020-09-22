package breakout;


import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;


class BallTest extends DukeApplicationTest {


    private final BreakoutGame myBreakoutGame = new BreakoutGame();
    private Scene myScene;

    private Ball myBall;


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        myScene = myBreakoutGame.setupScene(BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        stage.setScene(myScene);
        stage.show();

        myBall = lookup("#ball").query();


    }

    @Test
    public void testBallInitPosition() {
        assertEquals(400 / 2, myBall.getCenterX());
        assertEquals(400 - 60, myBall.getCenterY());
    }

    @Test
    public void testBallInitSize() {
        assertEquals(7, myBall.getRadius());
    }

    @Test
    public void testBallInitVelocity() {
        assertEquals(0, myBall.getBALL_SPEED());
    }

    @Test
    public void testBallVelocityAfterStart() {
        press(myScene, KeyCode.S);

        assertEquals(150, myBall.getBALL_SPEED());
    }

    @Test
    public void testBallBounceOffCorner() {
        myBall.setCenterX(0);
        myBall.setCenterY(0);
        myBall.setX_DIRECTION(-1);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        myBreakoutGame.step(BreakoutGame.SECOND_DELAY);
        assertEquals(1, myBall.getX_DIRECTION());
        assertEquals(1, myBall.getY_DIRECTION());

    }

    @Test
    public void testBallBounceOffPaddle() {
        myBall.setCenterX(BreakoutGame.SIZE / 2);
        myBall.setCenterY(BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT - myBall.getRadius());
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        myBreakoutGame.step(BreakoutGame.SECOND_DELAY);
        assertEquals(0, myBall.getX_DIRECTION());
        assertEquals(1, myBall.getY_DIRECTION());

    }

    @Test
    public void testBallReset() {
        myBall.setCenterX(0);
        myBall.setCenterY(BreakoutGame.SIZE);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        myBreakoutGame.step(BreakoutGame.SECOND_DELAY);
        assertEquals(400 / 2, myBall.getCenterX());
        assertEquals(400 - 60, myBall.getCenterY());

    }

    @Test
    public void testBallBounceOffBrickY(){
        myBall.setCenterX(0);
        myBall.setCenterY(160 + myBall.getRadius() / 2);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(1, myBall.getY_DIRECTION());
    }

    @Test
    public void testBallBounceOffBrickX(){
        myBall.setCenterX(300 + myBall.getRadius() / 2);
        myBall.setCenterY(150);
        myBall.setX_DIRECTION(-1);
        myBall.setY_DIRECTION(0);
        myBall.startBall(150);
        myBreakoutGame.step(BreakoutGame.SECOND_DELAY);
        assertEquals(1, myBall.getX_DIRECTION());
    }


}