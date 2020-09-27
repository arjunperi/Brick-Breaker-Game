package breakout;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;


class BreakoutGameTest extends DukeApplicationTest {


  private final BreakoutGame myBreakoutGame = new BreakoutGame();
  private Scene myScene;

  private Brick myBrick0;
  private Brick myBrick1;
  private Brick myBrick8;
  private Paddle myPaddle;
  private Ball myBall;
  private Display myDisplay;
  private Stage myStage;

  private PowerUp myPowerUp0;


  @Override
  public void start(Stage stage) {
    myScene = myBreakoutGame.setupScene(1, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
    myStage = stage;
    myStage.setScene(myScene);
    myStage.show();
    myBrick0 = lookup("#brick0").query();
    myBrick1 = lookup("#brick1").query();
    myBrick8 = lookup("#brick8").query();
    myPaddle = lookup("#paddle").query();
    myBall = lookup("#ball").query();
    myDisplay = lookup("#display").query();

  }

  @Test
  public void testPaddleOnEdgeOfScreen(){
    myPaddle.setX(0);
    press(myScene, KeyCode.LEFT);
    assertEquals(0, myPaddle.getX());
    myPaddle.setX(myBreakoutGame.SIZE - myPaddle.PADDLE_WIDTH);
    press(myScene, KeyCode.RIGHT);
    assertEquals(myBreakoutGame.SIZE - myPaddle.PADDLE_WIDTH, myPaddle.getX());
  }

  @Test
  public void testStartKey() {
    assertEquals(0, myBall.getSpeed());
    press(myScene, KeyCode.S);

    assertEquals(150, myBall.getSpeed());
  }

  @Test
  public void testResetKey() {
    myBall.setCenterX(0);
    myBall.setCenterY(BreakoutGame.SIZE);
    myPaddle.setX(0);
    myPaddle.setY(390);
    press(myScene, KeyCode.R);
    assertEquals(BreakoutGame.SIZE / 2 - Paddle.PADDLE_WIDTH / 2, myPaddle.getX());
    assertEquals(BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT, myPaddle.getY());
    assertEquals(BreakoutGame.SIZE / 2, myBall.getCenterX());
    assertEquals(BreakoutGame.SIZE - 60, myBall.getCenterY());
  }

  @Test
  public void testExtraLifeCheatKey() {
    press(myScene, KeyCode.L);
    javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
    myDisplay.stats();
    assertEquals("Lives: 4" + " Score: " + myDisplay.getScore() + " Level: " +
            myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
  }

  @Test
  public void testBrickLivesCheatKey() {
    assertEquals(2, myBrick0.getBrickLives());
    press(myScene, KeyCode.O);
    assertEquals(1, myBrick0.getBrickLives());
  }

  @Test
  public void testDestroyBrickCheatKey(){
    assertEquals(2, myBrick0.getBrickLives());
    press(myScene, KeyCode.D);
    assertEquals(0, myBrick0.getBrickLives());
  }

  @Test
  public void testChangeLevel0(){
    Group temp = new Group();
    myScene.setRoot(temp);
    javafxRun(() -> myScene = myBreakoutGame.setupScene(0, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND));
    press(myScene, KeyCode.DIGIT0);
    javafxRun(() -> myStage.setScene(myScene));
    sleep(1000);
    myDisplay = lookup("#display").query();
    myDisplay.stats();
    javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
    assertEquals(myDisplay.getLevel(), 0);
  }

  @Test
  public void testTransitionToLevelOne(){
    Group temp = new Group();
    myScene.setRoot(temp);
    javafxRun(() -> myScene = myBreakoutGame.setupScene(1, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND));
    press(myScene, KeyCode.DIGIT1);
    javafxRun(() -> myStage.setScene(myScene));
    javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
    myDisplay = lookup("#display").query();
    myDisplay.stats();
    assertEquals(myDisplay.getLevel(), 1);
    assertEquals("Lives: " +  myDisplay.getLives() + " Score: " + myDisplay.getScore() + " Level: " +
            myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
  }

  @Test
  public void testTransitionToLevelTwo(){
    Group temp = new Group();
    myScene.setRoot(temp);
    javafxRun(() -> myScene = myBreakoutGame.setupScene(2, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND));
    press(myScene, KeyCode.DIGIT2);
    javafxRun(() -> myStage.setScene(myScene));
    javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
    myDisplay = lookup("#display").query();
    myDisplay.stats();
    assertEquals(myDisplay.getLevel(), 2);
    assertEquals("Lives: " +  myDisplay.getLives() + " Score: " + myDisplay.getScore() + " Level: " +
            myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
  }

  @Test
  public void testTransitionToLevelThree(){
    Group temp = new Group();
    myScene.setRoot(temp);
    javafxRun(() -> myScene = myBreakoutGame.setupScene(3, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND));
    press(myScene, KeyCode.DIGIT3);
    javafxRun(() -> myStage.setScene(myScene));
    javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
    myDisplay = lookup("#display").query();
    myDisplay.stats();
    assertEquals(myDisplay.getLevel(), 3);
    assertEquals("Lives: " +  myDisplay.getLives() + " Score: " + myDisplay.getScore() + " Level: " +
            myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
  }
}