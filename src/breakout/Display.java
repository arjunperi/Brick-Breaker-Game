package breakout;

import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Display extends Text {

    public Display(){
        super(50,350, "Lives: 3 Score = 0");
        setId("display");
    }

    public void displayRules(){
        setLocation(5,20);
        readText("startupScreen");
    }


    private void setLocation(int x, int y){
        setX(x);
        setY(y);
    }


    public void displayGameOver(){
        setText("GAME OVER: Press Q to restart game");
    }

    public void displayLevelClear(){
        setLocation(5,20);
        readText("levelCleared");
    }

    public void setStats(int lives, int score, int levelNum, int highScore){
        setText("Lives: " + lives + " Score: " + score + " Level: " + levelNum + " High Score: " + highScore);
    }

    private void readText(String fileName){
        String displayText = "";
        try {
            File myFile = new File("data/" + fileName + ".txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                displayText += myReader.nextLine();
                displayText += "\n";
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setText(displayText);
    }


}
