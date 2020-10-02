package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import java.util.List;

/**
 * @author Jerry Fang and Arjun Peri.
 * <p>
 * Class for the Ball object used within the game. Keeps track of the Ball movement, direction,
 * speed, and lives of the player. Furthermore, contains the methods checking for Ball collisions
 * with other game objects such as the Paddle, Bricks, and Walls.
 * <p>
 * The constructor for the Ball class creates a Ball with the given lives. Then, the Level class may
 * call startBall to start movement of the Ball, and the getBallCollision method in order to update
 * the ball throughout the game.
 */
public class Ball extends Circle {

  private static final Paint BALL_COLOR = Color.BISQUE;
  public static final int BALL_RADIUS = 5;
  public static final int START_SPEED = 150;


  private int speed;
  private double ballXDirection;
  private double ballYDirection;
  private int gameLives;

  /**
   * Constructor. Sets the initial lives, position, and direction of the Ball.
   * @param lives of the Ball.
   */
  public Ball(int lives) {
    super(BreakoutGame.SIZE / 2.0,
        BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT - BALL_RADIUS, BALL_RADIUS, BALL_COLOR);
    this.setId("ball");
    ballYDirection = 1;
    ballXDirection = 0;
    gameLives = lives;
  }

  /**
   * Default getter for speed.
   *
   * @return speed.
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * Default setter for speed.
   *
   * @param speed sets the new Ball speed (used for PowerUp).
   */
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  /**
   * Default getter for X-Direction.
   *
   * @return X-Direction.
   */
  public double getXDirection() {
    return ballXDirection;
  }

  /**
   * Default getter for Y-Direction.
   *
   * @return Y-Direction.
   */
  public double getYDirection() {
    return ballYDirection;
  }

  /**
   * Setter for X-Direction.
   *
   * @param changedX set as new X-Direction.
   */
  public void setXDirection(double changedX) {
    ballXDirection = changedX;
  }

  /**
   * Setter for Y-Direction.
   *
   * @param changedY set as new Y-Direction.
   */
  public void setYDirection(double changedY) {
    ballYDirection = changedY;
  }

  /**
   * Used by Level to start the movement of the Ball.
   */
  public void startBall() {
    this.speed = START_SPEED;
  }

  /**
   * Used by Level to stop the movement of the Ball.
   */
  public void endBall() {
    speed = 0;
  }

  /**
   * Method called by Level to update the position of the Ball.
   *
   * @param elapsedTime    is a "time" increment of the game.
   * @param myPaddle       is the game's Paddle created in Level.
   * @param myLevelsBricks is the game's list of Bricks created by BrickList (passed by Level).
   * @param myLevelsWalls  is the game's list of Walls created by BrickList (passed by Level).
   * @return this Ball with updated position and direction.
   */
  public Ball getBallPosition(double elapsedTime, Paddle myPaddle, List<Brick> myLevelsBricks,
      List<Wall> myLevelsWalls) {
    checkBorderCollision();
    checkPaddleCollision(myPaddle);
    checkBrickCollision(myLevelsBricks);
    setPosition(elapsedTime);
    checkWallCollision(myLevelsWalls);
    return this;
  }

  /**
   * Method to simulate movement of the ball, updating position by adding speed*time.
   *
   * @param elapsedTime simulated "time" of the game.
   */
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

  /**
   * Method used by Level to reset the Ball to it's starting position.
   */
  public void resetBall() {
    setCenterX(BreakoutGame.SIZE / 2.0);
    setCenterY(BreakoutGame.SIZE - Paddle.PADDLE_HEIGHT - BALL_RADIUS);
    ballXDirection = 0;
    ballYDirection = 1;
    endBall();
  }

  /**
   * Method used to add player lives.
   */
  public void addGameLives() {
    gameLives++;
  }

  /**
   * Used to get player lives.
   *
   * @return lives.
   */
  public int getGameLives() {
    return gameLives;
  }

}

