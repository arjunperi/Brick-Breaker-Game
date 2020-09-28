package breakout;


import java.util.Iterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BrickList {

  private List<Brick> myBricks = new ArrayList<>();
  private List<Wall> myWalls = new ArrayList<>();

  public BrickList(int levelNum) {
    setUpLevel(levelNum);
  }

  public List<Brick> getMyBricks() {
    return myBricks;
  }

  public List<Wall> getMyWalls() {
    return myWalls;
  }


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
