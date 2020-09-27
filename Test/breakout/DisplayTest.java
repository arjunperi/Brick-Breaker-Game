package breakout;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;


class DisplayTest extends DukeApplicationTest {


    private final BreakoutGame myBreakoutGame = new BreakoutGame();
    private Scene myScene;

    private Paddle myPaddle;
    private Ball myBall;
    private Display myDisplay;
    private PowerUp myPowerUp0;
    private Stage myStage;


    @Override
    public void start(Stage stage) {
        myScene = myBreakoutGame.setupScene(1, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        myStage = stage;
        myStage.setScene(myScene);
        myStage.show();
        myPaddle = lookup("#paddle").query();
        myBall = lookup("#ball").query();
        myDisplay = lookup("#display").query();

    }
    @Test
    public void testScoreIncrease(){
        myBall.setCenterX(0);
        myBall.setCenterY(160 + myBall.getRadius() / 2);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay.stats();
        assertEquals("Lives: " +  myDisplay.getLives() + " Score: 1" + " Level: " +
                myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
    }

    @Test
    public void testLifeDisplay(){
        myBall.setCenterX(0);
        myBall.setCenterY(BreakoutGame.SIZE);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay.stats();
        assertEquals("Lives: " +  myDisplay.getLives() + " Score: " + myDisplay.getScore() + " Level: " +
                myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
    }

    @Test
    public void testLifeDisplayExtraLife(){
        myBall.setCenterX(0);
        myBall.setCenterY(160 + myBall.getRadius() / 2);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
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
        myDisplay.stats();
        assertEquals("Lives: 4" + " Score: " + myDisplay.getScore() + " Level: " +
                myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
    }

    @Test
    public void testLoseGame() {
        //Each of these makes the ball go out out bounds and we lose a life
        myBall.setCenterX(0);
        myBall.setCenterY(BreakoutGame.SIZE);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myBall.setCenterX(0);
        myBall.setCenterY(BreakoutGame.SIZE);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));

        myBall.setCenterX(0);
        myBall.setCenterY(BreakoutGame.SIZE);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay.lose();
        assertEquals("GAME OVER\nPress 1 to restart game from level 1\nPress 0 to restart game from rules screen" +
                "\nBetter luck next time!\n\nLevel reached: " + myDisplay.getLevel() +  "\nScore reached: "
                + myDisplay.getScore() + "\nHigh score to beat: " + myDisplay.getHighScore(), myDisplay.getText());

    }


    @Test
    public void testLevelCompletion() {
        press(myScene, KeyCode.C);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay.clear();
        assertEquals("LEVEL CLEARED!\n" +
                "Press Y to continue\n\nLevel just completed: " + myDisplay.getLevel() +  "\nLives remaining: " +
                myDisplay.getLives() +  "\nCurrent score: " + myDisplay.getScore() + "\nHigh score to beat: " + myDisplay.getHighScore(), myDisplay.getText());
    }

    @Test
    public void testWinGame(){
        Group temp = new Group();
        myScene.setRoot(temp);
        javafxRun(() -> myScene = myBreakoutGame.setupScene(3, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND));
        press(myScene, KeyCode.DIGIT3);
        javafxRun(() -> myStage.setScene(myScene));
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        press(myScene, KeyCode.C);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay = lookup("#display").query();
        assertEquals("GAME WON!\n" +
                "Press 1 to restart game from level 1\n" +
                "Press 0 to restart game from rules screen\n" +
                "Nice job!\n" +
                "\n" +
                "Lives remaining: 3\n" +
                "Score reached: 0\n" +
                "High score to beat: 22", myDisplay.getText());
    }

    @Test
    public void testStartupScreen(){
        Group temp = new Group();
        myScene.setRoot(temp);
        javafxRun(() -> myScene = myBreakoutGame.setupScene(0, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND));
        press(myScene, KeyCode.DIGIT0);
        javafxRun(() -> myStage.setScene(myScene));
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay = lookup("#display").query();
        assertEquals("Welcome to Breakout! Here are the rules of the game:\n" +
                "Move the paddle across the screen using the left and right keys\n" +
                "Press S to start the ball\n" +
                "The ball will bounce around the screen and off bricks\n" +
                "But don't let it fall to the bottom, or you lose a life!\n" +
                "Clear all the bricks to complete a level\n" +
                "But lose all three of your lives, and it's game over!\n" +
                "Press Y to begin level 1\n", myDisplay.getText());
    }

}
