package breakout;

import javafx.scene.paint.Color;

/**
 * @author Jerry Fang
 *
 * Subclass of PowerUp. This PowerUp increases player life by one.
 */
public class ExtraLifePowerUp extends PowerUp {

  /**
   * Default constructor. Sets color to Yellow.
   */
  public ExtraLifePowerUp() {
    super();
    setFill(Color.YELLOW);
    setStroke(Color.RED);
  }

  /**
   * Constructor used on Brick destruction. Sets color to Yellow.
   * @param myBrick is the Brick destroyed.
   */
  public ExtraLifePowerUp(Brick myBrick) {
    super(myBrick);
    setFill(Color.YELLOW);
    setStroke(Color.RED);
  }

  /**
   * Adds one life to the player.
   * @param myPaddle is the game's Paddle.
   * @param myBall   is the game's Ball.
   */
  @Override
  public void activatePowerUp(Paddle myPaddle, Ball myBall) {
    myBall.addGameLives();
  }
}
