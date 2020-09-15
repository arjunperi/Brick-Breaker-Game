package breakout;

import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

    public Paddle(){
        super();
        this.setX(Board.SIZE/2 - Board.PADDLE_WIDTH/2);
        this.setY(Board.SIZE-Board.PADDLE_HEIGHT);
        this.setWidth( Board.PADDLE_WIDTH);
        this.setHeight(Board.PADDLE_HEIGHT);
        this.setFill(Board.PADDLE_COLOR);
        this.setId("paddle");
    }

    public Rectangle getPaddle(){
        return this;
    }

    public void resetPaddle(){
        this.setX(Board.SIZE/2- Board.PADDLE_WIDTH/2);
        this.setY(Board.SIZE-Board.PADDLE_HEIGHT);
    }
}

