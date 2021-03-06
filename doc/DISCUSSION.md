- Magic Numbers
    - One of the obvious things that came up from the design coach was the use of magic numbers. In a lot of the classes for objects on the scene (paddles, balls, etc), we use constants, but we also use arbitrary numbers like SIZE/2. We're going to go in and make these constants so they are more readable, and also so we have less duplication. 
- Handling file reading errors
    - Another thing we noticed from the design coach was the flaws in our file reading, specifically in how we handle erroneus files. To fix these errors, we are going to go in and throw exceptions that will allow us to see exactly where in the code the error is occurring. 
- Boolean literals
    - An easy fix -  we need to replace statements like "checkGameOver == false" to "!checkGameOver"
- Bricks
    - This is something we already planned on doing but haven't yet implemented - we're going to create a brick hierarchy so that the current long method of brick and ball collisions can be extracted out of the ball class and into an abstract brick class, with each subclass having its own collision logic. 
- Powerups
    - Like the bricks, we already planned on adding powerup hierarchy so that we can pull out conditionals into subclasses.
- Things we can ignore
    - Most of the errors that we found had to do with the keyCode logic, but we spoke with TA's and were told that there is not much we can do to make this better. 
- Other changes to make
    - Another thing we will do is change the Display hierarchy back to one Display class and simply have methods for each different type of display that print their respective stats.

