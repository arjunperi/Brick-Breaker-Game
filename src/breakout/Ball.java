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
  private int gameLives;

  public Ball(int lives) {
    super(BreakoutGame.SIZE / 2, BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT - 7, 7, BALL_COLOR);
    this.setId("ball");
    Y_DIRECTION = -1;
    X_DIRECTION = 1;
    gameLives = lives;
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
    checkBorderCollision();
    checkPaddleCollision(myPaddle);
    checkBrickCollision(myLevelsBricks);
    setPosition(elapsedTime);
    return this;
  }


  public void setPosition(double elapsedTime) {
    setCenterY(getCenterY() + Y_DIRECTION * BALL_SPEED * elapsedTime);
    setCenterX(getCenterX() + X_DIRECTION * BALL_SPEED * elapsedTime);
  }

  private void checkBorderCollision() {

    if (getBoundsInParent().getMaxX() >= BreakoutGame.SIZE || getCenterX() <= 0) {
      X_DIRECTION = X_DIRECTION * -1;
    }
    if (getCenterY() + getRadius() / 2 >= BreakoutGame.SIZE) {
      resetBall();
      gameLives--;
    }
    if (this.getCenterY() <= 0) {
      Y_DIRECTION *= -1;
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


  private void checkPaddleCollision(Paddle paddle) {
    //Split paddle into 6 regions with variable bounce directions

    double ballCenter = getCenterX();
    double paddleSection = paddle.getBoundsInLocal().getWidth() / 6;

    if (paddle.getBoundsInParent().intersects(getBoundsInParent())) {
      if (ballCenter <= paddle.getX() + paddleSection) {
        X_DIRECTION = -1;
      } else if (ballCenter >= paddle.getX() + 5 * paddleSection) {
        X_DIRECTION = 1;
      } else if (ballCenter <= paddle.getX() + 2 * paddleSection) {
        X_DIRECTION = -0.5;
      } else if (ballCenter >= paddle.getX() + 4 * paddleSection) {
        X_DIRECTION = 0.5;
      }
      Y_DIRECTION = -1;
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

