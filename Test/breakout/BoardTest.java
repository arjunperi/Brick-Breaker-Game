package breakout;


import java.util.concurrent.TimeUnit;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;


class BoardTest extends DukeApplicationTest {


  private final Board myBoard = new Board();
  private Scene myScene;

  private Brick myBrick0;
  private Brick myBrick1;
  private Brick myBrick8;
  private Paddle myPaddle;
  private Ball myBall;
  private Display myDisplay;


  @Override
  public void start(Stage stage) throws FileNotFoundException {
    myScene = myBoard.setupScene(Board.SIZE, Board.SIZE, Board.BACKGROUND);
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
  public void testPaddleInitSize() {
    assertEquals(75, myPaddle.getWidth());
    assertEquals(10, myPaddle.getHeight());
  }

  @Test
  public void testPaddleInitPosition() {
    assertEquals(400 / 2 - 75 / 2, myPaddle.getX());
    assertEquals(400 - 10, myPaddle.getY());
  }

  @Test
  public void testPaddleMovement() {
    myPaddle.setX(150);
    myPaddle.setY(390);
    press(myScene, KeyCode.RIGHT);
    assertEquals(160, myPaddle.getX());
    assertEquals(390, myPaddle.getY());
    press(myScene, KeyCode.LEFT);
    assertEquals(150, myPaddle.getX());
    assertEquals(390, myPaddle.getY());
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
  public void testFirstBrickInFirstAndSecondRow() {
    assertEquals(100, myBrick0.getX());
    assertEquals(0, myBrick0.getY());
    assertEquals(0, myBrick1.getX());
    assertEquals(40, myBrick1.getY());
  }

  @Test
  public void testBallBounceOffCorner() {
    myBall.setCenterX(0);
    myBall.setCenterY(0);
    myBall.setX_DIRECTION(-1);
    myBall.setY_DIRECTION(-1);
    myBall.startBall(150);
    myBoard.step(Board.SECOND_DELAY);
    assertEquals(1, myBall.getX_DIRECTION());
    assertEquals(1, myBall.getY_DIRECTION());

  }

  @Test
  public void testBallBounceOffPaddle() {
    myBall.setCenterX(Board.SIZE / 2);
    myBall.setCenterY(Board.SIZE - Paddle.PADDLE_HEIGHT - myBall.getRadius());
    myBall.setX_DIRECTION(0);
    myBall.setY_DIRECTION(-1);
    myBall.startBall(150);
    myBoard.step(Board.SECOND_DELAY);
    assertEquals(0, myBall.getX_DIRECTION());
    assertEquals(1, myBall.getY_DIRECTION());

  }

  @Test
  public void testBallReset() {
    myBall.setCenterX(0);
    myBall.setCenterY(Board.SIZE);
    myBall.setX_DIRECTION(0);
    myBall.setY_DIRECTION(-1);
    myBall.startBall(150);
    myBoard.step(Board.SECOND_DELAY);
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
    myBoard.step(Board.SECOND_DELAY);
    assertEquals(1, myBall.getY_DIRECTION());
  }

  @Test
  public void testBallBounceOffBrickX(){
    myBall.setCenterX(300 + myBall.getRadius() / 2);
    myBall.setCenterY(150);
    myBall.setX_DIRECTION(-1);
    myBall.setY_DIRECTION(0);
    myBall.startBall(150);
    myBoard.step(Board.SECOND_DELAY);
    assertEquals(1, myBall.getX_DIRECTION());
  }

//  @Test
//  public void testBrickDestruction(){
//    myBall.setCenterX(0);
//    myBall.setCenterY(160 + myBall.getRadius() / 2);
//    myBall.setX_DIRECTION(0);
//    myBall.setY_DIRECTION(-1);
//    myBall.startBall(150);
//    myBoard.step(Board.SECOND_DELAY);
//    assertEquals("Lives: 3 Score: 1", myDisplay.getText());
//  }

  @Test
  public void testLoseLife(){
    myBall.setCenterX(0);
    myBall.setCenterY(Board.SIZE);
    myBall.setX_DIRECTION(0);
    myBall.setY_DIRECTION(-1);
    myBall.startBall(150);
    myBoard.step(Board.SECOND_DELAY);
    assertEquals("Lives: 2 Score: 0" , myDisplay.getText());
  }

}