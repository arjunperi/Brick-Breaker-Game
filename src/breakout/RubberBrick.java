package breakout;

import javafx.scene.paint.Color;

public class RubberBrick extends Brick{

    private final int SPEED_INCREASE = 10;

    RubberBrick(int lives){
        super(lives);
        setFill(Color.BLACK);
        setStroke(Color.BLACK);
    }

    @Override
    public void setColor(){
    }
    @Override
    public void topBallCollision(Ball myBall){
        super.topBallCollision(myBall);
        myBall.setSpeed(myBall.getSpeed() + SPEED_INCREASE);
    }
    @Override
    public void bottomBallCollision(Ball myBall){
        super.bottomBallCollision(myBall);
        myBall.setSpeed(myBall.getSpeed() + SPEED_INCREASE);
    }
    @Override
    public void sideBallCollision(Ball myBall){
        super.sideBallCollision(myBall);
        myBall.setSpeed(myBall.getSpeed() + SPEED_INCREASE);
    }
}

