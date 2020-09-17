package breakout;

import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

    public Paddle(){
        super(Board.SIZE/2 - Board.PADDLE_WIDTH/2,Board.SIZE-Board.PADDLE_HEIGHT,Board.PADDLE_WIDTH,Board.PADDLE_HEIGHT);
        this.setFill(Board.PADDLE_COLOR);
        this.setId("paddle");
    }

    public void resetPaddle(){
        this.setX(Board.SIZE/2- Board.PADDLE_WIDTH/2);
        this.setY(Board.SIZE-Board.PADDLE_HEIGHT);
    }
}

