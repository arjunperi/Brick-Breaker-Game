package breakout;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
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
  private Timeline animation;


  private Group root = new Group();
  private int currentLevel = 1;
  private int levelMax = 3;
  //private Level myLevel = new Level (currentLevel, root);
  private Level myLevel = new Level (currentLevel, root);


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
  }

  Scene setupScene(int width, int height, Paint background) throws FileNotFoundException {
    Scene scene = new Scene(root, width, height, background);
    scene.setOnKeyPressed(e -> myLevel.handleKeyInput(e.getCode(), animation));
    return scene;
  }

  void step(double elapsedTime){
    if (myLevel.checkEnd() && currentLevel < levelMax){
      currentLevel++;
      myLevel = new Level(currentLevel,root);
    }
    if (myLevel.changeLevel() >= 0){
      myLevel = new Level(myLevel.changeLevel(), root);
    }
    myLevel.updateShapes(elapsedTime);
  }

  public static void main(String[] args) {
    launch(args);
  }
}



