package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle {

  public static final int BRICK_WIDTH = Board.SIZE / 4;
  public static final int BRICK_HEIGHT = Board.SIZE / 10;
  private int myLives;
  private boolean containsPowerUp = false;
  private String powerUpType = "ExtraLife";

  public Brick(int lives) {
    super(BRICK_WIDTH, BRICK_HEIGHT);
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
    }
    return isDestroyed;
  }

  public void addPowerUp(String powerUpType){
    containsPowerUp = true;
    this.powerUpType = powerUpType;
  }

  public boolean checkPowerUp(){
    return containsPowerUp;
  }

  public String getPowerUpType(){
    return powerUpType;
  }


}
