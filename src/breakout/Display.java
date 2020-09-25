package breakout;

import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class Display extends Text {

    private int myScore;
    private int myLives;
    private int myLevel;
    private int myHighScore;
    private boolean hideStats;

    public Display(){
        super(50,350,"");
        setId("display");
    }

    public abstract void changeText();

    public int getScore(){
        return myScore;
    }

    public int getLives(){
        return myLives;
    }

    public int getLevel(){
        return myLevel;
    }

    public int getHighScore(){
        return myHighScore;
    }

    private void setLocation(int x, int y){
        setX(x);
        setY(y);
    }


    public void setStats(int lives, int score, int levelNum, int highScore){
        myLives = lives;
        myScore = score;
        myLevel = levelNum;
        myHighScore = highScore;
    }

    public String readText(String fileName){
        setLocation(5,20);
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
        return displayText;
    }


}
