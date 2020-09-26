package breakout;


import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import org.junit.Ignore;
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
  private PowerUp myPowerUp0;

  private int lives;
  private int score;
  private int highScore;



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
    assertEquals(0, myBall.getBALL_SPEED());
    press(myScene, KeyCode.S);

    assertEquals(150, myBall.getBALL_SPEED());
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
    assertEquals("Lives: 4 Score: 0 Level: 0 High Score: 0" , myDisplay.getText());
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





}