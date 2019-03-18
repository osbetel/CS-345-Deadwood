Andrew Nguyen + Jade Jordan

**Assignment 3 Final Submission**

**Compiling instructions:**
1) first, open up a terminal and cd to the src directory.
2) then, in the src directory, execute the following:
"javac -d <output directory> *.java"

3) You can specify an output directory using the -d flag.
For this, we used:
"javac -d ../output *.java"

which will output all the compiled classes to the same parent folder
under the output directory.

If you simply use "javac *.java" then it will output all compiled class files
into the same directory as the java files.

**How to run**
cd into the output folder, and execute "java Deadwood"
Make sure the Assets folder is in the output folder.
I've already precompiled into the "output" folder if you want to use that.


**Design choices report**

In terms of Design, for the final stage we decided to go with an MVC style of design.
The GameController serves as the controller portion of the application,
while the model is comprised of Board, Room, Scene, ParseXML, Role, and any
subclasses of the aforementioned.

The view portion of the design paradigm is taken over entirely by a single UserView
class. Essentially, the GameController mediates between the two, and tells the view
when to update it's images, or the model when a user updates their data.

As for cohesion, I feel that the Model portion had the largest amount of good cohesion.
It was a little tricky trying to make the Controller able to access the Model,
and the View able to access the Controller, while still ensuring that they were
distinctly separate and didn't mesh together too much in terms of functionality.

That said, I think the strongest points are functional and sequential cohesion.
Many events in a player's turn can be done one after the other. For example,
you can only take a roll if you've first moved to a room with available roles.
Additionally, I made sure to try and group things by functionality.

The coupling we used and thought about were content and control coupling.
Namely that the View can tell the controller that it wants to modify something,
and the Controller will then modify the Model accordingly. We also had type-use coupling
with Rooms and inheritance from Room to Trailer and Casting Office.
