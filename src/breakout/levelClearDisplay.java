package breakout;

public class levelClearDisplay extends Display{
    @Override
    public void changeText() {
        setText(readText("levelCleared") + "\nLevel just completed: " + getLevel() +  "\nLives remaining: " +
                getLives() +  "\nCurrent score: " + getScore() + "\nHigh score to beat: " + getHighScore());
    }
}
