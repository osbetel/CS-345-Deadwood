/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:35
 * Deadwood
 */

import java.io.InvalidObjectException;

public class Player {

    private GamePiece dice;
    public final String playerName;

    private int dollars;
    private int credits;

    private Room currentRoom;
    private Scene currentScene;
    private Role currentRole;


    public Player(String name, int startingRank,
                  int startingDollars, int startingCredits, Room startingRoom) {
        playerName = name;
        credits = startingCredits;
        dollars = startingDollars;
        dice = new GamePiece(startingRank);

        currentRoom = startingRoom;
    }


    public void move(Room location) {
        //todo: check currentRoom to see if target room is adjacent
        this.currentRoom = location;
    }


    private void takeRole(Scene scene, String role) {
        // roles are defined in Scene class as a Map<String, Integer>, where the integer value is the payout.
        // Consider defining a role class, a very short one to contain  more info about roles
        this.currentRole = scene.getRole(role);
    }


    private void doNothing() {
        // player takes no action
        // add something to remove their turn
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


    public double getTotalValue() {
        return -1.0;
    }


    public void rankUp(boolean usingCredits) {

        int currentRank = getRank();
        if (currentRank == 6) {
            System.out.println("You're already the maximum rank!");
        }

        if (currentRoom instanceof CastingOffice) {
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
