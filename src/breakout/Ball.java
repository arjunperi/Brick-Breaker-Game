package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.List;

public class Ball extends Circle{

  private static final Paint BALL_COLOR = Color.BISQUE;

  private int BALL_SPEED;
  private double X_DIRECTION;
  private double Y_DIRECTION;

  public Ball() {
    super();
    this.setCenterX(Board.SIZE / 2);
    this.setCenterY(Board.SIZE - 60);
    this.setRadius(7);
    this.setFill(BALL_COLOR);
    this.setId("ball");
    this.Y_DIRECTION = 1;
    this.X_DIRECTION = 0;
  }

  public int getBALL_SPEED(){
    return BALL_SPEED;
  }

  public double getX_DIRECTION(){
    return X_DIRECTION;
  }

  public double getY_DIRECTION(){
    return Y_DIRECTION;
  }

  public void setX_DIRECTION(double x_direction){
    X_DIRECTION = x_direction;
  }

  public void setY_DIRECTION(double y_direction){
    Y_DIRECTION = y_direction;
  }

  public void startBall(int speed) {
    BALL_SPEED = speed;
  }

  public void endBall() {
    BALL_SPEED = 0;
  }


  public Circle getBall() {
    return this;
  }

  public Ball getBallPosition(double elapsedTime, Paddle myPaddle, List<Brick> myLevelsBricks) {
    checkXPosition();
    checkYPosition(myPaddle,myLevelsBricks);
    setPosition(elapsedTime);
    return this;
  }


  public void setPosition(double elapsedTime) {
    this.setCenterY(this.getCenterY() + Y_DIRECTION * BALL_SPEED * elapsedTime);
    this.setCenterX(this.getCenterX() + X_DIRECTION * BALL_SPEED * elapsedTime);
  }

  private void checkXPosition() {
    //change direction if it hits side of screen
    if (this.getBoundsInParent().getMaxX() >= Board.SIZE || this.getCenterX() <= 0) {
      X_DIRECTION = X_DIRECTION * -1;
    }
  }


  private void checkYPosition(Paddle paddle, List<Brick> myLevelsBricks) {


    if (this.getCenterY() + this.getRadius() / 2 >= Board.SIZE) {
      resetBall();
    }

    if (this.getCenterY() <= 0) {
      Y_DIRECTION *= -1;
    }

    Rectangle paddleRect = paddle;
    double rightEdgeBall = this.getCenterX() + this.getRadius() / 2;
    double paddleSection = paddleRect.getBoundsInLocal().getWidth() / 6;

    if (paddleRect.getBoundsInParent().intersects(this.getBoundsInParent())) {
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
      Rectangle brickRect  = myBrick;
      if (brickRect.getBoundsInParent().intersects(this.getBoundsInParent())) {
        double brickEndX = brickRect.getX() + myBrick.getWidth();
        double leftEdgeBall = this.getCenterX() - this.getRadius() / 2;

        //current brick direction logic - if the ball hits the sides, change X direction and keep Y direction
        //if ball hits the top or bottom - change Y direction and keep X direction
        //corners?

        //bug - when you start the ball on the right edge of the paddle, it catches the brick and gets stuck in a loop - has something to do with corner cases?

        //case 1 - the ball hits either the top or the bottom of the brick
        // i.e the ball is between brick.getX() (X coordinate of left edge) and brickEndX (X coordinate of right edge)
        if (rightEdgeBall >= brickRect.getX() && leftEdgeBall <= brickEndX) {
          X_DIRECTION *= -1;
        }
        //case 2 -  top or bottom

        else {
          Y_DIRECTION *= -1;
        }
      }
    }
  }

    public void resetBall () {
      this.setCenterX(Board.SIZE / 2);
      this.setCenterY(Board.SIZE - 60);
      X_DIRECTION = 0;
      Y_DIRECTION = 1;
      endBall();

    }

  }

