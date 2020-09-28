package breakout;


import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BrickTest extends DukeApplicationTest {


    private final BreakoutGame myBreakoutGame = new BreakoutGame();
    private Scene myScene;

    private Brick myBrick0;
    private Brick myBrick1;
    private Brick myBrick70;
    private Ball myBall;
    private Display myDisplay;


    @Override
    public void start(Stage stage) {
        myScene = myBreakoutGame.setupScene(2, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        stage.setScene(myScene);
        stage.show();
        myBrick0 = lookup("#brick0").query();
        myBrick1 = lookup("#brick1").query();
        myBrick70 = lookup("#brick70").query();
        myBall = lookup("#ball").query();
        myDisplay = lookup("#display").query();
    }

    @Test
    public void testFirstTwoBricks() {
        assertEquals(0, myBrick0.getX());
        assertEquals(0, myBrick0.getY());
        assertEquals(Brick.BRICK_WIDTH, myBrick1.getX());
        assertEquals(0, myBrick1.getY());
    }

    @Test
    public void testBrickDestruction() {
        myBall.setCenterX(Brick.BRICK_WIDTH * 1.5);
        myBall.setCenterY(Brick.BRICK_HEIGHT * 5.5);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay.stats();
        assertEquals("Lives: 3 Score: 1 Level: " +myDisplay.getLevel()+ " High Score: " + myDisplay.getHighScore(),
                myDisplay.getText());
    }

    @Test
    public void testDestroyBrickCheatKey(){
        assertEquals(3, myBrick0.getBrickLives());
        press(myScene, KeyCode.D);
        assertEquals(0, myBrick0.getBrickLives());
    }

    @Test
    public void testBrickLivesToOneCheatKey() {
        assertEquals(3, myBrick0.getBrickLives());
        press(myScene, KeyCode.O);
        assertEquals(1, myBrick0.getBrickLives());
    }

    @Test
    public void testMultipleLivesBrick() {
        myBall.setCenterX(0);
        myBall.setCenterY(Brick.BRICK_HEIGHT * 8);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay.stats();
        assertEquals(2, myBrick70.getBrickLives());
    }

    @Test
    public void testBrokenBrickDestructionAndNoCollision() {
        myBall.setCenterX(Brick.BRICK_WIDTH * 1.5);
        myBall.setCenterY(Brick.BRICK_HEIGHT * 5.5);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myBall.setCenterX(Brick.BRICK_WIDTH * 1.5);
        myBall.setCenterY(Brick.BRICK_HEIGHT * 5);
        myBall.setXDirection(0);
        myBall.setYDirection(-1);
        myBall.startBall();
        myDisplay.stats();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay.stats();
        assertEquals("Lives: 3 Score: 2 Level: " +myDisplay.getLevel()+ " High Score: " + myDisplay.getHighScore(),
            myDisplay.getText());
        assertEquals(-1, myBall.getYDirection());
    }

    @Test
    public void testRubberBrickBottom() {
        myBall.setCenterX(Brick.BRICK_WIDTH * 3.5);
        myBall.setCenterY(Brick.BRICK_HEIGHT *2.5);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(160, myBall.getSpeed());
    }

    @Test
    public void testRubberBrickTop() {
        myBall.setCenterX(Brick.BRICK_WIDTH * 3.5);
        myBall.setCenterY(Brick.BRICK_HEIGHT* 2.0);
        myBall.startBall();
        sleep(1000);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(160, myBall.getSpeed());
    }

    @Test
    public void testRubberBrickSide() {
        myBall.setCenterX(Brick.BRICK_WIDTH * 4.0);
        myBall.setCenterY(Brick.BRICK_HEIGHT* 2.5);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(160, myBall.getSpeed());
    }
}