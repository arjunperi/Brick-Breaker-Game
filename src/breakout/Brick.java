package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle {

  private static final int WIDTH = 400 / 4;
  private static final int HEIGHT = 400 / 10;
  private int myLives;
  private int myBricksDestroyed;

  public Brick(int lives) {
    super(WIDTH, HEIGHT);
    myLives = lives;
    setColor();
  }

  public int getBrickLives() {
    return myLives;
  }

  public void subtractLives() {
    myLives--;
    setColor();
  }

  public void setPosition(double x, double y) {
    setX(x);
    setY(y);
  }

  public void setColor() {
    if (myLives == 1) {
      setFill(Color.HOTPINK);
      setStroke(Color.BLACK);
    }
    if (myLives == 2) {
      setFill(Color.GREEN);
      setStroke(Color.BLACK);
    }
    if (myLives == 3) {
      setFill(Color.BLUE);
      setStroke(Color.BLACK);
    }
  }


  public boolean isDestroyed() {
    boolean isDestroyed = false;
    if (getBrickLives() <= 0) {
      isDestroyed = true;
      myBricksDestroyed++;
    }
    return isDestroyed;
  }

  public int getBricksDestroyed() {
    return myBricksDestroyed;
  }


}
