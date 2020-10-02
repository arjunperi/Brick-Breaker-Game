package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Arjun Peri Main class of the program, used to set up the Scene, Timeline, and Group of
 * the JavaFX program. Creates a Scene and Level, and calls the Level step method to update the next
 * frame of the game.
 * <p>
 * start, setupScene, and step methods modified from code written by Robert Duvall.
 */
public class BreakoutGame extends Application {

  public static final String TITLE = "Breakout JavaFX";
  public static final int SIZE = 400;
  public static final int FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final Paint BACKGROUND = Color.AZURE;


  private Scene myScene;
  private Timeline animation;

  private int lives;
  private int score;
  private int currentLevel = 0;
  private final int levelMax = 3;

  private final Group root = new Group();
  private Level myLevel;

  /**
   * Called when running main, sets up the Scene and Timeline of the game as well as creates a
   * Keyframe to allow for movement within the game.
   *
   * @param stage of the game.
   */
  public void start(Stage stage) {
    myScene = setupScene(0, SIZE, SIZE, BACKGROUND);
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  /**
   * Sets up the scene of the game.
   *
   * @param level      is the Level of the game.
   * @param width      of the viewer.
   * @param height     of the viewer.
   * @param background color.
   * @return scene of the game.
   */
  Scene setupScene(int level, int width, int height, Paint background) {
    currentLevel = level;
    myLevel = new Level(currentLevel, score, 3, root);
    Scene scene = new Scene(root, width, height, background);
    scene.setOnKeyPressed(e -> myLevel.handleKeyInput(e.getCode(), animation));
    return scene;
  }

  /**
   * Used to update the game's object in relation to the time. Also checks for if a level is
   * completed, and if it is creates a new Level object of the next level and updates the game.
   *
   * @param elapsedTime is the "time" of the game.
   */
  void step(double elapsedTime) {
    score = myLevel.getScore();
    if (currentLevel == 0) {
      lives = 3;
    } else {
      lives = myLevel.getLives();
    }
    if (myLevel.checkEnd() && currentLevel < levelMax) {
      currentLevel++;
      myLevel = new Level(currentLevel, score, lives, root);
    } else if (myLevel.changeLevel() >= 0) {
      currentLevel = myLevel.changeLevel();
      myLevel = new Level(currentLevel, score, lives, root);
    }
    myLevel.updateShapes(elapsedTime);
  }

  /**
   * Run main to start the game.
   * @param args arguments.
   */
  public static void main(String[] args) {
    launch(args);
  }
}



