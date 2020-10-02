package breakout;

import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Arjun Peri
 * <p>
 * Used for the text in the game, including stats and splash screens.
 */
public class Display extends Text {

  private int myScore;
  private int myLives;
  private int myLevel;
  private int myHighScore;

  /**
   * Constructor. Sets the position of the text.
   */
  public Display() {
    super(50, 350, "");
    setId("display");
  }

  /**
   * On game startup, display text contained in startupScreen file.
   */
  public void startup() {
    setText(readText("startupScreen"));
  }

  /**
   * Display this text on level completion.
   */
  public void clear() {
    setText(
        readText("levelCleared") + "\nLevel just completed: " + myLevel +
            "\nLives remaining: " + myLives + "\nCurrent score: " + myScore +
            "\nHigh score to beat: " + myHighScore);
  }

  /**
   * Display this text on game win.
   */
  public void win() {
    setText(readText("gameWon") + "\nLives remaining: " + myLives + "\nScore reached: "
        + myScore + "\nHigh score to beat: " + myHighScore);
  }

  /**
   * Display this text on game loss.
   */
  public void lose() {
    setText(readText("gameOver") + "\nLevel reached: " + myLevel + "\nScore reached: "
        + myScore + "\nHigh score to beat: " + myHighScore);
  }

  /**
   * Display the current stats of the player.
   */
  public void stats() {
    setText("Lives: " + myLives + " Score: " + myScore + " Level: " + myLevel + " High Score: "
        + myHighScore);
  }

  /**
   * Update the current stats of the player. Called with each step of the game.
   *
   * @param lives     current lives.
   * @param score     current score.
   * @param levelNum  current level.
   * @param highScore highest score ever achieved in the game.
   */
  public void setStats(int lives, int score, int levelNum, int highScore) {
    myLives = lives;
    myScore = score;
    myLevel = levelNum;
    myHighScore = highScore;
  }

  public int getLives() {
    return myLives;
  }

  public int getScore() {
    return myScore;
  }

  public int getLevel() {
    return myLevel;
  }

  public int getHighScore() {
    return myHighScore;
  }

  /**
   * Used to read the various files containing the text for the Display.
   *
   * @param fileName is the name of the wanted text file.
   * @return the contents of the file as a String.
   */
  public String readText(String fileName) {
    setX(5);
    setY(20);
    StringBuilder displayText = new StringBuilder();
    try {
      File myFile = new File("data/" + fileName + ".txt");
      Scanner myReader = new Scanner(myFile);
      while (myReader.hasNextLine()) {
        displayText.append(myReader.nextLine());
        displayText.append("\n");
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return displayText.toString();
  }


}
