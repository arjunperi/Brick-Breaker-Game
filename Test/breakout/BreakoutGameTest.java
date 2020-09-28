package breakout;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class BreakoutGameTest extends DukeApplicationTest {


  private final BreakoutGame myBreakoutGame = new BreakoutGame();
  private Scene myScene;

  private Paddle myPaddle;
  private Ball myBall;
  private Display myDisplay;
  private BrickList myBrickList;
  private Stage myStage;
  private Object IllegalArgumentException;


  @Override
  public void start(Stage stage) {
    myScene = myBreakoutGame
        .setupScene(1, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
    myStage = stage;
    myStage.setScene(myScene);
    myStage.show();
    myPaddle = lookup("#paddle").query();
    myBall = lookup("#ball").query();
    myDisplay = lookup("#display").query();

  }

  @Test
  public void testPaddleOnEdgeOfScreen() {
    myPaddle.setX(0);
    press(myScene, KeyCode.LEFT);
    assertEquals(0, myPaddle.getX());
    myPaddle.setX(BreakoutGame.SIZE - Paddle.PADDLE_WIDTH);
    press(myScene, KeyCode.RIGHT);
    assertEquals(BreakoutGame.SIZE - Paddle.PADDLE_WIDTH, myPaddle.getX());
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
    assertEquals(BreakoutGame.SIZE / 2.0 - Paddle.PADDLE_WIDTH / 2.0, myPaddle.getX());
    assertEquals(BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT, myPaddle.getY());
    assertEquals(BreakoutGame.SIZE / 2.0, myBall.getCenterX());
    assertEquals(BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT - Ball.BALL_RADIUS, myBall.getCenterY());
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
  public void testChangeLevel0() {
    Group temp = new Group();
    myScene.setRoot(temp);
    javafxRun(() -> myScene = myBreakoutGame
        .setupScene(0, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND));
    press(myScene, KeyCode.DIGIT0);
    javafxRun(() -> myStage.setScene(myScene));
    sleep(1000);
    myDisplay = lookup("#display").query();
    myDisplay.stats();
    javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
    assertEquals(myDisplay.getLevel(), 0);
  }

  @Test
  public void testTransitionToLevelOne() {
    Group temp = new Group();
    myScene.setRoot(temp);
    javafxRun(() -> myScene = myBreakoutGame
        .setupScene(1, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND));
    press(myScene, KeyCode.DIGIT1);
    javafxRun(() -> myStage.setScene(myScene));
    javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
    myDisplay = lookup("#display").query();
    myDisplay.stats();
    assertEquals(myDisplay.getLevel(), 1);
    assertEquals("Lives: " + myDisplay.getLives() + " Score: " + myDisplay.getScore() + " Level: " +
        myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
  }

  @Test
  public void testTransitionToLevelTwo() {
    Group temp = new Group();
    myScene.setRoot(temp);
    javafxRun(() -> myScene = myBreakoutGame
        .setupScene(2, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND));
    press(myScene, KeyCode.DIGIT2);
    javafxRun(() -> myStage.setScene(myScene));
    javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
    myDisplay = lookup("#display").query();
    myDisplay.stats();
    assertEquals(myDisplay.getLevel(), 2);
    assertEquals("Lives: " + myDisplay.getLives() + " Score: " + myDisplay.getScore() + " Level: " +
        myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
  }

  @Test
  public void testTransitionToLevelThree() {
    Group temp = new Group();
    myScene.setRoot(temp);
    javafxRun(() -> myScene = myBreakoutGame
        .setupScene(3, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND));
    press(myScene, KeyCode.DIGIT3);
    javafxRun(() -> myStage.setScene(myScene));
    javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
    myDisplay = lookup("#display").query();
    myDisplay.stats();
    assertEquals(myDisplay.getLevel(), 3);
    assertEquals("Lives: " + myDisplay.getLives() + " Score: " + myDisplay.getScore() + " Level: " +
        myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
  }

  @Test
  public void testIncreaseBallSpeedCheatKey() {
    press(myScene, KeyCode.S);
    press(myScene, KeyCode.UP);
    javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
    assertEquals(myBall.getSpeed(), 160);
  }

  @Test
  public void testDecreaseBallSpeedCheatKey() {
    press(myScene, KeyCode.S);
    press(myScene, KeyCode.DOWN);
    javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
    assertEquals(myBall.getSpeed(), 140);
  }

  //Test for file error in creating the list

  @Test
  public void testReadListForFileNotFoundException() {
    assertThrows(IllegalStateException.class, () -> new BrickList(4));
  }

  @Test
  public void testImproperFormattedLevelFile() {
    assertThrows(IllegalStateException.class, () -> new BrickList(5));
  }
}