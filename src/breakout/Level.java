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

    public static final int MIN_BALL_SPEED = 10;
    public static final int BALL_SPEED_INCREASE = 10;

    private Ball myBall;
    private Display myDisplay;

    private final Group myRoot;
    private final List<Brick> myLevelsBricks;
    private final List<Wall> myLevelsWalls;
    private Paddle myPaddle;
    private List<PowerUp> myLevelsPowerUps;
    private BrickList myBrickList;

    private boolean paused;
    private int myScore;
    private int myLives;
    private int powerUpIndex;
    private int currentLevel;
    private boolean levelOver;
    private boolean continueGame;
    private boolean changeKeyPressed;
    private boolean gameEnd;


    public Level (int levelNum, int score, int lives,  Group root) {
        myRoot = root;
        myRoot.getChildren().clear();
        currentLevel = levelNum;
        myLives = lives;
        myScore = score;
        addDisplay();
        addPaddle();
        addBall();
        myBrickList = new BrickList(levelNum);
        myLevelsBricks = myBrickList.getMyBricks();
        myLevelsWalls = myBrickList.getMyWalls();
        addWalls();
        addBricks();
        initializePowerUps();
    }

    private void addPaddle(){
        myPaddle = new Paddle();
        myRoot.getChildren().add(myPaddle);
    }

    private void addBall(){
        myBall = new Ball(myLives);
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
    private void addWalls(){
        int wallIndex = 0;
        for (Wall currentWall : myLevelsWalls) {
            currentWall.setId("wall" + wallIndex);
            myRoot.getChildren().add(currentWall);
            wallIndex++;
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
        myBall = myBall.getBallPosition(elapsedTime, myPaddle, myLevelsBricks, myLevelsWalls);
        deleteBricksAndCreatePowerUp();
        checkPowerUps(elapsedTime);
        setHighScore();
        myLives = myBall.getGameLives();
        myDisplay.setStats(myLives, myScore, currentLevel, getHighScore());
        if (currentLevel == 0){
            myDisplay.startup();
            levelOver = true;
        }
        else {
            myDisplay.stats();
            clearLevelIfOver();
        }
    }

    public int getScore(){
        return myScore;
    }

    public int getLives(){
        return myLives;
    }

    private void resetStats(){
        myLives = 3;
        myScore = 0;
    }

    private void clearLevelIfOver(){
        if (myBall.getGameLives() == 0 && currentLevel > 0) {
            myRoot.getChildren().clear();
            myRoot.getChildren().add(myDisplay);
            myDisplay.lose();
            gameEnd = true;
            myBall.endBall();
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
                gameEnd = true;
            }
            myBall.endBall();
        }
    }

    public boolean checkEnd(){
        return (levelOver && continueGame);
    }

    public int changeLevel(){
        if (changeKeyPressed){
            return currentLevel;
        }
        else {
            return -1;
        }
    }


    private void deleteBricksAndCreatePowerUp(){
        List<Brick> deletedBricks = myBrickList.checkIfBrickIsDestroyed(myLevelsBricks);
        for(Brick currentBrick: deletedBricks) {
            if (currentBrick.checkPowerUp()) {
                dropPowerUp(currentBrick);
            }
            myRoot.getChildren().remove(currentBrick);
            if (currentLevel > 0) {
                myScore++;
            }
        }
    }

    private void dropPowerUp(Brick powerBrick) {
        PowerUp droppedPowerUp = null;
        if (powerBrick.getPowerUpType().equals("L")) {
            droppedPowerUp = new ExtraLifePowerUp(powerBrick);
        } else if (powerBrick.getPowerUpType().equals("S")) {
            droppedPowerUp = new BallSpeedReductionPowerUp(powerBrick);
        } else if (powerBrick.getPowerUpType().equals("P")) {
            droppedPowerUp = new PaddleLengthPowerUp(powerBrick);
        }
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

    public int getHighScore(){
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
            if (myScore > getHighScore()) {
                myWriter.write(Integer.toString(myScore));
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
            myBall.startBall();

        } else if (code == KeyCode.L) {
            myBall.addGameLives();
        }
        else if (code == KeyCode.N){
            PowerUp extraLife = new ExtraLifePowerUp();
            addPowerUpToGame(extraLife);
        }
        else if (code == KeyCode.B){
            PowerUp extraLife = new BallSpeedReductionPowerUp();
            addPowerUpToGame(extraLife);
        }
        else if (code == KeyCode.P){
            PowerUp extraLife = new PaddleLengthPowerUp();
            addPowerUpToGame(extraLife);
        }
        else if (code == KeyCode.O){
            for(Brick currentBrick: myLevelsBricks){
                currentBrick.setLives(1);
            }
        }
        else if(code == KeyCode.C){
            if (currentLevel > 0){
                myLevelsBricks.clear();
                myBall.endBall();
            }
        }
        else if(code == KeyCode.D){
            if (myLevelsBricks.size()>0){
                Brick targetBrick = myLevelsBricks.get(0);
                targetBrick.setLives(0);
            }
        }
        else if(code == KeyCode.Y && levelOver){
            continueGame = true;
        }
        else if(code == KeyCode.DIGIT0){
            if (gameEnd){
                resetStats();
            }
            changeKeyPressed = true;
            currentLevel = 0;
        }
        else if(code == KeyCode.DIGIT1){
            if (gameEnd){
                resetStats();
            }
            changeKeyPressed = true;
            currentLevel = 1;
        }
        else if(code == KeyCode.DIGIT2){
            if (gameEnd){
                resetStats();
            }
            changeKeyPressed = true;
            currentLevel = 2;
        }
        else if(code == KeyCode.DIGIT3){
            if (gameEnd){
                resetStats();
            }
            changeKeyPressed = true;
            currentLevel = 3;
        }
        else if(code == KeyCode.DOWN){
            if (myBall.getSpeed() > MIN_BALL_SPEED){
                PowerUp speedPowerUp = new BallSpeedReductionPowerUp();
                speedPowerUp.activatePowerUp(myPaddle, myBall);
            }
        }
        else if(code == KeyCode.UP){
            myBall.setSpeed(myBall.getSpeed() + BALL_SPEED_INCREASE);
        }
    }

}
