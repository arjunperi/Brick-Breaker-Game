package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.List;

public class Ball extends Circle {

  private static final Paint BALL_COLOR = Color.BISQUE;
  private static final int BALL_RADIUS = 7;
  public static final int START_SPEED = 150;


  private int speed;
  private double xDirection;
  private double yDirection;
  private int gameLives;

  public Ball(int lives) {
    super(BreakoutGame.SIZE / 2, BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT - BALL_RADIUS, BALL_RADIUS, BALL_COLOR);
    this.setId("ball");
    yDirection = -1;
    xDirection = 1;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public double getXDirection() {
    return xDirection;
  }

  public double getYDirection() {
    return yDirection;
  }

  public void setXDirection(double x_direction) {
    xDirection = x_direction;
  }

  public void setYDirection(double y_direction) {
    yDirection = y_direction;
  }

  public void startBall() {
    this.speed = START_SPEED;
  }

  public void endBall() {
    speed = 0;
  }

  public Ball getBallPosition(double elapsedTime, Paddle myPaddle, List<Brick> myLevelsBricks) {
    checkBorderCollision();
    checkPaddleCollision(myPaddle);
    checkBrickCollision(myLevelsBricks);
    setPosition(elapsedTime);
    return this;
  }


  public void setPosition(double elapsedTime) {
    setCenterY(getCenterY() + yDirection * speed * elapsedTime);
    setCenterX(getCenterX() + xDirection * speed * elapsedTime);
  }

  private void checkBorderCollision() {

    if (getBoundsInParent().getMaxX() >= BreakoutGame.SIZE || getCenterX() <= 0) {
      xDirection = xDirection * -1;
    }
    if (getCenterY() + getRadius() / 2 >= BreakoutGame.SIZE) {
      resetBall();
      gameLives--;
    }
    if (this.getCenterY() <= 0) {
      yDirection *= -1;
    }
  }

  private void checkBrickCollision(List<Brick> myLevelsBricks) {

    for (Brick myBrick: myLevelsBricks){
      double brickEndX = myBrick.getX() + myBrick.getWidth();
      double leftEdgeBall = getCenterX() - getRadius() / 2;
      double rightEdgeBall = getCenterX() + getRadius() / 2;
      double bottomEdgeBall  = getCenterY() + getRadius() / 2;
      double topEdgeBall  = getCenterY() - getRadius() / 2;

      if (myBrick.getBoundsInParent().intersects(getBoundsInParent())) {
        if ((rightEdgeBall > myBrick.getX() && leftEdgeBall < brickEndX && topEdgeBall > myBrick.getY())) {
          myBrick.bottomBallCollision(this);
          myBrick.subtractLives();
          myBrick.getBrickLives();
        }
        else if ((rightEdgeBall > myBrick.getX() && leftEdgeBall < brickEndX && topEdgeBall < myBrick.getY())) {
          myBrick.topBallCollision(this);
          myBrick.subtractLives();
          myBrick.getBrickLives();
        }
        else if (bottomEdgeBall > myBrick.getY() && topEdgeBall < myBrick.getY() + myBrick.getHeight()){
          myBrick.sideBallCollision(this);
          myBrick.subtractLives();
          myBrick.getBrickLives();
        }
      }
    }
  }


  private void checkPaddleCollision(Paddle paddle) {
    //Split paddle into 6 regions with variable bounce directions

    double ballCenter = getCenterX();
    double paddleSection = paddle.getBoundsInLocal().getWidth() / 6;

    if (paddle.getBoundsInParent().intersects(getBoundsInParent())) {
      if (ballCenter <= paddle.getX() + paddleSection) {
        xDirection = -1;
      } else if (ballCenter >= paddle.getX() + 5 * paddleSection) {
        xDirection = 1;
      } else if (ballCenter <= paddle.getX() + 2 * paddleSection) {
        xDirection = -0.5;
      } else if (ballCenter >= paddle.getX() + 4 * paddleSection) {
        xDirection = 0.5;
      }
      yDirection = -1;
    }
  }


  public void resetBall() {
    setCenterX(BreakoutGame.SIZE / 2);
    setCenterY(BreakoutGame.SIZE - 60);
    xDirection = 0;
    yDirection = 1;
    endBall();
  }

  public void addGameLives() {
    gameLives++;
  }

  public int getGameLives() {
    return gameLives;
  }

}

