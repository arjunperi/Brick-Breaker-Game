package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle{
  private static final int WIDTH = 400/4;
  private static final int HEIGHT = 400/10;
  private int myLives;


  public Brick(int lives){
    super();
    this.setWidth(WIDTH);
    this.setHeight(HEIGHT);
    myLives = lives;
    this.setId("brick");
    this.setColor();
  }

  public int getBrickLives(){
    return myLives;
  }

  public void subtractLives(){
    myLives--;
  }

  public void setPosition(double x, double y){
    this.setX(x);
    this.setY(y);
  }

  public void setColor(){
    if(myLives == 1){
      this.setFill(Color.HOTPINK);
      this.setStroke(Color.BLACK);
    }
    if (myLives == 2){
      this.setFill(Color.GREEN);
      this.setStroke(Color.BLACK);
    }
    if (myLives == 3){
      this.setFill(Color.BLUE);
      this.setStroke(Color.BLACK);
    }

  }

  public Rectangle getBrick(){
    return this;
  }

}
