package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * @author Jerry Fang
 * <p>
 * Creates the Paddle object within the game.
 */
public class Paddle extends Rectangle {

  public static final int PADDLE_HEIGHT = 10;
  public static final int PADDLE_WIDTH = 55;
  public static final Paint PADDLE_COLOR = Color.PLUM;
  public static final int PADDLE_SPEED = 10;

  /**
   * Constructor. Sets the paddle's initial position and color.
   */
  public Paddle() {
    super(BreakoutGame.SIZE / 2.0 - PADDLE_WIDTH / 2.0, BreakoutGame.SIZE - PADDLE_HEIGHT,
        PADDLE_WIDTH, PADDLE_HEIGHT);
    setFill(PADDLE_COLOR);
    setId("paddle");
  }

  /**
   * Resets the paddle to it's initial position.
   */
  public void resetPaddle() {
    setX(BreakoutGame.SIZE / 2.0 - PADDLE_WIDTH / 2.0);
    setY(BreakoutGame.SIZE - PADDLE_HEIGHT);
  }
}

