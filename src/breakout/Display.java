package breakout;

import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Display extends Text {

    private int myScore;
    private int myLives;
    private int myLevel;
    private int myHighScore;
    private boolean hideStats;

    public Display(){
        super(50,350, "Lives: 3 Score = 0");
        setId("display");
    }

    public void displayRules(){
        hideStats = true;
        setLocation(5,20);
        readText("startupScreen");
    }


    private void setLocation(int x, int y){
        setX(x);
        setY(y);
    }


    public void displayGameOver(){
       readText("gameOver");
    }

    public void displayLevelClear(){
        setLocation(5,20);
        readText("levelCleared");
    }

    public void displayGameWon(){
        readText("gameWon");
    }


    public void setStats(int lives, int score, int levelNum, int highScore){
        myLives = lives;
        myScore = score;
        myLevel = levelNum;
        myHighScore = highScore;
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
        if (hideStats){
            setText(displayText);
        }
        else {
            setText(displayText + "\nLevel just completed: " + myLevel +  "\nLives remaining: " +  myLives +  "\nCurrent score: " + myScore + "\nHigh score to beat: " + myHighScore);
        }
    }


}
