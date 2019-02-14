/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 22:20
 * Deadwood
 */

public class GameController {

    public int score;
    public int pChip;
    public int credits; //data shared w/in other classes that update it
    public int remScenes;
    public int remCount;

    public GameController() {
        //Needs to create the board, players, scenes, rooms, etc.
        //Constructing the Board should also construct the Rooms
        //Constructing Players should also construct GamePieces
        //The Scenes are separate and tacked onto the rooms each day
    }

    //display credits of current player
    public int getCredits(Player player) {
        return this.credits;
    }

/*
    //get rank was already in another class, should it just stay there?
    public int getRank() {
        return dice.getRank();
    }
*/
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