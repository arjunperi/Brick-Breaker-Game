package breakout;

import javafx.scene.paint.Color;

public class BrokenBrick extends Brick{
    BrokenBrick(int lives){
        super(lives);
        setFill(Color.BROWN);
    }

    @Override
    public int topBallCollision(){
        return 1;
    }

    @Override
    public int bottomBallCollision(){
        return -1;
    }

    @Override
    public int sideBallCollision(){
        return 1;
    }
}
