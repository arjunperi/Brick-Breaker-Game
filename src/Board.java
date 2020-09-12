import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Board extends Application {
    public static final String TITLE = "Breakout JavaFX";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final int BRICK_SIZE = 50;
    public static final Paint BRICK_COLOR = Color.HOTPINK;
    public static final int PADDLE_SIZE = 50;
    public static final Paint PADDLE_COLOR = Color.PLUM;
    public static final int BALL_SIZE = 30;
    public static final Paint BALL_COLOR = Color.BISQUE;
    public static final int BALL_ROUNDING = 15;
    public static final int BALL_SPEED = 40;
    public static final int VERTICAL_OFFSET = 80;


    private Scene myScene;
    private Rectangle brick;
    private Rectangle paddle;
    private Circle ball;

    public void start(Stage stage){
        myScene = setupScene(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    Scene setupScene (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // make some shapes and set their properties
        brick = new Rectangle(width/2 - BRICK_SIZE, height/2 - VERTICAL_OFFSET, BRICK_SIZE, BRICK_SIZE);
        brick.setFill(BRICK_COLOR);
        brick.setId("brick");

        paddle = new Rectangle(width/2 - PADDLE_SIZE, height/2 + VERTICAL_OFFSET, PADDLE_SIZE, PADDLE_SIZE);
        paddle.setFill(PADDLE_COLOR);
        paddle.setId("grower");


        ball = new Circle(width/2, height/2, BALL_SIZE/2);
//        ball.setArcWidth(BALL_ROUNDING);
//        ball.setArcHeight(BALL_ROUNDING);
        ball.setFill(BALL_COLOR);
        ball.setId("mover");

        root.getChildren().add(brick);
        root.getChildren().add(paddle);
        root.getChildren().add(ball);

        Scene scene = new Scene(root, width, height, background);


//        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
//        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }


    void step (double elapsedTime) {
        // update "actors" attributes
        updateShapes(elapsedTime);
    }

    private void updateShapes (double elapsedTime) {
        // there are more sophisticated ways to animate shapes, but these simple ways work fine to start
//        ball.setCenterX(ball.getCenterX() + BALL_SPEED * elapsedTime);
        ball.setRotate(ball.getRotate()-1);
        brick.setRotate(brick.getRotate() - 1);
        paddle.setRotate(paddle.getRotate() + 1);
    }

    public static void main (String[] args) {
        launch(args);
    }


}



