package breakout;


import java.util.Iterator;
import javafx.scene.Group;
import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BrickList {


  public static List<Brick> setUpLevel(String levelName) throws FileNotFoundException {
    File myFile = new File("data/" + levelName + ".txt");
    List<Brick> myBricks = new ArrayList<>();
    Scanner myReader = new Scanner(myFile);
    int yOffset = 0;
    while (myReader.hasNextLine()) {
      String[] myRow = myReader.nextLine().split(" ");
      for (int col = 0; col < myRow.length; col++) {
        boolean containsPowerUp = false;
        String powerUpType = "";
        int currentBrickLives;
        if (myRow[col].contains("L")){
          currentBrickLives = Integer.parseInt(myRow[col].substring(1,2));
          powerUpType = myRow[col].substring(0,1);
          containsPowerUp = true;
        }
        else {
          currentBrickLives = Integer.parseInt(myRow[col]);
        }
        if (currentBrickLives != 0) {
          Brick currentBrick = new Brick(currentBrickLives);
          if (containsPowerUp){
            currentBrick.addPowerUp(powerUpType);
          }
          currentBrick.setPosition(col * 100, yOffset);
          myBricks.add(currentBrick);
        }
      }
      yOffset += 40;
    }
    return myBricks;
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
