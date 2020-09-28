game
====

This project implements the game of Breakout. There are 3 level variants. The first contains rows of bricks all with one life, with some containing one of three powerups (extra life, paddle extension, and ball speed decrease). The Second contains various brick variants: multiple lives brick (the life change is demarked by a change in color in order from least to greatest HOTPINK, GREEN, BLUE, and BEIGE if life > 3), broken brick (ball does not collide, marked as brown), and rubber brick (ball speed increases when hit, marked as black). The last level variant contains new structures known as Walls, which act to block the path of the ball, but do not contribute to score and cannot be destroyed (without use of a cheat key).

Name: 
- Arjun Peri
- Jerry Fang (jdf58)

### Timeline

Start Date: 9/11/2020

Finish Date: 9/28/2020

Hours Spent: ~5 hrs / week per person * 3 weeks * 2 people = 30 hrs

### Resources Used
- Course website - Some starter code with setting up JavaFX was taken and modified from works of Dr. Robert Duvall.
- Stack overflow - Used [this](https://stackoverflow.com/questions/29930784/how-do-i-get-only-integers-from-a-string) for help in the file parsing method.
- Youtube
- Class Piazza board
- Office hour TA's - Ryan Weeratung, Christina Chen

### Running the Program

Main class:

To run the program, simply run main in the BreakoutGame.java class.

Data files needed: 
- level0.txt, level1.txt, level2.txt, level3.txt
	- level files that hold a configuration of blocks to be read
- gameOver.txt, gameWon.txt, levelCleared.txt, startupScreen.txt
	- Cutscreen files that hold the texts that are read and displayed at the corresponding transition points in the program
- highScore.txt
	- highScore file that updates everytime a new highscore is reached by appending the file and adding the new highscore to the next line.

Key/Mouse inputs:
There are no mouse inputs for this program.
Key inputs are listed below under cheat keys.
Cheat keys:
- Left: moves paddle left (unless at left edge of screen)
- Right: moves paddle right (unless at right edge of screen)
- R: resets ball to starting position
- Space: pauses/unpauses game
- S: starts the ball 
- L: adds a life 
- N: drops an extra life power up
- B: drops ball speed reduction power up
- P: drops paddle length extension power up
- O: makes all the bricks at the current level have only one life
- C: clears all the bricks at the current level
- M: clears all the walls at the current level
- D: destroys the "first" remaining block on the screen, which is defined as the top left most brick 
- Y: allows user to continue to next level when prompted
- 0: switches game to level 0 (rules screen) 
- 1: switches game to level 1
- 2: switches game to level 2
- 3: switches game to level 3
- Down: reduces ball speed by 10 (lowest possible speed is 10)
- Up: increases ball speed by 10 

Known Bugs:

Extra credit:
- Splash screen: level0 displays the rules of the game, and also has two bricks, a paddle, and a ball so that the user can test out
the controls. User is prompted to press Y to begin actual game at level 1. 
- Win/Loss Screen: Loss screen appears when all lives are lost, displaying game lost message and game stats. Win screen appears when
all levels are cleared, displaying game won message and game stats. For both screens, user is prompted to press 0 or 1 to reset game at either level 0
(rules screen) or level 1, with all game stats being reset. 
- Transition screen: Level cleared screen appears when a level is won, displaying cleared message and game stats. User is prompted to press Y 
to continue to next level. 
- Extra non-required cheat keys.


### Notes/Assumptions
We operate under the assumption that each file that contains the data for level configuration has the name level#.txt, starting from level1, as level0 is the rules screen. To add more levels, a new level file must be added, and the maxLevel int in BreakoutGame.java must be increased.

To create a level:

- Each line in in the text file represets a row of bricks, which can be up to 15 rows. 
- Within each row, each column of bricks must be separated by a space.
- Each brick is represented as either a number (indicative of the number of lives) for normal or multiple live bricks, a "*" for broken bricks, or a "+" for a rubber brick.
- To represent no brick, use 0. To represent an entire row of no bricks, there must be at least one 0 in that row (leaving it empty will result in an IllegalStateException being thrown).
- A wall is represented by a "-"
- All bricks can contain powerups, and powerups are marked in the file as a letter corresponding to the powerup type immediately before the brick type. "L" is extra life, "P" is paddle length increase, and "S" is ball speed decrease"
- Examples of properly formatted levels can be found in the data directory.

### Impressions

