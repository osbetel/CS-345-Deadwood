/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 22:20
 * Deadwood
 */

import java.util.Arrays;

public class GameController {

    public int score;
    public int pChip;
    public int credits; //data shared w/in other classes that update it
    public int remScenes;
    public int remCount;

    public GameController(int numPlayers) {
        //Needs to create the board, players, scenes, rooms, etc.
        //Constructing the Board should also construct the Rooms
        //Constructing Players should also construct GamePieces
        //The Scenes are separate and tacked onto the rooms each day
        Board board = new Board(numPlayers);
        Player[] players = makePlayers(4);
        Scene[] scenes = createScenes(); //todo

        // FOR TESTING
        for (Player p : players) {
            System.out.println(p);
        }
        for (Room r : board.rooms) {
            System.out.println(r.getRoomName() + ", extras: " + r.extraRoles + ", shot counters: " +  r.shotCounters);
        }
    }

    public Scene[] createScenes() {
        // This should read in a text file and generate all the scenes,
        // Place them into an array and return it to the game controller
        return null;
    }

    private Player[] makePlayers(int num) {
        return null;
    }

    //display credits of current player
    public int getCredits(Player player) {
        return this.credits;
    }

    //display amount of practice chips a player has
    public int getPChip(Player player) {
        return this.pChip;
    }

    //calculating score at the moment
    public int getScore(Player player) {
        //todo calc current score
        return this.score;
    }

    //see how many counters left on a given scene
    //***counter could be returned using this.__ or retrieved 
    public int getCounters(Room room) {
        return this.remCount;
    }

    //know how many scenes are left
    public int scenesLeft() {
        return this.remScenes;
    }
}