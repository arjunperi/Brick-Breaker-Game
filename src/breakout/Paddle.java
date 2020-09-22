package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

  public static final int PADDLE_HEIGHT = 10;
  public static final int PADDLE_WIDTH = 75;
  public static final Paint PADDLE_COLOR = Color.PLUM;
  public static final int PADDLE_SPEED = 10;

  public Paddle() {
    super(BreakoutGame.SIZE / 2 - PADDLE_WIDTH / 2, BreakoutGame.SIZE - PADDLE_HEIGHT, PADDLE_WIDTH,
        PADDLE_HEIGHT);
    setFill(PADDLE_COLOR);
    setId("paddle");
  }

  public void resetPaddle() {
    setX(BreakoutGame.SIZE / 2 - PADDLE_WIDTH / 2);
    setY(BreakoutGame.SIZE - PADDLE_HEIGHT);
  }
}

