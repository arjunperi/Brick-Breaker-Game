package breakout;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Level {
    //what's different abotut levels?
        //1. block configuration - all good
        //2. the types of blocks available - should be good since it's based on the file reading
        //3. the types of power ups available

    private Ball myBall;
    private Display myDisplay;
    private Group myRoot;
    private List<Brick> myLevelsBricks;
    private Paddle myPaddle;
    private List<PowerUp> myLevelsPowerUps;

    private boolean paused;

    private int score;
    private int powerUpIndex;
    private int currentLevel;
    private boolean levelOver;


    public Level (int levelNum, Group root) {
        myRoot = root;
        myRoot.getChildren().clear();
        currentLevel = levelNum;
        myLevelsBricks = BrickList.setUpLevel(levelNum);
        addPaddle();
        addBall();
        addBricks();
        addDisplay();
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
        myBall = myBall.getBallPosition(elapsedTime, myPaddle, myLevelsBricks);
        deleteBricksAndCreatePowerUp();
        myDisplay.setStats(myBall.getGameLives(), score, currentLevel, getHighScore());
        checkPowerUps(elapsedTime);
        setHighScore();
        clearLevelIfOver();
    }

    private void clearLevelIfOver(){
        if (myBall.getGameLives() == 0){
            myRoot.getChildren().clear();
            myRoot.getChildren().add(myDisplay);
            myDisplay.displayGameOver();
        }
        if (myLevelsBricks.size() == 0){
            myRoot.getChildren().clear();
            myRoot.getChildren().add(myDisplay);
            myDisplay.displayLevelClear();
            levelOver = true;
        }
    }

    public boolean checkEnd(){
        return (levelOver);
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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return highScore;
    }

    //needs to be edited- the high score file is being updated during a run, but it's not being saved.
    private void setHighScore() {
        try {
            FileWriter myWriter = new FileWriter("data/highScore.txt");
            if (score > getHighScore()) {
                myWriter.write(score);
                myWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }



    public void handleKeyInput(KeyCode code, Timeline animation) {
        if (code == KeyCode.LEFT) {
            if(paused == false && myPaddle.getX() > 0) {
                myPaddle.setX(myPaddle.getX() - Paddle.PADDLE_SPEED);
            }
        } else if (code == KeyCode.RIGHT) {
            if(paused == false && myPaddle.getX() + myPaddle.PADDLE_WIDTH < BreakoutGame.SIZE) {
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
    }

}
