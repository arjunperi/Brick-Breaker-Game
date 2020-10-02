package breakout;

import javafx.scene.paint.Color;

/**
 * @author Jerry Fang
 * <p>
 * Subclass of PowerUp. This PowerUp decreases the speed of the Ball, making it easier to hit.
 */
public class BallSpeedReductionPowerUp extends PowerUp {

  public static final int SPEED_DECREASE = 10;

  /**
   * Default Constructor. Sets the color to Blue.
   */
  public BallSpeedReductionPowerUp() {
    super();
    setFill(Color.BLUE);
    setStroke(Color.RED);
  }

  /**
   * Constructor used with Brick destruction. Sets the color to Blue.
   *
   * @param myBrick is the Brick destroyed.
   */
  public BallSpeedReductionPowerUp(Brick myBrick) {
    super(myBrick);
    setFill(Color.BLUE);
    setStroke(Color.RED);
  }

  /**
   * Decreases the Ball's speed.
   *
   * @param myPaddle is the game's Paddle.
   * @param myBall   is the game's Ball.
   */
  @Override
  public void activatePowerUp(Paddle myPaddle, Ball myBall) {
    myBall.setSpeed(myBall.getSpeed() - SPEED_DECREASE);
  }
}
