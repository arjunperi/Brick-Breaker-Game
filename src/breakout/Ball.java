package breakout;

import javafx.css.Size;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Ball extends Board {

  private Circle ball = new Circle(SIZE / 2, SIZE - 15, 2);
  private static final Paint BALL_COLOR = Color.BISQUE;

  private int BALL_SPEED;
  private double X_DIRECTION;
  private double Y_DIRECTION;

  public Ball(int speed) {
    ball.setFill(BALL_COLOR);
    ball.setId("ball");
    Y_DIRECTION = -1;
    X_DIRECTION = 0;
    BALL_SPEED = speed;
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

    //changed direction if it hits top of screen
    if (ball.getCenterY() <= 0) {
      Y_DIRECTION = Y_DIRECTION * -1;

      //change direction if it hits the paddle
    } else if (paddle.getBoundsInParent().intersects(ball.getBoundsInParent())) {
      Y_DIRECTION *= -1;
    }

    //change direction if it hits the brick -- (Probably need to change this once we figure out making brinks from the file.
    //Might be a better idea to change the direction of the ball in the brick class instead, so that we can check which side of the brick it hits
    //Right now, if it hits anywhere on the brick it'll only change Y direction-- need to figure that out.
    //I'm thinking that for the bricks we do it using like an array list, and we iterate through each one to check for if the ball hits.
    else if (brick.getBoundsInParent().intersects(ball.getBoundsInParent())) {
      Y_DIRECTION *=  -1;
    }
  }
}
