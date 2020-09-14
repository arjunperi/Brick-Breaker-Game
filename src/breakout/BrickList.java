package breakout;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//Right now it is using the list tempLevel that I fill with numbers. Later, implement the ability
//to populate that list from a file.
//Also, need to add the ability to bounce off of these bricks.
public class BrickList {
  private List<Brick> myBricks;
  private ArrayList<List<Integer>> tempLevel = new ArrayList<>();
  public List<Brick> setUpLevel(){
    tempLevel.add(Arrays.asList(1,1,1,1));
    myBricks = new ArrayList<>();
    getBricksFromFile(tempLevel);
    return myBricks;
  }

  private void getBricksFromFile(ArrayList<List<Integer>> levelDesign){
    int yOffset = 0;
    for(List<Integer> myRow : levelDesign){
      for(int i = 0; i<myRow.size(); i++){
        Brick currentBrick = new Brick(myRow.get(i));
        currentBrick.setPosition(i*100,yOffset);
        myBricks.add(currentBrick);
      }
      yOffset += 40;
    }

  }

}
