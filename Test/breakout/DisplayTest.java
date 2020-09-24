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
class DisplayTest extends DukeApplicationTest {


    private final BreakoutGame myBreakoutGame = new BreakoutGame();
    private Scene myScene;

    private Paddle myPaddle;
    private Ball myBall;
    private Display myDisplay;
    private PowerUp myPowerUp0;

    DisplayTest() throws FileNotFoundException {
    }


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        myScene = myBreakoutGame.setupScene(BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        stage.setScene(myScene);
        stage.show();

        myPaddle = lookup("#paddle").query();
        myBall = lookup("#ball").query();
        myDisplay = lookup("#display").query();

    }
    @Test
    public void testScoreIncrease(){
        myBall.setCenterX(0);
        myBall.setCenterY(160 + myBall.getRadius() / 2);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals("Lives: 3 Score: 1 Level: 0 High Score: 1", myDisplay.getText());
    }

    @Test
    public void testLifeDisplay(){
        myBall.setCenterX(0);
        myBall.setCenterY(BreakoutGame.SIZE);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        myBreakoutGame.step(BreakoutGame.SECOND_DELAY);
        assertEquals("Lives: 2 Score: 0 Level: 0 High Score: 1" , myDisplay.getText());
    }

    @Test
    public void testLifeDisplayExtraLife(){
        myBall.setCenterX(0);
        myBall.setCenterY(160 + myBall.getRadius() / 2);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        //Ball starts on the block, step destroys and releases power up
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myPowerUp0 = lookup("#PowerUp0").query();
        myBall.resetBall();
        myPaddle.setX(0);
        myPowerUp0.setY(BreakoutGame.SIZE - PowerUp.POWERUP_HEIGHT - Paddle.PADDLE_HEIGHT);
        //Power up moved to paddle, next step is collision
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        //After collision, next step registers that it was activated
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals("Lives: 4 Score: 1 Level: 0 High Score: 1" , myDisplay.getText());
    }

    @Test
    public void testLoseGame() {
        //Each of these makes the ball go out out bounds and we lose a life
        myBall.setCenterX(0);
        myBall.setCenterY(BreakoutGame.SIZE);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myBall.setCenterX(0);
        myBall.setCenterY(BreakoutGame.SIZE);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myBall.setCenterX(0);
        myBall.setCenterY(BreakoutGame.SIZE);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        assertEquals("GAME OVER" , myDisplay.getText());
    }

    @Test
    public void testLevelCompletion() {
        press(myScene, KeyCode.C);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals("LEVEL CLEARED" , myDisplay.getText());
    }




}