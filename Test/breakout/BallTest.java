package breakout;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

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
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(1, myBall.getX_DIRECTION());
        assertEquals(1, myBall.getY_DIRECTION());

    }

    @Test
    public void testBallBounceOffPaddle() {
        myBall.setCenterX(BreakoutGame.SIZE / 2);
        myBall.setCenterY(BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT - myBall.getRadius() - 1);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(1);
        myBall.startBall(150);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(0, myBall.getX_DIRECTION());
        assertEquals(-1, myBall.getY_DIRECTION());
    }

    @Test
    public void testBallReset() {
        myBall.setCenterX(0);
        myBall.setCenterY(BreakoutGame.SIZE);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
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
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(1, myBall.getY_DIRECTION());
    }

    @Test
    public void testBallBounceOffBrickX(){
//        Group temp = new Group();
//        myScene.setRoot(temp);
//        javafxRun(() -> myScene = myBreakoutGame.setupScene(1, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND));
//        javafxRun(() -> myStage.setScene(myScene));
//        sleep(1000);
//        myBall = lookup("#ball").query();
        myBall.setCenterX(300 + myBall.getRadius() / 2);
        myBall.setCenterY(150);
        myBall.setX_DIRECTION(-1);
        myBall.setY_DIRECTION(0);
        myBall.startBall(150);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(1, myBall.getX_DIRECTION());
    }


}