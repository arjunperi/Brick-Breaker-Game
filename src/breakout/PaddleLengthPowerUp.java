package breakout;

import javafx.scene.paint.Color;

public class PaddleLengthPowerUp extends PowerUp {
    public static final int EXTEND_LENGTH = 10;

    public PaddleLengthPowerUp(){
        super();
        setFill(Color.PURPLE);
        setStroke(Color.RED);
    }

    public PaddleLengthPowerUp(Brick myBrick){
        super(myBrick);
        setFill(Color.PURPLE);
        setStroke(Color.RED);
    }

    @Override
    public void activatePowerUp(Paddle myPaddle, Ball myBall) {
        myPaddle.setWidth(myPaddle.getWidth() + EXTEND_LENGTH);
    }
}
