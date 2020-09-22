package breakout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PowerUp extends Rectangle {
  public static final int POWERUP_WIDTH  = Brick.BRICK_WIDTH/2;
  public static final int POWERUP_HEIGHT = Brick.BRICK_HEIGHT/2;
  private static final int POWER_UP_X_OFFSET = Brick.BRICK_WIDTH/4;
  private static final int POWER_UP_Y_OFFSET = Brick.BRICK_HEIGHT/4;
  private String powerUpType;
  private boolean activated;
  List<String> powerUpList = new ArrayList<String>(Arrays.asList("ExtraLife", "SpeedDecrease", "PaddleGrow"));
  List<String> powerUpCode = new ArrayList<>(Arrays.asList("L"));

  public PowerUp(){
    super(BreakoutGame.SIZE / 2 - POWERUP_WIDTH / 2,BreakoutGame.SIZE - 200, POWERUP_WIDTH, POWERUP_HEIGHT);
    this.setPowerUpType("ExtraLife");
    this.setColor();
    activated = false;
  }

  public PowerUp (Brick poweredBrick){
    super(poweredBrick.getX() + POWER_UP_X_OFFSET, poweredBrick.getY() + POWER_UP_Y_OFFSET, POWERUP_WIDTH,
        POWERUP_HEIGHT);
    int typeOfPower = powerUpCode.indexOf(poweredBrick.getPowerUpType());
    this.setPowerUpType(powerUpList.get(typeOfPower));
    activated = false;
  }

  private void setPowerUpType(String powerUpType){
    //for now, restrict to just extra life. Add more later.
    this.powerUpType = powerUpType;
    setColor();
  }

  public void setColor() {
    if(powerUpType.equals("ExtraLife")) {
      setFill(Color.YELLOW);
      setStroke(Color.RED);
    }
  }

  public void activatePowerUp(Paddle myPaddle, Ball myBall){
    activated = true;
    if(powerUpType.equals("ExtraLife")){
      extraLife(myBall);
    }
  }

  public void extraLife(Ball myBall){
    myBall.addGameLives();
  }


  //This is where we check for if the power up is dropped.

  public void update (double elapsedTime){
    this.setY(this.getY() + 80 * elapsedTime);
  }
  public boolean checkActivation(Paddle myPaddle, Ball myBall) {
    if (checkHitPaddle(myPaddle) && !activated) {
      activatePowerUp(myPaddle, myBall);
      return true;
    }
    return false;
  }

  private boolean checkHitPaddle(Paddle myPaddle){
    return this.getBoundsInParent().intersects(myPaddle.getBoundsInParent());
  }


}
