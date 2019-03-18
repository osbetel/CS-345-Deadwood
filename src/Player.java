/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:35
 * Deadwood
 */

import java.lang.*;
import java.util.Random;


public class Player {

    public final String playerName;
    private GamePiece dice;
    private final String color;
    private final int playerNum;

    private int dollars;
    private int credits;

    private Room currentRoom;
    private Scene currentScene;
    private Role currentRole;
    private int practiceChips;

    private boolean hasMoved;
    private boolean hasTakenRole;
    private boolean hasTakenAction;

    /**
     * Player constructor
     * @param name
     * @param startingRank
     * @param startingDollars
     * @param startingCredits
     * @param startingRoom
     * @param startingColor
     * @param num
     */
    public Player(String name, int startingRank, int startingDollars,
                  int startingCredits, Room startingRoom, String startingColor, int num) {
        playerName = name;
        dice = new GamePiece(startingRank);
        dollars = startingDollars;
        credits = startingCredits;
        color = startingColor;
        playerNum = num;
        practiceChips = 0;

        currentRoom = startingRoom;
        hasMoved = false;
        hasTakenRole = false;
        hasTakenAction = false;
    }

    public void move(Room location) {
        this.currentRoom = location;
        hasMoved = true;
        hasTakenAction = true;
    }

    /**
     * This is only used to move players back to trailer at the end of the day, and performs no checks
     */
    public void forceMove(Room location) {
        this.currentRoom = location;
    }

    public void takeRole(String role) {
        // roles are defined in Scene class as a Map<String, Integer>, where the integer value is the payout.
        // Consider defining a role class, a very short one to contain  more info about roles
        if (currentRoom.getScene() == null) {
            System.out.println("Room has no scene!");
            return;
        }

        if (currentRoom.extraRoles.containsKey(role)) {
            Role r = currentRoom.extraRoles.get(role);
            if (r.requiredRank <= getRank()) {
                this.currentRole = currentRoom.extraRoles.remove(role);
                currentScene = currentRoom.currentScene;
                currentScene.playersInScene.add(this);
                hasTakenRole = true;
                hasTakenAction = true;
            }
        } else if (currentRoom.getScene().roles.containsKey(role)) {
            Role r = currentRoom.getScene().roles.get(role);
            if (r.requiredRank <= getRank()) {
                this.currentRole = currentRoom.getScene().roles.remove(role);
                currentScene = currentRoom.currentScene;
                currentScene.playersInScene.add(this);
                hasTakenRole = true;
                hasTakenAction = true;
            }
        } else {  // can't take non-existent role
            System.out.println("Role cannot be taken!");
        }
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public String getPlayerIcon() {
        return "Assets/dice/" + color + dice.getRank() +".png";
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

    public void setHasTakenAction(boolean hasTakenAction) {
        this.hasTakenAction = hasTakenAction;
    }

    public boolean getHasTakenAction() {
        return hasTakenAction;
    }

    public int getPracticeChips() {
        return this.practiceChips;
    }

    public void rehearse() {
        practiceChips += 1;
        hasTakenAction = true;
    }

    /**
     * Takes an integer from a rolled die as input, determines if the player succeeds or fails a shot.
     * If success, decrements counter, adds credits, etc.
     * If scene shots go to 0, calls wrapScene()
     * @return returns true/false based on player success or fail
     */
    public boolean act() {

        if (currentRole == null) {
            return false;
        }

        hasTakenAction = true;
        boolean isMainRole = currentRole.mainRole;
        int sceneBudget = currentScene.budget;
        int dieRoll = rollDie();

        if (dieRoll >= sceneBudget) {
            //acting successful
            if (isMainRole) {
                credits += 2;
            } else {
                credits += 1;
                dollars += 1;
            }
            currentRoom.shotCounters.remove(0);
            int numCounters = currentRoom.shotCounters.size();
            if (numCounters == 0) {
                currentScene.wrapScene();
            }
            return true;
        } else {
            if (isMainRole) {
                //nothing
            } else {
                dollars += 1;
            }
            return false;
        }
    }

    //random integer die roll
    private int rollDie() {
        Random r = new Random();
        return r.nextInt(6) + 1 + practiceChips;
    }

    public void addDollars(int dollars) {
        this.dollars += dollars;
    }

    public void addCredits(int credits) {
        this.credits += credits;
    }

    public void setPracticeChips(int x) {
        practiceChips = x;
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
            return;
        }

        if (!(currentRoom instanceof CastingOffice)) {
            System.out.println("You must be in the Casting Office to rank up!");
        } else {

            if (usingCredits) { //if using credit
                int creditCost = ((CastingOffice) currentRoom).creditCost[currentRank - 1];
                if (credits >= creditCost) {
                    credits -= creditCost;
                    this.dice.rankUp();
                    hasTakenAction = true;
                } else {
                    System.out.println("Insufficient amount of credits");
                }
            }

            else { //if using credit
                int dollarCost = ((CastingOffice) currentRoom).dollarCost[currentRank - 1];
                if (dollars >= dollarCost) {
                    dollars -= dollarCost;
                    this.dice.rankUp();
                    hasTakenAction = true;
                } else {
                    System.out.println("Insufficient amount of dollars");
                }
            }
        }
    }
}
