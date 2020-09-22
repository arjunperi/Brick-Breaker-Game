package breakout;

import javafx.scene.text.Text;


public class Display extends Text {

    public Display(){
        super(50,350, "Lives: 3 Score = 0");
        setId("display");
    }

    public void setStats(int lives, int score, int scoreMax){
        if (lives != 0) {
            setText("Lives: " + lives + " Score: " + score);
            if (score == scoreMax){
                setText("LEVEL CLEARED");
            }
        }
        else {
            setText("GAME OVER");
        }
    }

}
