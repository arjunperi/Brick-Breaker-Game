package breakout;

import javafx.scene.paint.Color;

public class BallSpeedReductionPowerUp extends PowerUp {

  public static final int SPEED_DECREASE = 10;

  public BallSpeedReductionPowerUp() {
    super();
    setFill(Color.BLUE);
    setStroke(Color.RED);
  }

  public BallSpeedReductionPowerUp(Brick myBrick) {
    super(myBrick);
    setFill(Color.BLUE);
    setStroke(Color.RED);
  }

  @Override
  public void activatePowerUp(Paddle myPaddle, Ball myBall) {
    myBall.setSpeed(myBall.getSpeed() - SPEED_DECREASE);
  }
}
