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
    super(Board.SIZE / 2 - PADDLE_WIDTH / 2, Board.SIZE - PADDLE_HEIGHT, PADDLE_WIDTH,
        PADDLE_HEIGHT);
    this.setFill(PADDLE_COLOR);
    this.setId("paddle");
  }

  public void resetPaddle() {
    this.setX(Board.SIZE / 2 - PADDLE_WIDTH / 2);
    this.setY(Board.SIZE - PADDLE_HEIGHT);
  }
}

