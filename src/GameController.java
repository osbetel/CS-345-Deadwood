/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 22:20
 * Deadwood
 */

public class GameController {

    private int score;
    private int pChip;
    private int credits;
    private int remScenes;

    public GameController() {

    }

    //display credits of current player
    public int getCredits(Player player) {
        return -1;
    }

/*
    //get rank was already in another class, should it just stay there?
    public int getRank() {
        return dice.getRank();
    }
*/
    //display amount of practice chips a player has
    public int getPChip(Player player) {
        return -1;
    }

    //calculating score at the moment
    public int getScore(Player player) {
        //todo calc current score
        return -1;
    }

    //see how many counters left on a given scene
    public int getCounters(Room room) {
        return -1;
    }

    //know how many scenes are left
    public int scenesLeft() {
        return -1;
    }


}