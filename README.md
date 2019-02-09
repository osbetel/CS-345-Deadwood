Andrew Nguyen + Jade Jordan

**Assignment 2, Part 1:
Project skeleton files.**

**Compiling instructions:**
1) first, open up a terminal and cd to the src directory.
2) then, in the src directory, execute the following:
"javac -d <output directory> *.java"

3) You can specify an output directory using the -d flag.
I like to use

"javac -d out *.java"

which will output all the compiled classes to the same folder under the out directory.

If you simply use

"javac *.java" then it will output all compiled class files
into the same directory as the java files.


**Design Choices:**
So far, we believe that there should be a central GameController class which
will construct the Players, Board, and everything necessary for those two.
The GameController and the Board should work together to loop through player turns
and increment days, clearing the Scenes and assigning new ones as necessary.

Players and their GamePieces are simply objects on the Board, but have no function
in determining whether the day is done and whether new Scenes are needed.
But they should be able to access Scenes to act, rehearse, and receive payouts.
