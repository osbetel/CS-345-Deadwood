Andrew Nguyen + Jade Jordan

**Assignment 2 Final Submission**

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
You'll have to manually move the Assets folder into the output directory.
I've already precompiled into the "output" folder if you want to use that.


**Design choices, cohesion and coupling report**

In terms of coupling, I would say that the most prevalent forms we
have are control coupling and some type-use coupling.

Control coupling is especially noticeable in classes close to the GameController,
due to the fact that certain actions and events in the game are called by the
GameController when certain conditions are met.

The GameController for example, receives player input and will move the player,
set their current scene or role, etc.. It also creates the Board, the Scene cards,
and all the Players, while tying them together and acting as a medium through which
these vital game objects can interact.

===

Cohesion is another thing though, and I would say the most visible forms we have are
communicational, procedural, then sequential cohesion, in order of usage. There is
also a small amount of temporal cohesion as well.

Many of the actions in the game are grouped by when and how they occur in terms of dependency.
As another example, some things require data and other conditions to be met before
they are allowed to execute. The wrapScene() method is in the Scene class, which requires a shot
counter of 0, some players performing on the Scene / Room (as extras), etc.

Once these conditions are met and the Scene is wrapped, several things happen:
The Scene is removed from the Board, players are paid out bonuses (in addition to
  a regular payout), and then those Players' active scenes and active roles are cleared
  (because there is no more Scene).

===

With overall design choice, we believed that it was best to have a single class,
in this case the GameController class, act as a medium through which the other classes interacted.
We also used inheritance in a couple places, namely the Trailer and CastingOffice,
which are both children classes of the Room class. This is so that you can easily
check if a Player is at one of those rooms with "location instanceof CastingOffice/Trailer."
This is also a good example of type-use coupling, as these classes will also function
as Room objects (and thus can be stored in the list of Rooms), but regular Rooms
will not function as Trailers or Casting Offices. It's a good way to differentiate
between unique objects while still retaining functionality of another.
