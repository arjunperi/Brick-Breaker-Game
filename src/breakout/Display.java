package breakout;

import javafx.scene.text.Text;


public class Display extends Text {

    public Display(){
        super(50,350, "3 Lives Remaining, Score = 0");
    }

    public void setStats(int lives, int score){
        setText("Lives: " + lives  + " Score: " + score);


    }

}
