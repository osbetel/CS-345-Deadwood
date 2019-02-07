/*
 * Created by: Andrew Nguyen
 * Date: 2019-02-05
 * Time: 16:35
 * Deadwood
 */

public class Player {

    private GamePiece dice; //Contains player's rank! This is a death relationship.
    private Room currentRoom;
    private Scene currentScene;

    public Player() {
        //stuff
    }

    private void move() {
        // check currentRoom to see if target room is adjacent
    }

    private void takeRole() {

    }

    private void doNothing() {

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








}
