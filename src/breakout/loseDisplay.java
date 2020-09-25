package breakout;

public class loseDisplay extends Display{
    @Override
    public void changeText() {
        setText(readText("gameOver") + "\nLevel reached: " + getLevel() +  "\nScore reached: "
                + getScore() + "\nHigh score to beat: " + getHighScore());
    }
}
