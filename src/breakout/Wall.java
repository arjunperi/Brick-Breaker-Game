package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle {

  /**
   * @author Jerry Fang
   *
   * Class for the Wall object within the game. Walls are created within BrickList.
   */
  public Wall() {
    super(Brick.BRICK_WIDTH, Brick.BRICK_HEIGHT);
    setFill(Color.GREY);
  }
}
