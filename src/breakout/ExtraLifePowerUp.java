package breakout;

import javafx.scene.paint.Color;

public class ExtraLifePowerUp extends PowerUp {

  public ExtraLifePowerUp() {
    super();
    setFill(Color.YELLOW);
    setStroke(Color.RED);
  }

  public ExtraLifePowerUp(Brick myBrick) {
    super(myBrick);
    setFill(Color.YELLOW);
    setStroke(Color.RED);
  }

  @Override
  public void activatePowerUp(Paddle myPaddle, Ball myBall) {
    myBall.addGameLives();
  }
}
