package breakout;


import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;


class PowerUpTest extends DukeApplicationTest {


    private final BreakoutGame myBreakoutGame = new BreakoutGame();
    private Scene myScene;

    private Brick myBrick0;
    private Brick myBrick1;
    private Brick myBrick8;
    private Paddle myPaddle;
    private Ball myBall;
    private Display myDisplay;
    private PowerUp myPowerUp0;


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        myScene = myBreakoutGame.setupScene(BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        stage.setScene(myScene);
        stage.show();

        myBrick0 = lookup("#brick0").query();
        myBrick1 = lookup("#brick1").query();
        myBrick8 = lookup("#brick8").query();
        myPaddle = lookup("#paddle").query();
        myBall = lookup("#ball").query();
        myDisplay = lookup("#display").query();

    }

    @Test
    public void testPowerUpCreation(){
        myBall.setCenterX(0);
        myBall.setCenterY(160 + myBall.getRadius() / 2);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myPowerUp0 = lookup("#PowerUp0").query();
        assertEquals( Brick.BRICK_WIDTH/4, myPowerUp0.getX());
        //adjusted y for one "step" movement downwards
        assertEquals(120 +  Brick.BRICK_HEIGHT/4 + 80 * BreakoutGame.SECOND_DELAY, myPowerUp0.getY());
    }

    @Test
    public void testExtraLifePowerUpActivation(){
        press(myScene, KeyCode.Y);
        myPowerUp0 = lookup("#PowerUp0").query();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myPowerUp0 = lookup("#PowerUp0").query();
        myPowerUp0.setY(BreakoutGame.SIZE - PowerUp.POWERUP_HEIGHT - Paddle.PADDLE_HEIGHT);
        //Power up moved to paddle, next step is collision
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        //After collision, next step registers that it was activated
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals("Lives: 4 Score: 0" , myDisplay.getText());
      //  assertEquals("Lives: 4 Score: 0 Level: 0 High Score: 1" , myDisplay.getText());
    }

    @Test
    public void testBallSpeedReductionPowerUpActivation(){
        press(myScene, KeyCode.B);
        myPowerUp0 = lookup("#PowerUp0").query();
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myPowerUp0 = lookup("#PowerUp0").query();
        myPowerUp0.setY(BreakoutGame.SIZE - PowerUp.POWERUP_HEIGHT - Paddle.PADDLE_HEIGHT);
        //Power up moved to paddle, next step is collision
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        //After collision, next step registers that it was activated
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(Ball.START_SPEED - BallSpeedReductionPowerUp.SPEED_DECREASE, myBall.getSpeed());
    }

    @Test
    public void testPaddleLengthPowerUpActivation(){
        press(myScene, KeyCode.P);
        myPowerUp0 = lookup("#PowerUp0").query();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myPowerUp0 = lookup("#PowerUp0").query();
        myPowerUp0.setY(BreakoutGame.SIZE - PowerUp.POWERUP_HEIGHT - Paddle.PADDLE_HEIGHT);
        //Power up moved to paddle, next step is collision
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        //After collision, next step registers that it was activated
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(Paddle.PADDLE_WIDTH + PaddleLengthPowerUp.EXTEND_LENGTH, myPaddle.getWidth());
    }

    @Test
    public void testPowerUpCreationCheat(){
        press(myScene, KeyCode.P);
        myPowerUp0 = lookup("#PowerUp0").query();
        assertEquals( BreakoutGame.SIZE / 2 - PowerUp.POWERUP_WIDTH / 2, myPowerUp0.getX());
        assertEquals(BreakoutGame.SIZE - 200, myPowerUp0.getY());
    }


}