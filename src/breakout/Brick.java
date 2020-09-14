package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Brick extends Rectangle{
  private static final int WIDTH = 400/4;
  private static final int HEIGHT = 400/10;
  private int myLives;
  Rectangle myBrick;


  public Brick(int lives){
    myLives = lives;
    myBrick = new Rectangle(WIDTH, HEIGHT);
    myBrick.setId("brick");
    this.setColor();
  }

  public int getBrickLives(){
    return myLives;
  }

  public void subtractLives(){
    myLives--;
  }

  public void setPosition(double x, double y){
    myBrick.setX(x);
    myBrick.setY(y);
  }

  public void setColor(){
    if(myLives == 1){
      myBrick.setFill(Color.HOTPINK);
      myBrick.setStroke(Color.BLACK);
    }
    if (myLives == 2){
      myBrick.setFill(Color.GREEN);
      myBrick.setStroke(Color.BLACK);
    }
    if (myLives == 3){
      myBrick.setFill(Color.BLUE);
      myBrick.setStroke(Color.BLACK);
    }

  }

  public Rectangle getBrick(){
    return myBrick;
  }

}
