package breakout;

import javafx.scene.control.DialogPane;

public class winDisplay extends Display{

    @Override
    public void changeText() {
        setText(readText("gameWon") + "\nLives remaining: " +  getLives() +  "\nScore reached: "
                + getScore() + "\nHigh score to beat: " + getHighScore());
    }
}
