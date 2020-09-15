package breakout;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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


    public static final int PADDLE_HEIGHT = 10;
    public static final int PADDLE_WIDTH = 75;
    public static final Paint PADDLE_COLOR = Color.PLUM;
    public static final int PADDLE_SPEED= 10;


    public static final int VERTICAL_OFFSET = 80;


    private Scene myScene;
    private Paddle myPaddle;
    private Ball myBall;
    private Timeline animation;
    private boolean paused;
    private BrickList brickList;
    private List<Brick> myLevelsBricks;

    public void start(Stage stage) throws FileNotFoundException {
        myScene = setupScene(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();

        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);

        animation.play();
        paused = false;
    }

    Scene setupScene (int width, int height, Paint background) throws FileNotFoundException {
        Group root = new Group();

        brickList = new BrickList();
        myLevelsBricks = brickList.setUpLevel("level0");

        for(Brick currentBrick: myLevelsBricks){
            root.getChildren().add(currentBrick.getBrick());
        }

        myPaddle = new Paddle();

        myBall = new Ball();

        root.getChildren().add(myPaddle);
        root.getChildren().add(myBall);

        Scene scene = new Scene(root, width, height, background);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//        scene.setOnMouseClicked(e -> handleMouseInput());
        return scene;
    }


    void step (double elapsedTime) {
        updateShapes(elapsedTime);
    }

    private void updateShapes (double elapsedTime) {
        myBall = myBall.getBallPosition(elapsedTime, myPaddle, myLevelsBricks);
    }


    private void handleKeyInput (KeyCode code) {
        Rectangle paddleRect = myPaddle.getPaddle();
        if (code == KeyCode.LEFT) {
          paddleRect.setX(paddleRect.getX() - PADDLE_SPEED);
        }
        else if (code == KeyCode.RIGHT) {
          paddleRect.setX(paddleRect.getX() + PADDLE_SPEED);
        }
        else if (code == KeyCode.R){
          myPaddle.resetPaddle();
          myBall.resetBall();
        }
        else if (code == KeyCode.SPACE){
            if(paused) {
              animation.play();
              paused = false;
          }
            else{
              animation.pause();
              paused = true;
            }
        }
        else if (code == KeyCode.S){
            myBall.startBall(150);
        }
    }

  //  private void handleMouseInput () {
  //     myBall.startBall(150);
  //  }


    public static void main (String[] args) {
        launch(args);
    }
    
}



