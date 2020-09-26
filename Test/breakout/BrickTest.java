package breakout;


import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;


class BrickTest extends DukeApplicationTest {


    private final BreakoutGame myBreakoutGame = new BreakoutGame();
    private Scene myScene;

    private Brick myBrick0;
    private Brick myBrick1;
    private Ball myBall;
    private Display myDisplay;


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        myScene = myBreakoutGame.setupScene(1, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        stage.setScene(myScene);
        stage.show();
        myBrick0 = lookup("#brick0").query();
        myBrick1 = lookup("#brick1").query();
        myBall = lookup("#ball").query();
        myDisplay = lookup("#display").query();
    }

    @Test
    public void testFirstBrickInFirstAndSecondRow() {
        assertEquals(100, myBrick0.getX());
        assertEquals(0, myBrick0.getY());
        assertEquals(0, myBrick1.getX());
        assertEquals(40, myBrick1.getY());
    }

    @Test
    public void testBrickDestruction(){
        myBall.setCenterX(30);
        myBall.setCenterY(160 + myBall.getRadius());
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay.stats();
        assertEquals("Lives: " +  myDisplay.getLives() + " Score: " + myDisplay.getScore() + " Level: " +
                myDisplay.getLevel() + " High Score: " + myDisplay.getHighScore(), myDisplay.getText());
    }
}