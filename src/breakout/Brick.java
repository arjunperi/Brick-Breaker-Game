package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jerry Fang
 * <p>
 * Class for an individual Brick object within the game. Contains the type of PowerUp a Brick has. A
 * Brick can have multiple lives. Is also the superclass for RubberBrick and BrokenBrick.
 *
 * Bricks are created within BrickList.
 */
public class Brick extends Rectangle {

  public static final int BRICK_WIDTH = BreakoutGame.SIZE / 10;
  public static final int BRICK_HEIGHT = BreakoutGame.SIZE / 20;
  private int myLives;
  private boolean containsPowerUp = false;
  private String powerUpType;
  private final List<Color> brickColors = new ArrayList<>(
      Arrays.asList(Color.HOTPINK, Color.GREEN, Color.BLUE));

  /**
   * Constructor. Sets the initial size, lives, and color of the Brick.
   * @param lives
   */
  public Brick(int lives) {
    super(BRICK_WIDTH, BRICK_HEIGHT);
    myLives = lives;
    setColor();
  }

  /**
   * Default getter for brick lives.
   *
   * @return lives of this brick.
   */
  public int getBrickLives() {
    return myLives;
  }

  /**
   * Subtracts a life from the brick. Called by Ball when collision occurs.
   */
  public void subtractLives() {
    myLives--;
    setColor();
  }

  /**
   * Set lives of this brick. Used for cheat codes in level.
   *
   * @param lives new amount of lives.
   */
  public void setLives(int lives) {
    myLives = lives;
    setColor();
  }

  /**
   * Sets the position of the brick. Used when constructing bricks in the level.
   *
   * @param x coordinate.
   * @param y coordinate.
   */
  public void setPosition(double x, double y) {
    setX(x);
    setY(y);
  }

  /**
   * Sets the color of the brick to match with it's lives remaining. If lives > 3, the color is
   * Beige.
   */
  public void setColor() {
    //Sets corresponding color. If lives > 3, set color to black.
    if (1 <= myLives && myLives <= brickColors.size()) {
      setFill(brickColors.get(myLives - 1));
    } else {
      setFill(Color.BEIGE);
    }
    setStroke(Color.BLACK);
  }

  /**
   * Check if lives are 1. Used by method in BrickList to remove this brick from the game's list of
   * Bricks if lives = 0.
   *
   * @return
   */
  public boolean isDestroyed() {
    boolean isDestroyed = false;
    if (getBrickLives() <= 0) {
      isDestroyed = true;
    }
    return isDestroyed;
  }

  /**
   * Adds an associated power up to the brick.
   *
   * @param powerUpType type of power up.
   */
  public void addPowerUp(String powerUpType) {
    containsPowerUp = true;
    this.powerUpType = powerUpType;
  }

  /**
   * Boolean to check if the brick contains a power up, called by Level when Brick is destroyed.
   *
   * @return true if it contains a power up.
   */
  public boolean checkPowerUp() {
    return containsPowerUp;
  }

  /**
   * @return the type of power up.
   */
  public String getPowerUpType() {
    return powerUpType;
  }

  /**
   * Called when Ball collides with the top of the brick. Updates the direction of the Ball.
   *
   * @param myBall game's Ball.
   */

  public void topBallCollision(Ball myBall) {
    myBall.setYDirection(-1);
  }

  /**
   * Called when Ball collides with the bottom of the brick. Updates the direction of the Ball.
   *
   * @param myBall game's Ball.
   */
  public void bottomBallCollision(Ball myBall) {
    myBall.setYDirection(1);
  }

  /**
   * Called when Ball collides with the side of the brick. Updates the direction of the Ball.
   *
   * @param myBall game's Ball.
   */
  public void sideBallCollision(Ball myBall) {
    myBall.setXDirection(myBall.getXDirection() * -1);
  }


}
