/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:35
 * Deadwood
 */

import java.io.InvalidObjectException;
import java.util.Random;

public class Player {

    private GamePiece dice;
    public final String playerName;

    private int dollars;
    private int credits;

    private Room currentRoom;
    private Scene currentScene;
    private Role currentRole;
    private int practiceChips;

    private boolean hasMoved;
    private boolean hasTakenRole;


    public Player(String name, int startingRank,
                  int startingDollars, int startingCredits, Room startingRoom) {
        playerName = name;
        credits = startingCredits;
        dollars = startingDollars;
        dice = new GamePiece(startingRank);
        practiceChips = 0;

        currentRoom = startingRoom;
        hasMoved = false;
        hasTakenRole = !(this.getCurrentRole() == null);
    }


    public void move(Room location) {
        this.currentRoom = location;
    }


    private void takeRole(Scene scene, String role) {
        // roles are defined in Scene class as a Map<String, Integer>, where the integer value is the payout.
        // Consider defining a role class, a very short one to contain  more info about roles
        this.currentRole = scene.getRole(role);
    }


    private void doNothing() {
        // player takes no action
        System.out.println("On to the next player's turn!");
    }


    public Room getCurrentRoom() {
        return currentRoom;
    }


    public Scene getCurrentScene() {
        return currentScene;
    }


    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }


    public Role getCurrentRole() {
        return currentRole;
    }


    public void setCurrentRole(Role r) {
        currentRole = r;
    }


    public int getRank() {
        return dice.getRank();
    }


    public int getCredits() {
        return credits;
    }


    public int getDollars() {
        return dollars;
    }


    public int getScore() {
        return credits + dollars + dice.getRank() * 5;
    }


    public boolean getHasMoved() {
        return hasMoved || currentScene != null;
    }


    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getHasTakenRole() {
        return hasTakenRole;
    }


    public void setHasTakenRole(boolean hasTakenRole) {
        this.hasTakenRole = hasTakenRole;
    }


    public int getPracticeChips() {
        return this.practiceChips;
    }

    /**
     * Called from parseInput() in the GameController by a player.
     */
    public void rehearse() {
        practiceChips += 1;
        hasMoved = true;    //both set to true ends a player's turn
        hasTakenRole = true;
    }


    /**
     * Takes an integer from a rolled die as input, determines if the player succeeds or fails a shot.
     * If success, decrements counter, adds credits, etc.
     * If scene shots go to 0, calls wrapScene()
     * @param rolledDie integer
     * @return returns true/false based on player success or fail
     */
    public boolean act(int rolledDie) {
        //todo implement differentiation between extra roles and main roles
        hasMoved = true;
        hasTakenRole = true;
        if (currentScene.budget <= rolledDie + practiceChips) {
            //acting successful
            credits += 2;
            currentScene.setCounter(currentScene.getCounter() - 1);
            if (currentScene.getCounter() == 0) {
                currentScene.wrapScene();
            }
            return true;
        }
        return false;
    }


    public void addDollars(int dollars) {
        this.dollars += dollars;
    }


    public void addCredits(int credits) {
        this.credits += credits;
    }


    /**
     * Checks if a player is in the CastingOffice, if they are, checks money or credits (specified by input)
     * Then ranks up a player
     * @param usingCredits True if using credits, false otherwise
     */
    public void rankUp(boolean usingCredits) {

        int currentRank = getRank();
        if (currentRank == 6) {
            System.out.println("You're already the maximum rank!");
        }

        if (!(currentRoom instanceof CastingOffice)) {
            System.out.println("You must be in the Casting Office to rank up!");
        } else {

            if (usingCredits) { //if using credit
                int creditCost = ((CastingOffice) currentRoom).creditCost[currentRank];
                if (credits >= creditCost) {
                    credits -= creditCost;
                    this.dice.rankUp();
                } else {
                    System.out.println("Insufficient amount of credits");
                }
            }

            else { //if using credit
                int dollarCost = ((CastingOffice) currentRoom).dollarCost[currentRank];
                if (dollars >= dollarCost) {
                    dollars -= dollarCost;
                    this.dice.rankUp();
                } else {
                    System.out.println("Insufficient amount of dollars");
                }
            }
        }
    }
}
