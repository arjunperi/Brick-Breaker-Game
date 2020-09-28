package breakout;


import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PowerUpTest extends DukeApplicationTest {


    private final BreakoutGame myBreakoutGame = new BreakoutGame();
    private Scene myScene;

    private Paddle myPaddle;
    private Ball myBall;
    private Display myDisplay;
    private PowerUp myPowerUp0;


    @Override
    public void start(Stage stage) {
        //myScene = myBreakoutGame.setupScene(BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        myScene = myBreakoutGame.setupScene(1, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        stage.setScene(myScene);
        stage.show();

        myPaddle = lookup("#paddle").query();
        myBall = lookup("#ball").query();
        myDisplay = lookup("#display").query();

    }

    @Test
    public void testPowerUpCreation(){
        myBall.setCenterX(0);
        myBall.setCenterY(Brick.BRICK_HEIGHT*8);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myPowerUp0 = lookup("#PowerUp0").query();
        assertEquals( Brick.BRICK_WIDTH/4.0, myPowerUp0.getX());
        //adjusted y for one "step" movement downwards
        assertEquals(Brick.BRICK_HEIGHT*7 +  Brick.BRICK_HEIGHT/4.0 + 80 * BreakoutGame.SECOND_DELAY, myPowerUp0.getY());
    }

    @Test
    public void testExtraLifePowerUpCreationAndActivation(){
        myBall.setCenterX(0);
        myBall.setCenterY(Brick.BRICK_HEIGHT*8);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myPowerUp0 = lookup("#PowerUp0").query();
        myPowerUp0.setX(BreakoutGame.SIZE/2.0);
        myPowerUp0.setY(BreakoutGame.SIZE - PowerUp.POWERUP_HEIGHT - Paddle.PADDLE_HEIGHT);
        //Power up moved to paddle, next step is collision
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        //After collision, next step registers that it was activated
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myDisplay.stats();
        assertEquals("Lives: 4" + " Score: " + myDisplay.getScore() + " Level: " +
                myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
    }

    @Test
    public void testExtraLifePowerUpCheatKeyAndActivation(){
        press(myScene, KeyCode.N);
        myPowerUp0 = lookup("#PowerUp0").query();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myPowerUp0 = lookup("#PowerUp0").query();
        myPowerUp0.setY(BreakoutGame.SIZE - PowerUp.POWERUP_HEIGHT - Paddle.PADDLE_HEIGHT);
        //Power up moved to paddle, next step is collision
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        //After collision, next step registers that it was activated
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myDisplay.stats();
        assertEquals("Lives: 4" + " Score: " + myDisplay.getScore() + " Level: " +
            myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
    }

    @Test
    public void testBallSpeedReductionPowerUpCreationAndActivation(){
        myBall.setCenterX(Brick.BRICK_WIDTH * 8.5);
        myBall.setCenterY(Brick.BRICK_HEIGHT*8);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myPowerUp0 = lookup("#PowerUp0").query();
        myPowerUp0.setX(BreakoutGame.SIZE/2.0);
        myPowerUp0.setY(BreakoutGame.SIZE - PowerUp.POWERUP_HEIGHT - Paddle.PADDLE_HEIGHT);
        //Power up moved to paddle, next step is collision
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        //After collision, next step registers that it was activated
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(Ball.START_SPEED - BallSpeedReductionPowerUp.SPEED_DECREASE, myBall.getSpeed());
    }

    @Test
    public void testBallSpeedReductionPowerUpCheatKeyAndActivation(){
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
    public void testPaddleLengthPowerUpCreationAndActivation(){
        myBall.setCenterX(Brick.BRICK_WIDTH * 1.5);
        myBall.setCenterY(Brick.BRICK_HEIGHT*8);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myPowerUp0 = lookup("#PowerUp0").query();
        myPowerUp0.setX(BreakoutGame.SIZE/2.0);
        myPowerUp0.setY(BreakoutGame.SIZE - PowerUp.POWERUP_HEIGHT - Paddle.PADDLE_HEIGHT);
        //Power up moved to paddle, next step is collision
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        //After collision, next step registers that it was activated
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(Paddle.PADDLE_WIDTH + PaddleLengthPowerUp.EXTEND_LENGTH, myPaddle.getWidth());
    }

    @Test
    public void testPaddleLengthPowerUpCheatKeyAndActivation(){
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



}