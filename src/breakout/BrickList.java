package breakout;


import java.util.Iterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BrickList {


  public static List<Brick> setUpLevel(int levelNum) {
    try {
      File myFile = new File("data/level" + levelNum + ".txt");
      List<Brick> myBricks = new ArrayList<>();
      Scanner myReader = new Scanner(myFile);
      int yOffset = 0;
      while (myReader.hasNextLine()) {
        String[] myRow = myReader.nextLine().split(" ");
        for (int col = 0; col < myRow.length; col++) {

          if(myRow[col].contains("*")) {
            constructBrokenBrick(myBricks, yOffset, myRow, col);
          }
          else {
            constructNormalOrRubberBrick(myBricks, yOffset, myRow, col);
          }
        }
        yOffset += Brick.BRICK_HEIGHT;
      }
      return myBricks;
    }
    catch(FileNotFoundException e){
      throw new IllegalArgumentException();
    }
  }

  private static void constructNormalOrRubberBrick(List<Brick> myBricks, int yOffset, String[] myRow, int col) {
    Brick currentBrick;
    String powerUpType = "";
    int currentBrickLives = Integer.parseInt(myRow[col].replaceAll("\\D", ""));
    if(currentBrickLives != 0) {
      if(myRow[col].contains("+")){
        currentBrick = new RubberBrick(currentBrickLives);
      }
      else {
        currentBrick = new Brick(currentBrickLives);
      }
      if (myRow[col].contains("L") || myRow[col].contains("S") || myRow[col].contains("P")) {
        powerUpType = myRow[col].substring(0, 1);
        currentBrick.addPowerUp(powerUpType);
      }
      addBrickToList(myBricks, yOffset, col, currentBrick);
    }
  }

  private static void constructBrokenBrick(List<Brick> myBricks, int yOffset, String[] myRow, int col) {
    Brick currentBrick = new BrokenBrick(1);
    if (myRow[col].contains("L") || myRow[col].contains("S") || myRow[col].contains("P")){
      String powerUpType = myRow[col].substring(0, 1);
      currentBrick.addPowerUp(powerUpType);
    }

    addBrickToList(myBricks, yOffset, col, currentBrick);
  }

  private static void addBrickToList(List<Brick> myBricks, int yOffset, int col, Brick currentBrick) {
    currentBrick.setPosition(col * Brick.BRICK_WIDTH, yOffset);
    myBricks.add(currentBrick);
  }

  public static List<Brick> checkIfBrickIsDestroyed(List<Brick> myLevelsBricks) {
    //used an iterator here so that I don't get a concurrent modification exception
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
