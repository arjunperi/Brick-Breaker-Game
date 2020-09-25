package breakout;

import javafx.scene.text.Text;


public class Display extends Text {

    public Display(){
        super(50,350, "Lives: 3 Score = 0");
        setId("display");
    }

    public void displayRules(){
        setLocation(5,20);
        setText("Welcome to Breakout! Here are the rules of the game:" +
                "\nMove the paddle across the screen using the left and right keys" +
                "\nPress S to start the ball" +
                "\nThe ball will bounce around the screen and off bricks" +
                "\nBut don't let it fall to the bottom, or you lose a life!" +
                "\nClear all the bricks to complete a level" +
                "\nBut lose all three of your lives, and it's game over!" +
                "\nPress Y to begin ");
    }

    private void setLocation(int x, int y){
        setX(x);
        setY(y);
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
