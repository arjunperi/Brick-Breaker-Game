package breakout;


import java.util.Iterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Jerry Fang and Arjun Peri
 * <p>
 * Used to read the level layout from the level#.txt files and construct the associated Brick list
 * and Wall list for the game level.
 */
public class BrickList {

  private List<Brick> myBricks = new ArrayList<>();
  private List<Wall> myWalls = new ArrayList<>();

  /**
   * Constructor.
   * @param levelNum current level number.
   */
  public BrickList(int levelNum) {
    setUpLevel(levelNum);
  }

  /**
   * Default getter for the list of Bricks.
   *
   * @return level's list of Bricks.
   */
  public List<Brick> getMyBricks() {
    return myBricks;
  }

  /**
   * Default getter for the list of Walls.
   *
   * @return level's list of Walls
   */
  public List<Wall> getMyWalls() {
    return myWalls;
  }

  /**
   * File reading method that constructs myBricks and myWalls based on the level layout contained in
   * the level files.
   *
   * @param levelNum is the level.
   */
  public void setUpLevel(int levelNum) {
    try {
      File myFile = new File("data/level" + levelNum + ".txt");
      Scanner myReader = new Scanner(myFile);
      int yOffset = 0;
      int maxRow = 15;
      while (myReader.hasNextLine() && yOffset < maxRow * Brick.BRICK_HEIGHT) {
        String[] myRow = myReader.nextLine().split(" ");
        int boardRightEdgeIndex = BreakoutGame.SIZE / Brick.BRICK_WIDTH;
        int maxRowIndex = Math.min(myRow.length, boardRightEdgeIndex);
        for (int col = 0; col < maxRowIndex; col++) {
          if (myRow[col].contains("-")) {
            Wall currentWall = new Wall();
            currentWall.setX(col * Brick.BRICK_WIDTH);
            currentWall.setY(yOffset);
            myWalls.add(currentWall);
          } else if (myRow[col].contains("*")) {
            constructBrokenBrick(myBricks, yOffset, myRow, col);
          } else if (myRow[col].contains("+")) {
            constructRubberBrick(myBricks, yOffset, myRow, col);
          } else {
            constructNormalBrick(myBricks, yOffset, myRow, col);
          }
        }
        yOffset += Brick.BRICK_HEIGHT;
      }
    } catch (FileNotFoundException | NumberFormatException e) {
      throw new IllegalStateException();
    }
  }

  private void constructRubberBrick(List<Brick> myBricks, int yOffset, String[] myRow, int col) {
    Brick currentBrick = new RubberBrick(1);
    addPowerUp(myRow, col, currentBrick);
    addBrickToList(myBricks, yOffset, col, currentBrick);
  }

  private void addPowerUp(String[] myRow, int col, Brick currentBrick) {
    if (myRow[col].contains("L") || myRow[col].contains("S") || myRow[col].contains("P")) {
      String powerUpType = myRow[col].substring(0, 1);
      currentBrick.addPowerUp(powerUpType);
    }
  }

  private void constructNormalBrick(List<Brick> myBricks, int yOffset, String[] myRow, int col) {
    Brick currentBrick;

    int currentBrickLives = Integer.parseInt(myRow[col].replaceAll("\\D", ""));
    if (currentBrickLives != 0) {

      currentBrick = new Brick(currentBrickLives);

      addPowerUp(myRow, col, currentBrick);
      addBrickToList(myBricks, yOffset, col, currentBrick);
    }
  }

  private void constructBrokenBrick(List<Brick> myBricks, int yOffset, String[] myRow, int col) {
    Brick currentBrick = new BrokenBrick(1);
    addPowerUp(myRow, col, currentBrick);

    addBrickToList(myBricks, yOffset, col, currentBrick);
  }

  private static void addBrickToList(List<Brick> myBricks, int yOffset, int col,
      Brick currentBrick) {
    currentBrick.setPosition(col * Brick.BRICK_WIDTH, yOffset);
    myBricks.add(currentBrick);
  }

  /**
   * Called by Level to iterate through the list of Bricks and check if any lives = 0. If so, remove
   * them from the list
   *
   * @param myLevelsBricks the level's bricks.
   * @return updated level's bricks.
   */
  public List<Brick> checkIfBrickIsDestroyed(List<Brick> myLevelsBricks) {
    List<Brick> deletedBricks = new ArrayList<>();
    Iterator<Brick> bricks = myLevelsBricks.iterator();
    while (bricks.hasNext()) {
      Brick currentBrick = bricks.next();
      if (currentBrick.isDestroyed()) {
        bricks.remove();
        deletedBricks.add(currentBrick);
      }
    }
    return deletedBricks;
  }
}
