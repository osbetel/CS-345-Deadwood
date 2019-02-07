/*
 * Created by: Andrew Nguyen
 * Date: 2019-02-05
 * Time: 16:34
 * Deadwood
 */

public class Board {
    public Room[] rooms;
    private Player[] players;
    private int day;

    public Board(int numPlayers) {
        //initial setup
        this.day = 0;
        //todo: need to loop and initialize players and rooms (don't forget special rooms)
        //setupGame(numPlayers);
    }

    private void initialSetupGame(int numPlayers) {
        //todo: setupGame() should make players, assign money, rank, credits, etc.
    }

    private void setupNewDay() {
        
    }

    private void startGame() {
        //todo: begin the game at the first player. Is there a better way to handle this?
    }

    private int nextDay() {
        this.day += 1;
        return this.day;
    }

    private void clearScenes() {
        //removes all scenes from all rooms
        for (Room r : this.rooms) {
            r.clearScene();
        }
    }

    private void dealScenes() {
        //deal out scene cards / objects to each room. Called at start of a new day
        for (Room r : this.rooms) {
            //todo: r.newScene... from remaining unused scenes
        }
    }

}
