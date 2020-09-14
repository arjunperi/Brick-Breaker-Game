package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ball extends Board {

  private Circle ball = new Circle(SIZE / 2, SIZE - 60, 7);
  private static final Paint BALL_COLOR = Color.BISQUE;

  private int BALL_SPEED;
  private double X_DIRECTION;
  private double Y_DIRECTION;

  public Ball() {
    ball.setFill(BALL_COLOR);
    ball.setId("ball");
    Y_DIRECTION = 1;
    X_DIRECTION = 0;
  }

  public void startBall(int speed){
    BALL_SPEED = speed;
  }

  public void endBall(){
    BALL_SPEED = 0;
  }

  public Circle getBall() {
    return ball;
  }

  public Ball getBallPosition(double elapsedTime, Rectangle myPaddle, Rectangle myBrick) {
    checkXPosition();
    checkYPosition(myPaddle, myBrick);
    setPosition(elapsedTime);
    return this;
  }

  public void setPosition(double elapsedTime) {
    ball.setCenterY(ball.getCenterY() + Y_DIRECTION * BALL_SPEED * elapsedTime);
    ball.setCenterX(ball.getCenterX() + X_DIRECTION * BALL_SPEED * elapsedTime);
  }

  private void checkXPosition() {
    //change direction if it hits side of screen
    if (ball.getBoundsInParent().getMaxX() >= SIZE || ball.getCenterX() <= 0) {
      X_DIRECTION = X_DIRECTION * -1;
    }
  }


  private void checkYPosition(Rectangle paddle, Rectangle brick) {

    //Reset ball to starting position if it hits the bottom of the screen
    if (ball.getCenterY() + ball.getRadius() / 2 >= SIZE) {
      resetBall();
    }

    //Switch Y direction if it hits the top of the screen
    if (ball.getCenterY() <= 0) {
      Y_DIRECTION *= -1;
    }

    //Ball changing directions if it hits the paddle - basically, the paddle is split up into 6 equal lengths. If the ball hits length 1 (first if statement), its X direction
    //changes to -1 (so if it hits the very left edge, it shoots out to the left), or if it hits length 6 (second if statement), then it changes to 1 (right edge, shoot out to the right),
    // and so on. As you get closer to the middle (lengths 3 & 4), the X direction change is less drastic, and in the middle there is no X direction.
    double rightEdgeBall = ball.getCenterX() + ball.getRadius() / 2;
    double paddleX = paddle.getBoundsInLocal().getWidth() / 6;

    if (paddle.getBoundsInParent().intersects(ball.getBoundsInParent())) {
      if (rightEdgeBall <= paddle.getX() + paddleX) {
        X_DIRECTION = -1;
        Y_DIRECTION *= -1;
      } else if (rightEdgeBall >= paddle.getX() + 5 * paddleX) {
        X_DIRECTION = 1;
        Y_DIRECTION *= -1;
      } else if (rightEdgeBall <= paddle.getX() + 2 * paddleX) {
        X_DIRECTION = -0.5;
        Y_DIRECTION *= -1;
      } else if (rightEdgeBall >= paddle.getX() + 4 * paddleX) {
        X_DIRECTION = 0.5;
        Y_DIRECTION *= -1;
      } else if (rightEdgeBall >= paddle.getX() + 2 * paddleX) {
        X_DIRECTION = 0;
        Y_DIRECTION *= -1;
      }

    }

    //change direction if it hits the brick -- (Probably need to change this once we figure out making brinks from the file.
    //Might be a better idea to change the direction of the ball in the brick class instead, so that we can check which side of the brick it hits
    //Right now, if it hits anywhere on the brick it'll only change Y direction-- need to figure that out.
    //I'm thinking that for the bricks we do it using like an array list, and we iterate through each one to check for if the ball hits.

    if (brick.getBoundsInParent().intersects(ball.getBoundsInParent())) {
      double brickEndX = brick.getX() + brick.getWidth();
      double leftEdgeBall  = ball.getCenterX() - ball.getRadius()/2;

      //current brick direction logic - if the ball hits the sides, change X direction and keep Y direction
      //if ball hits the top or bottom - change Y direction and keep X direction
      //corners?

      //bug - when you start the ball on the right edge of the paddle, it catches the brick and gets stuck in a loop - has something to do with corner cases?

      //case 1 - the ball hits either the top or the bottom of the brick
      // i.e the ball is between brick.getX() (X coordinate of left edge) and brickEndX (X coordinate of right edge)
      if (rightEdgeBall >= brick.getX() && leftEdgeBall <= brickEndX) {
        Y_DIRECTION *= -1;
      }

      //case 2 -  top or bottom
      else{
        X_DIRECTION *=-1;
      }
    }
  }

  public void resetBall(){
    ball.setCenterX(SIZE/2);
    ball.setCenterY(SIZE-60);
    X_DIRECTION = 0;
    Y_DIRECTION = 1;
    endBall();

  }

}

