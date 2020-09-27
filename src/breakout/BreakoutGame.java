package breakout;

import java.io.FileNotFoundException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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

  private int lives;
  private int score;
  private int currentLevel = 0;
  private int levelMax = 3;

  private Group root = new Group();
  private Level myLevel;

  public void start(Stage stage){
    myScene = setupScene(0, SIZE, SIZE, BACKGROUND);
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

  Scene setupScene(int level, int width, int height, Paint background) {
    currentLevel = level;
    myLevel = new Level (currentLevel, score, 3, root);
    Scene scene = new Scene(root, width, height, background);

    scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    return scene;
  }

  void step(double elapsedTime){
    score = myLevel.getScore();
    if (currentLevel == 0){
      lives = 3;
    }
    else{
      lives = myLevel.getLives();
    }
    if (myLevel.checkEnd() && currentLevel < levelMax){
      currentLevel++;
      myLevel = new Level(currentLevel, score, lives, root);
    }
    if (myLevel.changeLevel() >= 0){
      myLevel = new Level(myLevel.changeLevel(), score, lives, root);
    }
  }


  private void handleKeyInput(KeyCode code) {
    if (code == KeyCode.LEFT) {
      if(!paused && myPaddle.getX() > 0) {
        myPaddle.setX(myPaddle.getX() - Paddle.PADDLE_SPEED);
      }
    } else if (code == KeyCode.RIGHT) {
      if(!paused && myPaddle.getX() + Paddle.PADDLE_WIDTH <SIZE) {
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
      myBall.startBall();

    } else if (code == KeyCode.L) {
      myBall.addGameLives();
    }
    else if (code == KeyCode.Y){
      PowerUp extraLife = new ExtraLifePowerUp();
      addPowerUpToGame(extraLife);
    }
    else if (code == KeyCode.B){
      PowerUp extraLife = new BallSpeedReductionPowerUp();
      addPowerUpToGame(extraLife);
    }
    else if (code == KeyCode.P){
      PowerUp extraLife = new PaddleLengthPowerUp();
      addPowerUpToGame(extraLife);
    }
    else if (code == KeyCode.O){
      for(Brick currentBrick: myLevelsBricks){
        currentBrick.setLives(1);
      }
    }
    else if(code == KeyCode.C){
      myLevelsBricks.clear();
    }
  }


  public static void main(String[] args) {
    launch(args);
  }

}



