package breakout;

public class statsDisplay extends Display {
    @Override
    public void changeText() {
        setText("Lives: " + getLives() + " Score: " + getScore() + " Level: " + getLevel() + " High Score: " + getHighScore());
    }
}
