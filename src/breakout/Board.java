package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Board extends Application {

    public static final String TITLE = "Breakout JavaFX";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;

    public static final int BRICK_SIZE = 100;
    public static final Paint BRICK_COLOR = Color.HOTPINK;

    public static final int PADDLE_HEIGHT = 10;
    public static final int PADDLE_WIDTH = 50;
    public static final Paint PADDLE_COLOR = Color.PLUM;
    public static final int PADDLE_SPEED= 5;


    public static final int VERTICAL_OFFSET = 80;


    private Scene myScene;
    private Rectangle brick;
    private Rectangle paddle;
    private Ball myBall;

    public void start(Stage stage){
        myScene = setupScene(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);

        animation.play();
    }

    Scene setupScene (int width, int height, Paint background) {
        Group root = new Group();
        // make some shapes and set their properties
        brick = new Rectangle(width/2 - BRICK_SIZE, height/2 - VERTICAL_OFFSET, BRICK_SIZE, BRICK_SIZE);
        brick.setFill(BRICK_COLOR);
        brick.setId("brick");

        paddle = new Rectangle(width/2 - PADDLE_WIDTH/2, SIZE - 10, PADDLE_WIDTH, PADDLE_HEIGHT);
        paddle.setFill(PADDLE_COLOR);
        paddle.setId("paddle");

        myBall = new Ball();

        root.getChildren().add(brick);
        root.getChildren().add(paddle);
        root.getChildren().add(myBall.getBall());

        Scene scene = new Scene(root, width, height, background);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput());

        return scene;
    }


    void step (double elapsedTime) {
        updateShapes(elapsedTime);
    }

    private void updateShapes (double elapsedTime) {
        myBall = myBall.getBallPosition(elapsedTime, paddle, brick);
    }


    private void handleKeyInput (KeyCode code) {
      if (code == KeyCode.LEFT) {
          paddle.setX(paddle.getX() - PADDLE_SPEED);
      }
      else if (code == KeyCode.RIGHT) {
          paddle.setX(paddle.getX() + PADDLE_SPEED);
      }
      else if (code == KeyCode.R){
          resetPaddle();
          myBall.resetBall();
        }
    }

    private void handleMouseInput () {
       myBall.startBall(75);
    }

    public void resetPaddle(){
        paddle.setX(SIZE/2- PADDLE_WIDTH/2);
        paddle.setY(SIZE-10);
    }

    public static void main (String[] args) {
        launch(args);
    }
    
}



