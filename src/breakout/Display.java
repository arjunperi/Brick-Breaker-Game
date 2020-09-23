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
        setText("LEVEL CLEARED");
    }

    public void setStats(int lives, int score){
        setText("Lives: " + lives + " Score: " + score);
    }

}
