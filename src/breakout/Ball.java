package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.List;

public class Ball extends Circle {

  private static final Paint BALL_COLOR = Color.BISQUE;

  private int BALL_SPEED;
  private double X_DIRECTION;
  private double Y_DIRECTION;
  private int gameLives = 3;

  public Ball() {
    super(BreakoutGame.SIZE / 2, BreakoutGame.SIZE - 60, 7, BALL_COLOR);
    this.setId("ball");
    Y_DIRECTION = 1;
    X_DIRECTION = 0;
  }

  public int getBALL_SPEED() {
    return BALL_SPEED;
  }

  public double getX_DIRECTION() {
    return X_DIRECTION;
  }

  public double getY_DIRECTION() {
    return Y_DIRECTION;
  }

  public void setX_DIRECTION(double x_direction) {
    X_DIRECTION = x_direction;
  }

  public void setY_DIRECTION(double y_direction) {
    Y_DIRECTION = y_direction;
  }

  public void startBall(int speed) {
    BALL_SPEED = speed;
  }

  public void endBall() {
    BALL_SPEED = 0;
  }

  public Ball getBallPosition(double elapsedTime, Paddle myPaddle, List<Brick> myLevelsBricks) {
    checkXPosition();
    checkYPosition(myPaddle, myLevelsBricks);
    setPosition(elapsedTime);
    return this;
  }


  public void setPosition(double elapsedTime) {
    setCenterY(getCenterY() + Y_DIRECTION * BALL_SPEED * elapsedTime);
    setCenterX(getCenterX() + X_DIRECTION * BALL_SPEED * elapsedTime);
  }

  private void checkXPosition() {

    if (getBoundsInParent().getMaxX() >= BreakoutGame.SIZE || getCenterX() <= 0) {
      X_DIRECTION = X_DIRECTION * -1;
    }
  }


  private void checkYPosition(Paddle paddle, List<Brick> myLevelsBricks) {
    if (getCenterY() + getRadius() / 2 >= BreakoutGame.SIZE) {
      resetBall();
      gameLives--;
    }

    if (this.getCenterY() <= 0) {
      Y_DIRECTION *= -1;
    }

    double ballCenter = getCenterX();
    double paddleSection = paddle.getBoundsInLocal().getWidth() / 6;

    if (paddle.getBoundsInParent().intersects(getBoundsInParent())) {
      if (ballCenter <= paddle.getX() + paddleSection) {
        X_DIRECTION = -1;
        Y_DIRECTION *= -1;
      } else if (ballCenter >= paddle.getX() + 5 * paddleSection) {
        X_DIRECTION = 1;
        Y_DIRECTION *= -1;
      } else if (ballCenter <= paddle.getX() + 2 * paddleSection) {
        X_DIRECTION = -0.5;
        Y_DIRECTION *= -1;
      } else if (ballCenter >= paddle.getX() + 4 * paddleSection) {
        X_DIRECTION = 0.5;
        Y_DIRECTION *= -1;
        //If it hits hear the center, we don't change the angle of X for continuous momentum
      } else if (ballCenter >= paddle.getX() + 2 * paddleSection) {
        Y_DIRECTION *= -1;
      }
    }

    //current brick direction logic - if the ball hits the sides, change X direction and keep Y direction
    //if ball hits the top or bottom - change Y direction and keep X direction
    //corners?
    //bugs - when it hits an intersection?

    //for (Brick myBrick : myLevelsBricks) {
    for (Brick myBrick: myLevelsBricks){
      double brickEndX = myBrick.getX() + myBrick.getWidth();
      double leftEdgeBall = getCenterX() - getRadius() / 2;
      double rightEdgeBall = getCenterX() + getRadius() / 2;
      double bottomEdgeBall  = getCenterY() + getRadius() / 2;
      double topEdgeBall  = getCenterY() - getRadius() / 2;

      if (myBrick.getBoundsInParent().intersects(getBoundsInParent())) {
        if ((rightEdgeBall > myBrick.getX() && leftEdgeBall < brickEndX && topEdgeBall > myBrick.getY())) {
            myBrick.subtractLives();
            myBrick.getBrickLives();
            Y_DIRECTION = 1;
        }
        else if ((rightEdgeBall > myBrick.getX() && leftEdgeBall < brickEndX && topEdgeBall < myBrick.getY())) {
          myBrick.subtractLives();
          myBrick.getBrickLives();
          Y_DIRECTION = -1;
        }
       else if (bottomEdgeBall > myBrick.getY() && topEdgeBall < myBrick.getY() + myBrick.getHeight()){
         myBrick.subtractLives();
         myBrick.getBrickLives();
         X_DIRECTION *=-1;
       }
      }
    }
  }


  public void resetBall() {
    setCenterX(BreakoutGame.SIZE / 2);
    setCenterY(BreakoutGame.SIZE - 60);
    X_DIRECTION = 0;
    Y_DIRECTION = 1;
    endBall();
  }

  public void addGameLives() {
    gameLives++;
  }

  public int getGameLives() {
    return gameLives;
  }

}

