package breakout;

import javafx.scene.paint.Color;

/**
 * @author Jerry Fang
 * <p>
 * Subclass of PowerUp. This PowerUp increases the length of the Paddle.
 */
public class PaddleLengthPowerUp extends PowerUp {

  public static final int EXTEND_LENGTH = 10;

  /**
   * Default constructor. Sets the color to Purple.
   */
  public PaddleLengthPowerUp() {
    super();
    setFill(Color.PURPLE);
    setStroke(Color.RED);
  }

  /**
   * Constructor used on Brick Destruction. Sets the color to Purple.
   *
   * @param myBrick is the Brick destroyed.
   */
  public PaddleLengthPowerUp(Brick myBrick) {
    super(myBrick);
    setFill(Color.PURPLE);
    setStroke(Color.RED);
  }

  /**
   * Increases the length of the Paddle
   *
   * @param myPaddle is the game's Paddle.
   * @param myBall   is the game's Ball.
   */
  @Override
  public void activatePowerUp(Paddle myPaddle, Ball myBall) {
    myPaddle.setWidth(myPaddle.getWidth() + EXTEND_LENGTH);
  }
}
