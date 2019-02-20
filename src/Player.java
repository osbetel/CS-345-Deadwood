/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-05
 * Time: 16:35
 * Deadwood
 */

public class Player {

    private GamePiece dice;
    private Room currentRoom;
    private Scene currentScene;
    private Role currentRole;

    public Player(String name, int startingRank,
                  int startingMoney, int startingCredits) {
        //stuff
    }

    private void move(Room location) {
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

    public int getRank() {
        return dice.getRank();
    }

    public double getTotalValue() {
        return -1.0;
    }

    public boolean rankUp(int targetRank, boolean dollarCosts) {
        return false;
    }






}
