package breakout;


import javafx.scene.paint.Color;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BrickList{

  public List<Brick> setUpLevel(String levelName) throws FileNotFoundException {
    File myFile  = new File("data/" + levelName + ".txt");
    List<Brick> myBricks = new ArrayList<>();
    Scanner myReader = new Scanner(myFile);
    int yOffset = 0;

    while (myReader.hasNextLine()){
      String[] myRow = myReader.nextLine().split(" ");
      for (int col = 0; col < myRow.length; col++) {
        int currentBrickLives = Integer.parseInt(myRow[col]);
        if (currentBrickLives != 0){
          Brick currentBrick = new Brick(currentBrickLives);
          currentBrick.setPosition(col*100,yOffset);
          myBricks.add(currentBrick);
        }
      }
      yOffset += 40;
    }
    return myBricks;
  }
}
