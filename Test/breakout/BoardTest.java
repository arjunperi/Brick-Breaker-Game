package breakout;


import java.util.concurrent.TimeUnit;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;


class BoardTest extends DukeApplicationTest {


    private final Board myBoard = new Board();
    private Scene myScene;

    private BrickList myBrick;
    private Paddle myPaddle;
    private Ball myBall;


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        myScene = myBoard.setupScene(Board.SIZE, Board.SIZE, Board.BACKGROUND);
        stage.setScene(myScene);
        stage.show();


     //   myBrick = lookup("#brick").query();
        myPaddle = lookup("#paddle").query();
        myBall = lookup("#ball").query();

    }

    @Test
    public void testPaddleInitSize() {
        assertEquals(75, myPaddle.getWidth());
        assertEquals(10, myPaddle.getHeight());
    }

    @Test
    public void testPaddleInitPosition() {
        assertEquals(400 / 2 - 75 / 2, myPaddle.getX());
        assertEquals(400 - 10, myPaddle.getY());
    }

    @Test
    public void testPaddleMovement() {
        myPaddle.setX(150);
        myPaddle.setY(390);
        press(myScene, KeyCode.RIGHT);
        assertEquals(160, myPaddle.getX());
        assertEquals(390, myPaddle.getY());
        press(myScene, KeyCode.LEFT);
        assertEquals(150, myPaddle.getX());
        assertEquals(390, myPaddle.getY());
    }

    @Test
    public void testBallInitPosition(){
        assertEquals(400/2,myBall.getCenterX());
        assertEquals(400 -60, myBall.getCenterY());
    }

    @Test
    public void testBallInitSize(){
        assertEquals(7,myBall.getRadius());
    }

    @Test
    public void testBallInitVelocity(){
        assertEquals(0, myBall.getBALL_SPEED());
    }

    @Test
    public void testBallVelocityAfterStart(){
        press(myScene, KeyCode.S);

        assertEquals(150, myBall.getBALL_SPEED());
    }

    @Test
    public void testBallBounceOffCorner(){
        myBall.setCenterX(0);
        myBall.setCenterY(0);
        myBall.setX_DIRECTION(-1);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        myBoard.step(Board.SECOND_DELAY);
        assertEquals(1, myBall.getX_DIRECTION());
        assertEquals(1, myBall.getY_DIRECTION());

    }

    @Test
    public void testBallBounceOffPaddle(){
        myBall.setCenterX(Board.SIZE/2);
        myBall.setCenterY(Board.SIZE-Board.PADDLE_HEIGHT - myBall.getRadius());
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        myBoard.step(Board.SECOND_DELAY);
        assertEquals(0, myBall.getX_DIRECTION());
        assertEquals(1, myBall.getY_DIRECTION());

    }

    @Test
    public void testBallReset(){
        myBall.setCenterX(0);
        myBall.setCenterY(Board.SIZE);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        myBoard.step(Board.SECOND_DELAY);
        assertEquals(400/2,myBall.getCenterX());
        assertEquals(400 -60, myBall.getCenterY());

    }


}