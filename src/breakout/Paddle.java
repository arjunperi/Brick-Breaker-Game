package breakout;

import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
    public Rectangle myPaddle;

    public Paddle(){
        myPaddle = new Rectangle(Board.SIZE/2 - Board.PADDLE_WIDTH/2, Board.SIZE-10, Board.PADDLE_WIDTH,  Board.PADDLE_HEIGHT);
        myPaddle.setFill(Board.PADDLE_COLOR);
        myPaddle.setId("paddle");
    }

    public Rectangle getPaddle(){
        return myPaddle;
    }

    public void resetPaddle(){
        myPaddle.setX(Board.SIZE/2- Board.PADDLE_WIDTH/2);
        myPaddle.setY(Board.SIZE-10);
    }
}

