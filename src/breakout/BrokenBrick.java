package breakout;

import javafx.scene.paint.Color;

public class BrokenBrick extends Brick{
    BrokenBrick(int lives){
        super(lives);
        setFill(Color.BROWN);
    }
    @Override
    public void topBallCollision(Ball myBall){
        return;
    }
    @Override
    public void bottomBallCollision(Ball myBall){
        return;
    }
    @Override
    public void sideBallCollision(Ball myBall){
        return;
    }
}
