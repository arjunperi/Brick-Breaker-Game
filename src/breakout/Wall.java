package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Rectangle {
  public Wall(){
    super(Brick.BRICK_WIDTH, Brick.BRICK_HEIGHT);
    setFill(Color.GREY);
  }
}
