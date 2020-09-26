package breakout;


import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

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

    BrickTest() throws FileNotFoundException {
    }


    @Override
    public void start(Stage stage) throws FileNotFoundException {
        myScene = myBreakoutGame.setupScene(BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        stage.setScene(myScene);
        stage.show();

        myBrick0 = lookup("#brick0").query();
        myBrick1 = lookup("#brick1").query();
        myBall = lookup("#ball").query();
        myDisplay = lookup("#display").query();

    }

    @Test
    public void testFirstBrickInFirstAndSecondRow() {
        press(myScene, KeyCode.DIGIT1);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(100, myBrick0.getX());
        assertEquals(0, myBrick0.getY());
        assertEquals(0, myBrick1.getX());
        assertEquals(40, myBrick1.getY());
    }

    @Test
    public void testBrickDestruction(){
        myBall.setCenterX(0);
        myBall.setCenterY(160 + myBall.getRadius() / 2);
        myBall.setX_DIRECTION(0);
        myBall.setY_DIRECTION(-1);
        myBall.startBall(150);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals("Lives: 3 Score: 1", myDisplay.getText());
       // assertEquals("Lives: 3 Score: 1 Level: 0 High Score: 0", myDisplay.getText());
    }
}