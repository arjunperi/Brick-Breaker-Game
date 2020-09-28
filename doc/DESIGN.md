# Simulation Design Final
### Names
- Arjun Peri
- Jerry Fang
## Team Roles and Responsibilities

 * Team Member #1
Arjun: Designed game logic (collisions, directionality, etc), implemented Level class and level transitions, implemented display 
and cut screens, implemented file reading methods (brick reading, high score, etc), along with other shared responsibilities. 

 * Team Member #2

 * Team Member #3


## Design goals

#### What Features are Easy to Add


## High-level Design
The BreakoutGame class sets up the stage and sets up the scene. Within the setupScene method, a new level is created by 
creating a new Level object. When a new Level objected is created, the Level class constructor takes in the root instance variable
from BreakoutGame as an argument, clears it, then calls the appropriate methods that repopulate it with the required objects (ball,
paddle, bricks, powerups, etc.). Within the Level class, there are methods that control the game logic. The step method in BreakoutGame calls 
updateShapes which is in the Level class, and this updateShapes method is able to consistently check for things like whether or not
the game is over. Also in the step method are conditional statements that dictate when to change levels and thus create a new Level object.


#### Core Classes


## Assumptions that Affect the Design

#### Features Affected by Assumptions


## New Features HowTo

#### Easy to Add Features

#### Other Features not yet Done

