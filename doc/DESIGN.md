# Simulation Design Final
### Names
- Arjun Peri
- Jerry Fang
## Team Roles and Responsibilities

 * Team Member #1

Arjun: Designed game logic (collisions, directionality, etc), implemented Level class and level transitions, implemented display 
and cut screens, implemented file reading methods (brick reading, high score, etc), along with other shared responsibilities. 

 * Team Member #2

Jerry: Focused on implementing the various game objects (bricks, powerups, ball, paddle) and their interactions with each other along with other shared responsibilities.


## Design goals

The core objective of this project was to practice abstraction and inheritance, in order to make subclasses of various game elements easy to implement within a game of Breakout.
#### What Features are Easy to Add
Features that are easily implemented are brick variants, specifically how they interact with the ball when a collision occurs, and powerups, granting different effects when caught with the paddle. In particular, the subclasses of bricks and powerups are implemented by overriding the three BallCollision methods and the activatePowerUp method respectively. Furthermore, different level designs (in terms of brick placement) was intended to be easily added as well, with a level's layout being given by a formatted text file.

## High-level Design
The BreakoutGame class sets up the stage and sets up the scene, and contained the JavaFX Group that allowed for visualization of the game. Within the setupScene method, a new level is created by creating a new Level object. When a new Level objected is created, the Level class constructor takes in the root instance variable from BreakoutGame as an argument, clears it, then calls the appropriate methods that repopulate it with the required objects (ball, paddle, bricks, powerups, etc.). Within the Level class, the various objects of the game are created (Ball, Bricks, Paddle, etc.). and there are methods that control the game logic. The step method in BreakoutGame calls updateShapes which is in the Level class, and this updateShapes method is able to consistently check for things like whether or not the game is over as well as methods such as getBallPosition which updates the moving objects' position to simulate movement while also checking for object interactions (e.g. Ball and Paddle collision). Also in the step method are conditional statements that dictate when to change levels and thus create a new Level object.

#### Core Classes
The core classes are BreakoutGame (main), Level (which handles Level creation and game object updating), Paddle, Ball, Brick (superclass of different brick implementations), PowerUp (superclass of different powerup implementations), Display (handles the tracking of stats and text for different situations such as rules), and BrickList (handles level file reading and the list of Bricks and Walls created from reading the level files).

## Assumptions that Affect the Design
One assumption that is made is that the various kinds of Bricks that may be implemented only vary in terms of their actions after collision with the ball (i.e. something occurs when the brick hits it, it cannot occur passively), and this effect may only occur on the Ball or the Brick. This was done to simplify the subclasses in terms of inheriting methods from the Brick superclass and which methods needed to be overriden. Furthermore, it was decided that any Bricks other than the multiple lives bricks that were created were only to have one life, to simplify file reading and the construction of the Brick List. Similarly, as written, the powerups can only have some effect on the Paddle, the Ball, or both, but cannot effect the overall Level in terms of Bricks, Walls, score, etc. This was done to simplify the hierarchy of inheritance in the PowerUp class, but could be extended to operate across other game elements if one should choose to do so (explained below). Lastly, a major design decision made was that the Levels themselves cannot be massively different than one another (i.e. the major difference between the levels is limited to powerups, brick variations, brick layouts, and wall layouts). This was done to simplify the creation of the level, and which objects needed to be created within the Level class. Furthermore, due to a relative similarity between levels, it allowed us to contain Level to a single class, with a new Level object being created within BreakoutGame for each level, and also allowed us to use a continous Group contained in BreakoutGame (clearing and readding elements to it) throughout the entire game. 


## New Features

#### How To Add Features
As noted above, since this project's goal was to practice abstraction and inheritance, there are Brick and PowerUp variations that can be added. To add a Brick variation, one must create a subclass that extends Brick, the constructor should read similar to this: 
```java
 BrokenBrick(int lives) {
    super(lives);
    setFill(fillColor);
    setStroke(strokeColor);
  }
``` 
with the desired fill and stroke of the brick declared. Then, one should Override topBallCollision, bottomBallCollision, and sideBallCollision, all methods that are called when the ball collides with the Brick. Currently, only the Ball is passed as an argument, such that the effect of the collision can only effect the ball (e.g. direction) or some effect on the Brick (not implemented in this game). This could be changed by modifying the signature of these methods (e.g. passing the Paddle as an argument as well), but this would also require changing the signature of checkBrickCollision in Ball as well. Once the subclass is implemented, one must then modify the BrickList class's setUpLevel method, and within the for loop, add another else if conditional in the format 
`if (myRow[col].contains("*"))`
 where * is the single, non-integer character that designates the brick type in the level text files. Furthermore, within this if statement, one must create this new type of Brick, and call addPowerUp (if one wishes for this brick to be able to contain power ups) and addBrickToList as shown:
 ```java 
 private void constructRubberBrick(List<Brick> myBricks, int yOffset, String[] myRow, int col) {
    Brick currentBrick = new RubberBrick(1);
    addPowerUp(myRow, col, currentBrick);
    addBrickToList(myBricks, yOffset, col, currentBrick);
  }
```

Creating and adding a PowerUp functions similarly, such that one must create a subclass extending PowerUp, create a constructor calling super() (this is optional only if one wants to be able to add a cheat key creating this powerup), another constructor calling super(Brick), setFill, and setStroke. Then, Override activatePowerUp(Paddle, Ball) to set the desired effect. Within BrickList once again, for file parsing purposes, within the addPowerUp method, one must add an addition "or" to the if conditional, checking for if the String contains a whatever value one desires to denote the PowerUp type. Lastly, in the Level class, within the dropPowerUp method, one must add an additional case to the switch, checking for the denoted character and creating a new PowerUp with the powerBrick as the argument, for example:
`case "P" -> new PaddleLengthPowerUp(powerBrick);`

Similar to Bricks, the PowerUps are passed the Paddle and Ball as arguments, and so currently can only modify those. Similar to the Brick class, one can add more objects to modify (e.g. Bricks) by changing the signature of activatePowerUp and checkActivation in the PowerUp class (and of course passing the required arguments in within the Level class calling the CheckActivation method).

#### Other Features not yet Done
One feature that was never implemented was having some sort of action occur during the cutscreens between levels, like an animation. Currently, the way cutscreens work is that the root of the Level is cleared, then repopulated with only a Diplay object, which can be seen in the clearLevelIfOver() method. This clearing/repopulating within the method is done differently based on conditional statements, as the type of Display that needs to appears differs based on the situation (level won, game won, game lost). This could have been designed better by extracting the commonalities within these conditionals, but here is an example of clearing the level once won:
```java 
 private void clearLevelIfOver(){
        if (myBall.getGameLives() == 0 && currentLevel > 0) {
            myRoot.getChildren().clear();
            myRoot.getChildren().add(myDisplay);
            myDisplay.lose();
            gameEnd = true;
            myBall.endBall();
        }
```
To implement an animation, we could create an Animation class that has subclasses based on the type of animation that the situation calls for. One possible implementation of this would be having some sort of quest that the ball undergoes (e.g searching for his lost friend who is also a ball), and each situation calls the corresponding Animation sublass which causes the ball to move in some way on the screen (e.g climbing stairs to get to the next level). Possible methods that these Animation classes would include/ovverride are animateBall() to move the ball a certain way, or setUpBackground() to set the background of the animation (e.g bricks that act as steps - maybe by adding a new Brick subclass called something like AnimationBrick to specify the movement with the ball). 

Additionally, a cheat key and feature that could have been interesting to include is one that changes the size of the ball. This would be useful if a player wants to increase the size to make the game easier, or vice versa. This would be easy to implement based on our current design, as we would just need to create methods in the Ball class that change the size, and then call those methods anytime the key is pressed. Furthermore, adding this as a powerup would be very easy to implement as well, as it would simply require the addition of a new PowerUp subclasses that Overrides the activatePowerUp() method using the aformentioned logic. 

