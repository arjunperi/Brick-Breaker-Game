package breakout;

import javafx.scene.paint.Color;

/**
 * @author Jerry Fang
 * <p>
 * Subclass of Brick. On Ball collision, Broken Brick does not change the direction of the Ball.
 * Overrides the three collision methods such that the Ball's direction is no longer changed.
 */
public class BrokenBrick extends Brick {

  /**
   * Constructor. Overrides the color of the Brick.
   * @param lives lives of the BrokenBrick.
   */
  BrokenBrick(int lives) {
    super(lives);
    setFill(Color.BROWN);
    setStroke(Color.BLACK);
  }

  @Override
  public void topBallCollision(Ball myBall) {
  }

  @Override
  public void bottomBallCollision(Ball myBall) {

  }

  @Override
  public void sideBallCollision(Ball myBall) {
  }
}
