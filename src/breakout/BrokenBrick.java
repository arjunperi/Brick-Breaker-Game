package breakout;

import javafx.scene.paint.Color;

public class BrokenBrick extends Brick {

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
