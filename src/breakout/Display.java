package breakout;

import javafx.scene.text.Text;


public class Display extends Text {

    private int myScore;
    private int myLives;
    private int myLevel;
    private int myHighScore;

    public Display(){
        super(50,350, "Lives: 3 Score = 0");
        setId("display");
    }

    public void displayGameOver(){
        setText("GAME OVER");
    }

    public void clear(){
        setText(readText("levelCleared") + "\nLevel just completed: " + myLevel +  "\nLives remaining: " +
                myLives +  "\nCurrent score: " + myScore + "\nHigh score to beat: " + myHighScore);
    }
    public void win(){
        setText(readText("gameWon") + "\nLives remaining: " +  myLives +  "\nScore reached: "
                + myScore + "\nHigh score to beat: " + myHighScore);
    }
    public void lose(){
        setText(readText("gameOver") + "\nLevel reached: " + myLevel +  "\nScore reached: "
                + myScore + "\nHigh score to beat: " + myHighScore);
    }

    public void stats(){
        setText("Lives: " + myLives + " Score: " + myScore + " Level: " + myLevel + " High Score: " + myHighScore);
    }


    public void setStats(int lives, int score, int levelNum, int highScore){
        myLives = lives;
        myScore = score;
        myLevel = levelNum;
        myHighScore = highScore;
    }

    public int getLives(){
        return myLives;
    }

    public int getScore(){
        return myScore;
    }

    public int getLevel(){
        return myLevel;
    }

    public int getHighScore(){
        return myHighScore;
    }


    public String readText(String fileName){
        setLocation(5,20);
        String displayText = "";
        try {
            File myFile = new File("data/" + fileName + ".txt");
            Scanner myReader = new Scanner(myFile);
            while (myReader.hasNextLine()) {
                displayText += myReader.nextLine();
                displayText += "\n";
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return displayText;
    }


}
