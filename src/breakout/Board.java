package breakout;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Popup;
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
    private BrickList brickList;
    private List<Brick> myLevelsBricks;
    private Display myDisplay;
    private Timeline animation;
    private boolean paused;
    private Group root;

    private int score;

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
        root = new Group();

        brickList = new BrickList();
        myLevelsBricks = brickList.setUpLevel("level0");
        int brickIndex = 0;

        for(Brick currentBrick: myLevelsBricks){
            currentBrick.setId("brick" + brickIndex);
            root.getChildren().add(currentBrick);
            brickIndex++;
        }


        myPaddle = new Paddle();

        myBall = new Ball();

        root.getChildren().add(myPaddle);
        root.getChildren().add(myBall);

        myDisplay = new Display();
        root.getChildren().add(myDisplay);


        Scene scene = new Scene(root, width, height, background);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }


    void step (double elapsedTime) {
        updateShapes(elapsedTime);
    }

    private void updateShapes (double elapsedTime) {
        myBall = myBall.getBallPosition(elapsedTime, myPaddle, myLevelsBricks);
        deleteBrickIfDestroyed();
        myDisplay.setStats(myBall.getGameLives(), score);
    }


    private void deleteBrickIfDestroyed() {
        //used an iterator here so that I don't get a concurrent modification exception
        Iterator<Brick> bricks = myLevelsBricks.iterator();
        while (bricks.hasNext()) {
            Brick currentBrick = bricks.next();
            if (currentBrick.isDestroyed()) {
                root.getChildren().remove(currentBrick);
                bricks.remove();
                score++;
            }
        }
    }



    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.LEFT) {
          myPaddle.setX(myPaddle.getX() - PADDLE_SPEED);
        }
        else if (code == KeyCode.RIGHT) {
          myPaddle.setX(myPaddle.getX() + PADDLE_SPEED);
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
        else if (code == KeyCode.L){
            myBall.addGameLives();
        }
    }


    public static void main (String[] args) {
        launch(args);
    }
    
}



