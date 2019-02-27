/*
 * Created by: Andrew Nguyen & Jade Jordan
 * Date: 2019-02-07
 * Time: 13:06
 * Deadwood
 */

/**
 * GamePiece class seems slightly redundant. May remove this in part 3.
 */
public class GamePiece {

    private int rank;

    public GamePiece(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    public void rankUp() {
        //DOES NOTHING. All the checks are performed in the player class and if they are true,
        //then this simply increments by 1. Does NOT check anything.
        this.rank += 1;
    }
}
