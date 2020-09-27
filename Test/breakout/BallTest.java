package breakout;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;

class BallTest extends DukeApplicationTest {


    private final BreakoutGame myBreakoutGame = new BreakoutGame();
    private Scene myScene;
    private Ball myBall;
    private Display myDisplay;
    private Stage myStage;



    @Override
    public void start(Stage stage) {
        myScene = myBreakoutGame.setupScene(1, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        myStage = stage;
        myStage.setScene(myScene);
        myStage.show();
        myBall = lookup("#ball").query();
        myDisplay = lookup("#display").query();
    }

    @Test
    public void testBallInitPosition() {
        assertEquals(myBreakoutGame.SIZE / 2, myBall.getCenterX());
        assertEquals(myBreakoutGame.SIZE - Paddle.PADDLE_HEIGHT - myBall.getRadius(), myBall.getCenterY());
    }

    @Test
    public void testBallInitSize() {
        assertEquals(7, myBall.getRadius());
    }

    @Test
    public void testBallInitVelocity() {
        assertEquals(0, myBall.getSpeed());
    }

    @Test
    public void testBallVelocityAfterStart() {
        press(myScene, KeyCode.S);

        assertEquals(150, myBall.getSpeed());
    }

    @Test
    public void testBallBounceOffCorner() {
        myBall.setCenterX(0);
        myBall.setCenterY(0);
        myBall.setXDirection(-1);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(1, myBall.getXDirection());
        assertEquals(1, myBall.getYDirection());

    }

    @Test
    public void testBallBounceOffPaddle() {
        myBall.setCenterX(BreakoutGame.SIZE / 2);
        myBall.setCenterY(BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT - myBall.getRadius() - 1);
        myBall.setXDirection(0);
        myBall.setYDirection(1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        assertEquals(0, myBall.getXDirection());
        assertEquals(-1, myBall.getYDirection());

    }

    @Test
    public void testBallReset() {
        myBall.setCenterX(0);
        myBall.setCenterY(BreakoutGame.SIZE);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay.stats();
        assertEquals(400 / 2, myBall.getCenterX());
        assertEquals(400 - 60, myBall.getCenterY());
        assertEquals(2,myBall.getGameLives());

    }

    @Test
    public void testBallBounceOffBrickY(){
        myBall.setCenterX(0);
        myBall.setCenterY(160 + myBall.getRadius() / 2);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(1, myBall.getYDirection());
    }

    @Test
    public void testBallBounceOffBrickX(){
        myBall.setCenterX(Brick.BRICK_WIDTH - myBall.getRadius());
        myBall.setCenterY(myBall.getRadius());
        myBall.setXDirection(1);
        myBall.setYDirection(0);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(-1, myBall.getXDirection());
    }


}