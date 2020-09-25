package breakout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;

public class Level {

    private Ball myBall;
    private Display myDisplay;

    private final Group myRoot;
    private final List<Brick> myLevelsBricks;
    private Paddle myPaddle;
    private List<PowerUp> myLevelsPowerUps;

    private boolean paused;

    private int score;
    private int powerUpIndex;
    private int currentLevel;
    private boolean levelOver;
    private boolean continueGame;
    private boolean levelChange;


    public Level (int levelNum, Group root) {
        myRoot = root;
        myRoot.getChildren().clear();
        currentLevel = levelNum;
        addDisplay();
        addPaddle();
        addBall();
        myLevelsBricks = BrickList.setUpLevel(levelNum);
        addBricks();
        initializePowerUps();

    }

    private void addPaddle(){
        myPaddle = new Paddle();
        myRoot.getChildren().add(myPaddle);
    }

    private void addBall(){
        myBall = new Ball();
        myRoot.getChildren().add(myBall);
    }

    private void addBricks(){
        int brickIndex = 0;
        for (Brick currentBrick : myLevelsBricks) {
            currentBrick.setId("brick" + brickIndex);
            myRoot.getChildren().add(currentBrick);
            brickIndex++;
        }
    }

    private void addDisplay(){
        myDisplay = new Display();
        myRoot.getChildren().add(myDisplay);
    }


    private void initializePowerUps(){
        myLevelsPowerUps = new ArrayList<>();
        powerUpIndex = 0;
    }

    public void updateShapes(double elapsedTime) {
        if (currentLevel == 0){
            myDisplay.startup();
            levelOver = true;
        }
        else{
            myDisplay.stats();
            clearLevelIfOver();
        }
        myDisplay.setStats(myBall.getGameLives(), score, currentLevel, getHighScore());
        myBall = myBall.getBallPosition(elapsedTime, myPaddle, myLevelsBricks);
        deleteBricksAndCreatePowerUp();
        checkPowerUps(elapsedTime);
        setHighScore();
        clearLevelIfOver();
    }

    private void clearLevelIfOver(){
        if (myBall.getGameLives() == 0 && currentLevel > 0) {
            myRoot.getChildren().clear();
            myRoot.getChildren().add(myDisplay);
            myDisplay.lose();
        }
        if (myLevelsBricks.size() == 0 && currentLevel>0 ){
            myRoot.getChildren().clear();
            myRoot.getChildren().add(myDisplay);
            myDisplay.clear();
            levelOver = true;
            if (currentLevel == 3){
                myRoot.getChildren().clear();
                myRoot.getChildren().add(myDisplay);
                myDisplay.win();
            }
        }
    }

    public boolean checkEnd(){
        return (levelOver && continueGame);

    }

    public int changeLevel(){
        if (levelChange){
            return currentLevel;
        }
        else {
            return -1;
        }
    }

    private void deleteBricksAndCreatePowerUp(){
        List<Brick> deletedBricks = BrickList.checkIfBrickIsDestroyed(myLevelsBricks);
        for(Brick currentBrick: deletedBricks){
            if(currentBrick.checkPowerUp()) {
            dropPowerUp(currentBrick);
         }
            myRoot.getChildren().remove(currentBrick);
            score++;
        }
    }

    private void dropPowerUp(Brick powerBrick){
        PowerUp droppedPowerUp = new PowerUp(powerBrick);
        addPowerUpToGame(droppedPowerUp);
    }

    public void addPowerUpToGame(PowerUp droppedPowerUp) {
        myRoot.getChildren().add(droppedPowerUp);
        droppedPowerUp.setId("PowerUp" + powerUpIndex);
        powerUpIndex++;
        myLevelsPowerUps.add(droppedPowerUp);
    }

    private void checkPowerUps(double elapsedTime) {
        Iterator<PowerUp> powerUps = myLevelsPowerUps.iterator();
        while (powerUps.hasNext()) {
            PowerUp currentPowerUp = powerUps.next();
            currentPowerUp.update(elapsedTime);
            if (currentPowerUp.checkActivation(myPaddle, myBall) || currentPowerUp.outOfBounds()) {
                myRoot.getChildren().remove(currentPowerUp);
                powerUps.remove();
            }
        }
    }

    private int getHighScore(){
        int highScore = 0;
        try {
            File myObj = new File("data/highScore.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                highScore = Integer.parseInt(myReader.nextLine());
            }
            myReader.close();
        }
        catch (FileNotFoundException e) {
            throw new IllegalStateException();
        }
        return highScore;
    }

    private void setHighScore() {
        try {
            FileWriter myWriter = new FileWriter("data/highScore.txt", true);
            if (score > getHighScore()) {
                myWriter.write(Integer.toString(score));
                myWriter.write("\n");
                myWriter.close();
            }
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }



    public void handleKeyInput(KeyCode code, Timeline animation) {
        if (code == KeyCode.LEFT) {
            if(!paused && myPaddle.getX() > 0) {
                myPaddle.setX(myPaddle.getX() - Paddle.PADDLE_SPEED);
            }
        } else if (code == KeyCode.RIGHT) {
            if(!paused && myPaddle.getX() + Paddle.PADDLE_WIDTH < BreakoutGame.SIZE) {
                myPaddle.setX(myPaddle.getX() + Paddle.PADDLE_SPEED);
            }
        } else if (code == KeyCode.R) {
            myPaddle.resetPaddle();
            myBall.resetBall();
        } else if (code == KeyCode.SPACE) {
            if (paused) {
                animation.play();
                paused = false;
            } else {
                animation.pause();
                paused = true;
            }
        } else if (code == KeyCode.S) {
            myBall.startBall(150);

        } else if (code == KeyCode.L) {
            myBall.addGameLives();
        }
        else if (code == KeyCode.P){
            PowerUp extraLife = new PowerUp();
            addPowerUpToGame(extraLife);
        }
        else if (code == KeyCode.O){
            for(Brick currentBrick: myLevelsBricks){
                currentBrick.setLives(1);
            }
        }
        else if(code == KeyCode.C){
            myLevelsBricks.clear();
        }
        else if(code == KeyCode.D){
            if (myLevelsBricks.size()>0){
                Brick targetBrick = myLevelsBricks.get(0);
                targetBrick.setLives(0);
            }
        }
        else if(code == KeyCode.Y){
            continueGame = true;
        }
        else if(code == KeyCode.DIGIT0){
            levelChange = true;
            currentLevel = 0;
        }
        else if(code == KeyCode.DIGIT1){
            levelChange = true;
            currentLevel = 1;
        }
        else if(code == KeyCode.DIGIT2){
            levelChange = true;
            currentLevel = 2;
        }
        else if(code == KeyCode.DIGIT3){
            levelChange = true;
            currentLevel = 3;
        }
    }

}
