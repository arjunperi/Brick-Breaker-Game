package breakout;

public class startDisplay extends Display{

    @Override
    public void changeText() {
        setText(readText("startupScreen"));
    }
}
