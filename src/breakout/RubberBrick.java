package breakout;

import javafx.scene.paint.Color;

/**
 * @author Jerry Fang
 * <p>
 * Subclass of Brick. On Ball collision, Broken Brick changes the direction of the Ball and
 * increases it's speed. Overrides the three collision methods such that the Ball's speed is also
 * increased.
 */
public class RubberBrick extends Brick {

  private final int SPEED_INCREASE = 10;

  /**
   * Constructor. Overrides the color to Black.
   *
   * @param lives
   */
  RubberBrick(int lives) {
    super(lives);
    setFill(Color.BLACK);
    setStroke(Color.BLACK);
  }

  @Override
  public void setColor() {
  }

  @Override
  public void topBallCollision(Ball myBall) {
    super.topBallCollision(myBall);
    myBall.setSpeed(myBall.getSpeed() + SPEED_INCREASE);
  }

  @Override
  public void bottomBallCollision(Ball myBall) {
    super.bottomBallCollision(myBall);
    myBall.setSpeed(myBall.getSpeed() + SPEED_INCREASE);
  }

  @Override
  public void sideBallCollision(Ball myBall) {
    super.sideBallCollision(myBall);
    myBall.setSpeed(myBall.getSpeed() + SPEED_INCREASE);
  }
}

