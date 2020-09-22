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
import javafx.stage.Stage;
import javafx.util.Duration;


public class BreakoutGame extends Application {

  public static final String TITLE = "Breakout JavaFX";
  public static final int SIZE = 400;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;


  private Scene myScene;
  private Paddle myPaddle;
  private Ball myBall;
  private List<Brick> myLevelsBricks;
  private List<PowerUp> myLevelsPowerUps;
  private Display myDisplay;
  private Timeline animation;
  private boolean paused;
  private Group root;
  private int score;
  private int scoreMax;
  private int powerUpIndex;

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

  Scene setupScene(int width, int height, Paint background) throws FileNotFoundException {
    root = new Group();

    myLevelsBricks = BrickList.setUpLevel("level0");
    int brickIndex = 0;
    scoreMax = myLevelsBricks.size();

    for (Brick currentBrick : myLevelsBricks) {
      currentBrick.setId("brick" + brickIndex);
      root.getChildren().add(currentBrick);
      brickIndex++;
    }

    myPaddle = new Paddle();

    myBall = new Ball();
    myLevelsPowerUps = new ArrayList<>();
    powerUpIndex = 0;

    root.getChildren().add(myPaddle);
    root.getChildren().add(myBall);

    myDisplay = new Display();
    root.getChildren().add(myDisplay);

    Scene scene = new Scene(root, width, height, background);

    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    return scene;
  }


  void step(double elapsedTime) {
    updateShapes(elapsedTime);
  }

  private void updateShapes(double elapsedTime) {
    myBall = myBall.getBallPosition(elapsedTime, myPaddle, myLevelsBricks);
    deleteBricksAndCreatePowerUp();
    myDisplay.setStats(myBall.getGameLives(), score, scoreMax);
    checkPowerUps(elapsedTime);
    clearLevelIfOver();
  }

  private void deleteBricksAndCreatePowerUp(){
    List<Brick> deletedBricks = BrickList.checkIfBrickIsDestroyed(myLevelsBricks);
    for(Brick currentBrick: deletedBricks){
      if(currentBrick.checkPowerUp()) {
        dropPowerUp(currentBrick);
      }
      root.getChildren().remove(currentBrick);
      score++;
    }
  }

  private void dropPowerUp(Brick powerBrick){
    PowerUp droppedPowerUp = new PowerUp(powerBrick);
    addPowerUpToGame(droppedPowerUp);
  }

  private void addPowerUpToGame(PowerUp droppedPowerUp) {
    root.getChildren().add(droppedPowerUp);
    droppedPowerUp.setId("PowerUp" + powerUpIndex);
    powerUpIndex++;
    myLevelsPowerUps.add(droppedPowerUp);
  }

  private void checkPowerUps(double elapsedTime){
    for(PowerUp currentPowerUp: myLevelsPowerUps){
      currentPowerUp.update(elapsedTime);
      if(currentPowerUp.checkActivation(myPaddle, myBall)){
        root.getChildren().remove(currentPowerUp);
      }
    }
  }

  private void clearLevelIfOver(){
    if (myBall.getGameLives() == 0){
      root.getChildren().clear();
      root.getChildren().add(myDisplay);
      myDisplay.setStats(myBall.getGameLives(), score, scoreMax);
    }
    if (score == scoreMax){
      root.getChildren().clear();
      root.getChildren().add(myDisplay);
      myDisplay.setStats(myBall.getGameLives(), score, scoreMax);
    }
  }


  private void handleKeyInput(KeyCode code) {
    if (code == KeyCode.LEFT) {
      if(paused == false && myPaddle.getX() > 0) {
        myPaddle.setX(myPaddle.getX() - Paddle.PADDLE_SPEED);
      }
    } else if (code == KeyCode.RIGHT) {
      if(paused == false && myPaddle.getX() + myPaddle.PADDLE_WIDTH <SIZE) {
        myPaddle.setX(myPaddle.getX() + Paddle.PADDLE_SPEED);
      }
    } else if (code == KeyCode.R) {
      myPaddle.resetPaddle();
      myBall.resetBall();
    } else if (code == KeyCode.SPACE) {
      if (paused) {
        animation.play();
        paused = false;
      } else {
        animation.pause();
        paused = true;
      }
    } else if (code == KeyCode.S) {
      myBall.startBall(150);

    } else if (code == KeyCode.L) {
      myBall.addGameLives();
    }
    else if (code == KeyCode.P){
      PowerUp extraLife = new PowerUp();
      addPowerUpToGame(extraLife);
    }
    else if (code == KeyCode.O){
      for(Brick currentBrick: myLevelsBricks){
        currentBrick.setLives(1);
      }
    }
    else if(code == KeyCode.C){
      score = scoreMax;
    }
  }


  public static void main(String[] args) {
    launch(args);
  }

}



