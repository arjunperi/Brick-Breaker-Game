package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Brick extends Rectangle {

  public static final int BRICK_WIDTH = BreakoutGame.SIZE / 4;
  public static final int BRICK_HEIGHT = BreakoutGame.SIZE / 10;
  private int myLives;
  private boolean containsPowerUp = false;
  private String powerUpType = "ExtraLife";
  private List<Color> brickColors = new ArrayList<>(Arrays.asList(Color.HOTPINK, Color.GREEN, Color.BLUE));

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

  public void setLives(int lives) {
    myLives = lives;
    setColor();
  }

  public void setPosition(double x, double y) {
    setX(x);
    setY(y);
  }

  public void setColor() {
    //Sets corresponding color. If lives > 3, set color to black.
    if(1 <= myLives && myLives <= brickColors.size()){
      setFill(brickColors.get(myLives - 1));
    }
    else{
      setFill(Color.BLACK);
    }
    setStroke(Color.BLACK);
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
