package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.List;

public class Ball extends Circle{

  private Circle ball;
  private static final Paint BALL_COLOR = Color.BISQUE;

  private int BALL_SPEED;
  private double X_DIRECTION;
  private double Y_DIRECTION;

  public Ball() {
    ball = new Circle(Board.SIZE / 2, Board.SIZE - 60, 7);
    ball.setFill(BALL_COLOR);
    ball.setId("ball");
    Y_DIRECTION = 1;
    X_DIRECTION = 0;
  }

  public void startBall(int speed) {
    BALL_SPEED = speed;
  }

  public void endBall() {
    BALL_SPEED = 0;
  }


  public Circle getBall() {
    return ball;
  }

  public Ball getBallPosition(double elapsedTime, Paddle myPaddle, List<Brick> myLevelsBricks) {
    checkXPosition();
    checkYPosition(myPaddle,myLevelsBricks);
    setPosition(elapsedTime);
    return this;
  }


  public void setPosition(double elapsedTime) {
    ball.setCenterY(ball.getCenterY() + Y_DIRECTION * BALL_SPEED * elapsedTime);
    ball.setCenterX(ball.getCenterX() + X_DIRECTION * BALL_SPEED * elapsedTime);
  }

  private void checkXPosition() {
    //change direction if it hits side of screen
    if (ball.getBoundsInParent().getMaxX() >= Board.SIZE || ball.getCenterX() <= 0) {
      X_DIRECTION = X_DIRECTION * -1;
    }
  }


  private void checkYPosition(Paddle paddle, List<Brick> myLevelsBricks) {


    if (ball.getCenterY() + ball.getRadius() / 2 >= Board.SIZE) {
      resetBall();
    }

    if (ball.getCenterY() <= 0) {
      Y_DIRECTION *= -1;
    }

    Rectangle paddleRect = paddle.getPaddle();
    double rightEdgeBall = ball.getCenterX() + ball.getRadius() / 2;
    double paddleSection = paddleRect.getBoundsInLocal().getWidth() / 6;

    if (paddleRect.getBoundsInParent().intersects(ball.getBoundsInParent())) {
      if (rightEdgeBall <= paddleRect.getX() + paddleSection) {
        X_DIRECTION = -1;
        Y_DIRECTION *= -1;
      } else if (rightEdgeBall >= paddleRect.getX() + 5 * paddleSection) {
        X_DIRECTION = 1;
        Y_DIRECTION *= -1;
      } else if (rightEdgeBall <= paddleRect.getX() + 2 * paddleSection) {
        X_DIRECTION = -0.5;
        Y_DIRECTION *= -1;
      } else if (rightEdgeBall >= paddleRect.getX() + 4 * paddleSection) {
        X_DIRECTION = 0.5;
        Y_DIRECTION *= -1;
        //If it hits hear the center, we don't change the angle of X for continous momentum
      } else if (rightEdgeBall >= paddleRect.getX() + 2 * paddleSection) {
        Y_DIRECTION *= -1;
      }
    }

    //change direction if it hits the brick -- (Probably need to change this once we figure out making brinks from the file.
    //Might be a better idea to change the direction of the ball in the brick class instead, so that we can check which side of the brick it hits
    //Right now, if it hits anywhere on the brick it'll only change Y direction-- need to figure that out.
    //I'm thinking that for the bricks we do it using like an array list, and we iterate through each one to check for if the ball hits.

    for (Brick myBrick : myLevelsBricks) {
      Rectangle brickRect  = myBrick.getBrick();
      if (brickRect.getBoundsInParent().intersects(ball.getBoundsInParent())) {
        double brickEndX = brickRect.getX() + myBrick.getWidth();
        double leftEdgeBall = ball.getCenterX() - ball.getRadius() / 2;

        //current brick direction logic - if the ball hits the sides, change X direction and keep Y direction
        //if ball hits the top or bottom - change Y direction and keep X direction
        //corners?

        //bug - when you start the ball on the right edge of the paddle, it catches the brick and gets stuck in a loop - has something to do with corner cases?

        //case 1 - the ball hits either the top or the bottom of the brick
        // i.e the ball is between brick.getX() (X coordinate of left edge) and brickEndX (X coordinate of right edge)
        if (rightEdgeBall >= brickRect.getX() && leftEdgeBall <= brickEndX) {
          Y_DIRECTION *= -1;
        }
        //case 2 -  top or bottom
        else {
          X_DIRECTION *= -1;
        }
        Y_DIRECTION*=-1;
      }
    }
  }

    public void resetBall () {
      ball.setCenterX(Board.SIZE / 2);
      ball.setCenterY(Board.SIZE - 60);
      X_DIRECTION = 0;
      Y_DIRECTION = 1;
      endBall();

    }

  }

