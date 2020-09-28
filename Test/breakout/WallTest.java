package breakout;


import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import org.testfx.service.query.EmptyNodeQueryException;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;

class WallTest extends DukeApplicationTest {


    private final BreakoutGame myBreakoutGame = new BreakoutGame();
    private Scene myScene;
    private Ball myBall;
    private Display myDisplay;
    private Stage myStage;
    private Wall myWall;



    @Override
    public void start(Stage stage) {
        myScene = myBreakoutGame.setupScene(3, BreakoutGame.SIZE, BreakoutGame.SIZE, BreakoutGame.BACKGROUND);
        myStage = stage;
        myStage.setScene(myScene);
        myStage.show();
        myBall = lookup("#ball").query();
        myDisplay = lookup("#display").query();
        myWall = lookup("#wall2").query();
    }

    @Test
    public void testBallBounceOffWall(){
        myBall.setCenterX(3*Brick.BRICK_WIDTH);
        myBall.setCenterY(4*Brick.BRICK_HEIGHT);
        sleep(1000);
        myBall.setXDirection(-1);
        myBall.setYDirection(0);
        myBall.startBall();
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertEquals(-1, myBall.getXDirection());
    }
    @Test
    public void testRemoveWallsCheatKey(){
        press(myScene, KeyCode.M);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        assertThrows(EmptyNodeQueryException.class, () -> myWall = lookup("#wall2").query());
    }

    @Test
    public void testRemoveAllBricksButLeaveWalls(){
        press(myScene, KeyCode.C);
        javafxRun(() -> myBreakoutGame.step(BreakoutGame.SECOND_DELAY));
        myDisplay = lookup("#display").query();
        assertEquals("""
                GAME WON!
                Press 1 to restart game from level 1
                Press 0 to restart game from rules screen
                Nice job!

                Lives remaining: 3
                Score reached: 0
                High score to beat: """ +" "+ myDisplay.getHighScore(), myDisplay.getText());
    }


}