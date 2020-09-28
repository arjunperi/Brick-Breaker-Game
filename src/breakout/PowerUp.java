package breakout;

import javafx.scene.shape.Rectangle;

public abstract class PowerUp extends Rectangle {

  public static final int POWERUP_WIDTH = Brick.BRICK_WIDTH / 2;
  public static final int POWERUP_HEIGHT = Brick.BRICK_HEIGHT / 2;
  private static final int POWER_UP_X_OFFSET = Brick.BRICK_WIDTH / 4;
  private static final int POWER_UP_Y_OFFSET = Brick.BRICK_HEIGHT / 4;

  public PowerUp() {
    super(BreakoutGame.SIZE / 2.0 - POWERUP_WIDTH / 2.0, BreakoutGame.SIZE - 200, POWERUP_WIDTH,
        POWERUP_HEIGHT);
  }

  public PowerUp(Brick poweredBrick) {
    super(poweredBrick.getX() + POWER_UP_X_OFFSET, poweredBrick.getY() + POWER_UP_Y_OFFSET,
        POWERUP_WIDTH,
        POWERUP_HEIGHT);
  }


  public abstract void activatePowerUp(Paddle myPaddle, Ball myBall);

  //This is where we check for if the power up is dropped.

  public void update(double elapsedTime) {
    this.setY(this.getY() + 80 * elapsedTime);
  }

  public boolean outOfBounds() {
    return this.getY() > BreakoutGame.SIZE;
  }

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
