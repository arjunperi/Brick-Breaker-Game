package breakout;

import javafx.scene.shape.Rectangle;

/**
 * @author Jerry Fang
 * <p>
 * Class for the PowerUp objects in the game. Created by Level upon Brick destruction. Abstract
 * Class with three subclasses that must override activatePowerUp method.
 * <p>
 * activatePowerUp has some sort of effect on the game. Class also has method to check for
 * activation on Paddle collision.
 */
public abstract class PowerUp extends Rectangle {

  public static final int POWERUP_WIDTH = Brick.BRICK_WIDTH / 2;
  public static final int POWERUP_HEIGHT = Brick.BRICK_HEIGHT / 2;
  private static final int POWER_UP_X_OFFSET = Brick.BRICK_WIDTH / 4;
  private static final int POWER_UP_Y_OFFSET = Brick.BRICK_HEIGHT / 4;

  /**
   * Default constructor used when created by Level cheat key.
   */
  public PowerUp() {
    super(BreakoutGame.SIZE / 2.0 - POWERUP_WIDTH / 2.0, BreakoutGame.SIZE - 200, POWERUP_WIDTH,
        POWERUP_HEIGHT);
  }

  /**
   * Constructor used when Brick is destroyed, such that the initial position is inside of the
   * destroyed Brick.
   *
   * @param poweredBrick
   */
  public PowerUp(Brick poweredBrick) {
    super(poweredBrick.getX() + POWER_UP_X_OFFSET, poweredBrick.getY() + POWER_UP_Y_OFFSET,
        POWERUP_WIDTH,
        POWERUP_HEIGHT);
  }

  /**
   * Abstract method that handles the effect of the PowerUp. Currently limited to only acting on the
   * Paddle or Ball.
   *
   * @param myPaddle is the game's Paddle.
   * @param myBall   is the game's Ball.
   */
  public abstract void activatePowerUp(Paddle myPaddle, Ball myBall);

  /**
   * Updates the position of the PowerUp to simulate movement.
   *
   * @param elapsedTime the game's "time"
   */
  public void update(double elapsedTime) {
    this.setY(this.getY() + 80 * elapsedTime);
  }

  /**
   * @return true if the PowerUp falls below the game window.
   */
  public boolean outOfBounds() {
    return this.getY() > BreakoutGame.SIZE;
  }

  /**
   * Called by Level. Used to check the activation of the PowerUp from Paddle collision.
   *
   * @param myPaddle game's Paddle.
   * @param myBall   game's Ball.
   * @return true if Paddle collision.
   */
  public boolean checkActivation(Paddle myPaddle, Ball myBall) {
    if (checkHitPaddle(myPaddle)) {
      activatePowerUp(myPaddle, myBall);
      return true;
    }
    return false;
  }

  private boolean checkHitPaddle(Paddle myPaddle) {
    return this.getBoundsInParent().intersects(myPaddle.getBoundsInParent());
  }


}
