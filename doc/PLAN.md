# Game Plan
## NAMEs


### Breakout Variation Ideas

### Interesting Existing Game Variations

 * Devilish is definitely an interesting variation because rather than clearing the screen after a level and then resetting it for the next level,
 the game flows smoothly into the next level with a passage. This continuity seems like it requires a different type of code structure, but it adds
 to the gameplay. 

 * Super breakout is another variant we think is interesting becasue of the different types of power-ups that can be implemented. By adding power-ups, the game becomes a lot more fun and adds another level of depth. We think that this variant allows for a lot of creativity in the power-ups we might want to implement. 


#### Block Ideas

 * Crumbly block - when the ball hits this block the block is removed but the ball maintains the same direction.

 * Strong block - this block has multiple lives and thus must be hit by the ball multiple times before it is removed. 

 * Power up block - when this block is hit by the ball, the block is removed and a power-up is rewarded. 


#### Power Up Ideas

 * Extra life power-up

 * Ball speed reduction power-up

 * Paddle growth power-up


#### Cheat Key Ideas

 * Press a button on your keyboard and a level is skipped. 

 * Press a button on your keyboard and the paddle grows to the size of the bottom of the screen, so there is no way the ball can hit the bottom and the game essentially autoplays for a period of time. 

 * Press a button on your keyboard and all blocks are set to have only one life. 

 * Press a button on your keyboard and you get infite lives. 


#### Level Descriptions

 * Level 1
   * Rows of blocks arranged in the standard breakout layout.

   * Variation features

 * Level 2
   * There is a layer of outer blocks that all have multiple lives that enclose the inner blocks.

   * Variation features

 * Level 3
   * Block Configuration

   * Variation features


### Possible Classes

 * Class 1
   * Brick class - holds information about bricks in the game

   * boolean isDestroyed() - returns if a brick is destroyed or not. 

 * Class 2
   * Ball class - holds information about balls in the game

   * void resetBall()  - resets the ball to the starting position on the paddle. 

 * Class 3
   * Paddle class - holds information about the paddles in the game

   * void movePaddle() - moves the paddle around the screen. 

 * Class 4
   * Board class - sets the board that the game will be played on 

   * void start (Stage stage) - sets the stage for the screen. 

 * Class 5
   * Game class - runs the game and holds the main method

   * void main (String[] args)
