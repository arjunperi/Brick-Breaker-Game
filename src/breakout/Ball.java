package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import java.util.List;

public class Ball extends Circle {

  private static final Paint BALL_COLOR = Color.BISQUE;
  public static final int BALL_RADIUS = 5;
  public static final int START_SPEED = 150;


  private int speed;
  private double ballXDirection;
  private double ballYDirection;
  private int gameLives;

  public Ball(int lives) {
    super(BreakoutGame.SIZE / 2.0,
        BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT - BALL_RADIUS, BALL_RADIUS, BALL_COLOR);
    this.setId("ball");
    ballYDirection = 1;
    ballXDirection = 0;
    gameLives = lives;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public double getXDirection() {
    return ballXDirection;
  }

  public double getYDirection() {
    return ballYDirection;
  }

  public void setXDirection(double changedX) {
    ballXDirection = changedX;
  }

  public void setYDirection(double changedY) {
    ballYDirection = changedY;
  }

  public void startBall() {
    this.speed = START_SPEED;
  }

  public void endBall() {
    speed = 0;
  }

  public Ball getBallPosition(double elapsedTime, Paddle myPaddle, List<Brick> myLevelsBricks,
      List<Wall> myLevelsWalls) {
    checkBorderCollision();
    checkPaddleCollision(myPaddle);
    checkBrickCollision(myLevelsBricks);
    setPosition(elapsedTime);
    checkWallCollision(myLevelsWalls);
    return this;
  }


  public void setPosition(double elapsedTime) {
    setCenterY(getCenterY() + ballYDirection * speed * elapsedTime);
    setCenterX(getCenterX() + ballXDirection * speed * elapsedTime);
  }

  private void checkBorderCollision() {

    if (getBoundsInParent().getMaxX() >= BreakoutGame.SIZE || getCenterX() <= 0) {
      ballXDirection = ballXDirection * -1;
    }
    if (getCenterY() + getRadius() / 2 >= BreakoutGame.SIZE) {
      resetBall();
      gameLives--;
    }
    if (this.getCenterY() <= 0) {
      ballYDirection *= -1;
    }
  }

  private void checkWallCollision(List<Wall> myLevelsWalls) {

    double leftEdgeBall = getCenterX() - getRadius() / 2;
    double rightEdgeBall = getCenterX() + getRadius() / 2;
    double bottomEdgeBall = getCenterY() + getRadius() / 2;
    double topEdgeBall = getCenterY() - getRadius() / 2;

    for (Wall currentWall : myLevelsWalls) {
      double brickEndX = currentWall.getX() + currentWall.getWidth();

      if (currentWall.getBoundsInParent().intersects(getBoundsInParent())) {
        if ((rightEdgeBall > currentWall.getX() && leftEdgeBall < brickEndX &&
            topEdgeBall > currentWall.getY())) {
          ballYDirection = 1;
        } else if ((rightEdgeBall > currentWall.getX() && leftEdgeBall < brickEndX &&
            topEdgeBall < currentWall.getY())) {
          ballYDirection = -1;
        } else if (bottomEdgeBall > currentWall.getY() &&
            topEdgeBall < currentWall.getY() + currentWall.getHeight()) {
          ballXDirection *= -1;
        }
      }
    }
  }

  private void checkBrickCollision(List<Brick> myLevelsBricks) {

    double leftEdgeBall = getCenterX() - getRadius() / 2;
    double rightEdgeBall = getCenterX() + getRadius() / 2;
    double bottomEdgeBall = getCenterY() + getRadius() / 2;
    double topEdgeBall = getCenterY() - getRadius() / 2;

    for (Brick myBrick : myLevelsBricks) {
      double brickEndX = myBrick.getX() + myBrick.getWidth();

      if (myBrick.getBoundsInParent().intersects(getBoundsInParent())) {
        if ((rightEdgeBall > myBrick.getX() && leftEdgeBall < brickEndX && topEdgeBall > myBrick
            .getY())) {
          myBrick.bottomBallCollision(this);

        } else if ((rightEdgeBall > myBrick.getX() && leftEdgeBall < brickEndX
            && topEdgeBall < myBrick.getY())) {
          myBrick.topBallCollision(this);
        } else if (bottomEdgeBall > myBrick.getY() && topEdgeBall < myBrick.getY() + myBrick
            .getHeight()) {
          myBrick.sideBallCollision(this);
        }
        myBrick.subtractLives();
      }
    }
  }


  private void checkPaddleCollision(Paddle paddle) {
    //Split paddle into 6 regions with variable bounce directions

    double ballCenter = getCenterX();
    double paddleSection = paddle.getBoundsInLocal().getWidth() / 6;

    if (paddle.getBoundsInParent().intersects(getBoundsInParent())) {
      if (ballCenter <= paddle.getX() + paddleSection) {
        ballXDirection = -1;
      } else if (ballCenter >= paddle.getX() + 5 * paddleSection) {
        ballXDirection = 1;
      } else if (ballCenter <= paddle.getX() + 2 * paddleSection) {
        ballXDirection = -0.5;
      } else if (ballCenter >= paddle.getX() + 4 * paddleSection) {
        ballXDirection = 0.5;
      }
      ballYDirection = -1;
    }
  }


  public void resetBall() {
    setCenterX(BreakoutGame.SIZE / 2.0);
    setCenterY(BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT - BALL_RADIUS);
    ballXDirection = 0;
    ballYDirection = 1;
    endBall();
  }

  public void addGameLives() {
    gameLives++;
  }

  public int getGameLives() {
    return gameLives;
  }

}

