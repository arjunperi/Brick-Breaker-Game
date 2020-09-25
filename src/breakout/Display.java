package breakout;

import javafx.scene.text.Text;


public class Display extends Text {

    public Display(){
        super(50,350, "Lives: 3 Score = 0");
        setId("display");
    }

    public void displayGameOver(){
        setText("GAME OVER");
    }

    public void displayLevelClear(){
        setText("LEVEL CLEARED: Press Y to continue");
    }

    public void setStats(int lives, int score, int levelNum, int highScore){
        setText("Lives: " + lives + " Score: " + score + " Level: " + levelNum + " High Score: " + highScore);
    }


}
