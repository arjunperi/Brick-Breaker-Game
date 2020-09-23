package breakout;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

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
    private int scoreMax;
    private int powerUpIndex;


    public Level (String levelName, Group root) throws FileNotFoundException {
        myRoot = root;
        myRoot.getChildren().clear();
        myLevelsBricks = BrickList.setUpLevel(levelName);
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
        scoreMax = myLevelsBricks.size();
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
        myDisplay.setStats(myBall.getGameLives(), score, scoreMax);
        checkPowerUps(elapsedTime);
        clearLevelIfOver();
    }

    private void clearLevelIfOver(){
        if (myBall.getGameLives() == 0){
            myRoot.getChildren().clear();
            myRoot.getChildren().add(myDisplay);
            myDisplay.setStats(myBall.getGameLives(), score, scoreMax);
        }
        if (score == scoreMax){
            myRoot.getChildren().clear();
            myRoot.getChildren().add(myDisplay);
            myDisplay.setStats(myBall.getGameLives(), score, scoreMax);
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

    private void checkPowerUps(double elapsedTime){
        for(PowerUp currentPowerUp: myLevelsPowerUps){
            currentPowerUp.update(elapsedTime);
            if(currentPowerUp.checkActivation(myPaddle, myBall)){
                myRoot.getChildren().remove(currentPowerUp);
            }
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
            score = scoreMax;
        }
    }

}
